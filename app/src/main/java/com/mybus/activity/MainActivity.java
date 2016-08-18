package com.mybus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mybus.R;
import com.mybus.adapter.ViewPagerAdapter;
import com.mybus.asynctask.ChargePointSearchCallback;
import com.mybus.asynctask.CompleteBusRouteCallback;
import com.mybus.asynctask.RoadSearchCallback;
import com.mybus.asynctask.RouteSearchCallback;
import com.mybus.dao.FavoriteLocationDao;
import com.mybus.fragment.BusRouteFragment;
import com.mybus.listener.CompoundSearchBoxListener;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnLocationChangedCallback;
import com.mybus.marker.MyBusMarker;
import com.mybus.model.BusRouteResult;
import com.mybus.model.ChargePoint;
import com.mybus.model.CompleteBusRoute;
import com.mybus.model.FavoriteLocation;
import com.mybus.model.GeoLocation;
import com.mybus.model.MyBusMap;
import com.mybus.model.SearchType;
import com.mybus.model.road.MapBusRoad;
import com.mybus.model.road.RoadResult;
import com.mybus.requirements.DeviceRequirementsChecker;
import com.mybus.requirements.PlayServicesChecker;
import com.mybus.service.ServiceFacade;
import com.mybus.view.AboutAlertDialog;
import com.mybus.view.CompoundSearchBox;
import com.mybus.view.FavoriteAlertDialogConfirm;
import com.mybus.view.FavoriteNameAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMyBusActivity implements OnMapReadyCallback, OnLocationChangedCallback,
        RouteSearchCallback, RoadSearchCallback, NavigationView.OnNavigationItemSelectedListener,
        CompoundSearchBoxListener, FavoriteNameAlertDialog.FavoriteAddOrEditNameListener, FavoriteAlertDialogConfirm.OnFavoriteDialogConfirmClickListener,
        ChargePointSearchCallback, CompleteBusRouteCallback {

    public static final int FROM_SEARCH_RESULT_ID = 1;
    public static final int TO_SEARCH_RESULT_ID = 2;
    public static final int DISPLAY_FAVORITES_RESULT = 3;
    private static final int DISPLAY_ROADS_RESULT = 4;
    public static final int DISPLAY_BUS_LINES_RESULT = 5;

    @Bind(R.id.compoundSearchBox)
    CompoundSearchBox mCompoundSearchBox;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.main_toolbar)
    View mToolbar;

    private Context mContext;
    private MyBusMap mMyBusMap;
    private MyBusMarker mMarkerFavoriteToUpdate;

    /*---Bottom Sheet------*/
    private BottomSheetBehavior<LinearLayout> mBottomSheetBehavior;
    private ViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.bottom_sheet)
    LinearLayout mBottomSheet;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    private static final int BOTTOM_SHEET_PEEK_HEIGHT_DP = 60;
    /*---Bottom Sheet------*/

    /**
     * Getter for Toolbar in order to interact with it from MyBusMap.
     * @return
     */
    public View getToolbar() {
        return mToolbar;
    }

    /**
     * Getter for CompoundSearchBox in order to interact with it from MyBusMap.
     * @return
     */
    public CompoundSearchBox getCompoundSearchBox() {
        return mCompoundSearchBox;
    }

    /*---Main bar---*/
    @OnClick(R.id.hamburger_icon)
    public void onHamgurgerIconClick(View view) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.search_box)
    public void onSearchBoxClick(View view) {
        startSearchActivity(FROM_SEARCH_RESULT_ID, SearchType.ORIGIN);
    }
    /*---Main bar---*/

    /*---Action Buton to center the current location---*/
    @OnClick(R.id.center_location_action_button)
    public void onCenterLocationButtonClick(View view) {
        if (DeviceRequirementsChecker.checkGpsEnabled(this)) {
            mMyBusMap.centerToLastKnownLocation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ButterKnife.bind(this);
        //check if play services are intalled and updated
        if (!PlayServicesChecker.checkPlayServices(mContext)) {
            //if not, request to open the play store
            PlayServicesChecker.buildAlertMessageUpdatePlayServices(mContext);
            return;
        }

        mMyBusMap = new MyBusMap(this);
        mMyBusMap.resetLocalVariables();

        initDrawer();
        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMyBusMap.setLocationUpdater(new LocationUpdater(this, this));
        //Disable the mPerformSearchButton action
        mCompoundSearchBox.setSearchEnabled(false);
        setupBottomSheet();
        DeviceRequirementsChecker.checkGpsEnabled(this);
        mCompoundSearchBox.setListener(this);
    }

    private void initDrawer() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void setupBottomSheet() {
        mBottomSheet.setVisibility(View.INVISIBLE);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setPeekHeight(mMyBusMap.dpToPx(BOTTOM_SHEET_PEEK_HEIGHT_DP));
    }

    /**
     * Bottom Sheet Tab selected listener
     * <p/>
     * Expands the bottom sheet when the user re-selects any tab
     */
    private final TabLayout.ViewPagerOnTabSelectedListener mOnTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition(), true);
            mViewPager.requestLayout();
            mBottomSheet.requestLayout();

            if (isBusRouteFragmentPresent(tab.getPosition())) {
                boolean isMapBusRoadPresent = mViewPagerAdapter.getItem(tab.getPosition()).getMapBusRoad() != null;
                if (isMapBusRoadPresent) {
                    mViewPagerAdapter.getItem(tab.getPosition()).showMapBusRoad(true);
                    MapBusRoad mapBusRoad = mViewPagerAdapter.getItem(tab.getPosition()).getMapBusRoad();
                    List<Marker> markerList = new ArrayList<>();
                    markerList.addAll(mapBusRoad.getMarkerList());
                    markerList.add(mMyBusMap.getStartLocationMarker().getMapMarker());
                    markerList.add(mMyBusMap.getEndLocationMarker().getMapMarker());
                    mMyBusMap.zoomOut(markerList);
                } else {
                    BusRouteResult busRouteResult = mViewPagerAdapter.getItem(tab.getPosition()).getBusRouteResult();
                    performRoadSearch(busRouteResult);
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            hideCurrentBusRouteOnMap(tab.getPosition());
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMyBusMap.setMap(googleMap);
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        mMyBusMap.onLocationChanged(latLng);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onRouteFound(List<BusRouteResult> results) {
        cancelProgressDialog();
        mMyBusMap.removeChargingPointMarkers();
        if (results == null || results.isEmpty()) {
            showBottomSheetResults(false);
            mViewPagerAdapter = null;
            Toast.makeText(this, R.string.toast_no_result_found, Toast.LENGTH_LONG).show();
            return;
        } else {
            startResultsActivity((ArrayList<BusRouteResult>) results);
        }
    }

    /**
     * Populates the bottom sheet with the Routes found
     */
    private void populateBottomSheet(List<BusRouteResult> results, int busResultId) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.this.getLayoutInflater());
        for (BusRouteResult route : results) {
            BusRouteFragment fragment = new BusRouteFragment();
            fragment.setBusRouteResult(route);
            mViewPagerAdapter.addFragment(fragment);
        }
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(mOnTabSelectedListener);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(mViewPagerAdapter.getTabView(mTabLayout, results.get(i)));
        }
        showBottomSheetResults(true);
        mTabLayout.getTabAt(busResultId).select();
    }

    /**
     * Searchs a specific route for a bus
     *
     * @param busRouteResult
     */
    private void performRoadSearch(BusRouteResult busRouteResult) {
        if (busRouteResult == null) {
            return;
        }
        showProgressDialog(getString(R.string.dialog_searching_specific_route));
        ServiceFacade.getInstance().searchRoads(busRouteResult.getType(), busRouteResult,
                mMyBusMap.getStartLocationMarker().getMapMarker().getPosition(), mMyBusMap.getEndLocationMarker().getMapMarker().getPosition(), MainActivity.this);
    }

    @Override
    public void onRoadFound(RoadResult roadResult) {
        cancelProgressDialog();
        MapBusRoad mapBusRoad = new MapBusRoad().addBusRoadOnMap(mMyBusMap.getMap(), roadResult.getMarkerOptions(), roadResult.getPolylineOptions());
        if (isBusRouteFragmentPresent(mViewPager.getCurrentItem())) {
            mViewPagerAdapter.getItem(mViewPager.getCurrentItem()).setMapBusRoad(mapBusRoad);
            List<Marker> markerList = new ArrayList<>();
            markerList.addAll(mapBusRoad.getMarkerList());
            markerList.add(mMyBusMap.getStartLocationMarker().getMapMarker());
            markerList.add(mMyBusMap.getEndLocationMarker().getMapMarker());
            mMyBusMap.zoomOut(markerList);
        }
    }


    /**
     * Hide all markers and polyline for a previous route
     * @param position
     */
    private void hideCurrentBusRouteOnMap(int position) {
        if (isBusRouteFragmentPresent(position)) {
            mViewPagerAdapter.getItem(position).showMapBusRoad(false);
        }
    }

    /**
     * Clears all markers and polyline
     */
    public void clearBusRouteOnMap() {
        if (mViewPagerAdapter != null) {
            mViewPagerAdapter.clearBusRoutes();
        }
        mMyBusMap.hideBusRoutes();
    }

    /**
     * Checks if the BusRouteFragment is present or not
     *
     * @param index
     * @return
     */
    private boolean isBusRouteFragmentPresent(int index) {
        return (mViewPagerAdapter != null && mViewPagerAdapter.getItem(index) != null);
    }

    /**
     * Show or hide the bottom sheet Layout
     *
     * @param show
     */
    public void showBottomSheetResults(boolean show) {
        if (mBottomSheet != null) {
            if (show) {
                mBottomSheet.setVisibility(View.VISIBLE);
            } else {
                mBottomSheet.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //TODO: Implement the item selected actions
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.drawerCosts:
                startActivity(new Intent(MainActivity.this, DisplayFaresActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.drawerFavorites:
                Intent favIntent = new Intent(MainActivity.this, DisplayFavoritesActivity.class);
                startActivityForResult(favIntent, DISPLAY_FAVORITES_RESULT);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.drawerRoads:
                Intent roadsIntent = new Intent(MainActivity.this, DisplayBusLinesActivity.class);
                startActivityForResult(roadsIntent, DISPLAY_ROADS_RESULT);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.about:
                AboutAlertDialog aboutAlertDialog = new AboutAlertDialog();
                aboutAlertDialog.show(getFragmentManager(), "");
                break;
            case R.id.drawerCharge:
                showProgressDialog(getString(R.string.dialog_searching_loading_points));
                ServiceFacade.getInstance().getNearChargingPoints(mMyBusMap.getLocationUpdater().getLastKnownLocation(), MainActivity.this);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * @param requestCode
     */
    private void startSearchActivity(int requestCode, int type) {
        Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
        searchIntent.putExtra(SearchActivity.SEARCH_TYPE_EXTRA, type);
        startActivityForResult(searchIntent, requestCode);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            if (requestCode == DISPLAY_BUS_LINES_RESULT) {
                //clear the map
                onBackArrowClick();
            }
        }
        if (resultCode == RESULT_OK) {
            mMyBusMap.removeChargingPointMarkers();
            GeoLocation geoLocation = data.getParcelableExtra(SearchActivity.RESULT_GEOLOCATION_EXTRA);
            boolean isFavorite = data.getBooleanExtra(SearchActivity.RESULT_ISFAVORITE_EXTRA, false);
            String favName = data.getStringExtra(SearchActivity.RESULT_FAVORITE_NAME_EXTRA);
            switch (requestCode) {
                case FROM_SEARCH_RESULT_ID:
                    mMyBusMap.updateFromInfo(geoLocation, favName, isFavorite);
                    break;
                case TO_SEARCH_RESULT_ID:
                    mMyBusMap.updateToInfo(geoLocation, favName, isFavorite);
                    break;
                case DISPLAY_FAVORITES_RESULT:
                    mMyBusMap.disPlayFavoritesResults(data);
                    break;
                case DISPLAY_ROADS_RESULT:
                    int busLineId = data.getIntExtra(DisplayBusLinesActivity.RESULT_BUS_LINE_ID, -1);
                    String busLineName = data.getStringExtra(DisplayBusLinesActivity.RESULT_BUS_LINE_NAME);
                    showCompleteBusRoute(busLineId, busLineName);
                    break;
                case DISPLAY_BUS_LINES_RESULT:
                    updateAfterBusLineResult(data);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showCompleteBusRoute(int busLineId, String busLineName) {
        clearBusRouteOnMap();
        //Check if the complete route is present in cache.
        if (mMyBusMap.completeRouteExists(busLineId)) {
            mMyBusMap.showCompleteBusRoute(busLineId);
        } else {
            showProgressDialog(getString(R.string.searching_complete_route));
            ServiceFacade.getInstance().getCompleteBusRoute(busLineId, busLineName, this);
        }
    }

    private void updateAfterBusLineResult(Intent data) {
        int busResultId = data.getIntExtra(BusResultsActivity.SELECTED_BUS_LINE_EXTRA, -1);
        GeoLocation startGeoLocation = data.getParcelableExtra(BusResultsActivity.START_GEOLOCATION_EXTRA);
        GeoLocation endGeoLocation = data.getParcelableExtra(BusResultsActivity.END_GEOLOCATION_EXTRA);
        List<BusRouteResult> results = data.getParcelableArrayListExtra(BusResultsActivity.RESULTS_EXTRA);
        //update the varialbes with the new addresses
        mCompoundSearchBox.setFromAddress(startGeoLocation.getAddress());
        mCompoundSearchBox.setToAddress(endGeoLocation.getAddress());
        mMyBusMap.getStartLocationMarker().getMapMarker().setPosition(startGeoLocation.getLatLng());
        mMyBusMap.getEndLocationMarker().getMapMarker().setPosition(endGeoLocation.getLatLng());
        mMyBusMap.getStartLocationMarker().getMapMarker().setSnippet(startGeoLocation.getAddress());
        mMyBusMap.getEndLocationMarker().getMapMarker().setSnippet(endGeoLocation.getAddress());
        if (busResultId != -1 && results != null) {
            populateBottomSheet(results, busResultId);
        }
    }

    @Override
    public void onFromClick() {
        startSearchActivity(FROM_SEARCH_RESULT_ID, SearchType.ORIGIN);
    }

    @Override
    public void onToClick() {
        startSearchActivity(TO_SEARCH_RESULT_ID, SearchType.DESTINATION);
    }

    @Override
    public void onBackArrowClick() {
        mMyBusMap.cleanMap();
    }

    @Override
    public void onFlipSearchClick() {
        mMyBusMap.flipMarkers();
    }

    @Override
    public void onSearchButtonClick() {
        if (mMyBusMap.getStartLocationMarker().getMapMarker() == null || mMyBusMap.getEndLocationMarker().getMapMarker() == null) {
            return;
        }
        if (DeviceRequirementsChecker.isNetworkAvailable(this)) {
            clearBusRouteOnMap();
            showBottomSheetResults(false);
            showProgressDialog(getString(R.string.toast_searching));
            ServiceFacade.getInstance().searchRoutes(mMyBusMap.getStartLocationMarker().getMapMarker().getPosition(), mMyBusMap.getEndLocationMarker().getMapMarker().getPosition(), this);
        } else {
            Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
        //when performing a search remove all the favorites in the map
        mMyBusMap.removeAllFavoritesMarkers();
    }

    @Override
    public void onNewFavoriteName(FavoriteLocation favoriteLocation) {
        if (FavoriteLocationDao.getInstance(this).saveOrUpdate(favoriteLocation)) {
            if (mMarkerFavoriteToUpdate != null) {
                mMarkerFavoriteToUpdate.setAsFavorite(true);
                mMarkerFavoriteToUpdate.setFavoriteName(favoriteLocation.getName());
                mMarkerFavoriteToUpdate.getMapMarker().showInfoWindow();
                mMarkerFavoriteToUpdate = null;
            }
        }
    }

    @Override
    public void onEditFavoriteName(FavoriteLocation favoriteLocation) {
    }

    @Override
    public void onOkFavoriteAlertConfirmClicked(FavoriteAlertDialogConfirm dialog) {
        if (dialog.getDialogType().equals(FavoriteAlertDialogConfirm.ADD)) {
            addFavorite(dialog.getMarker(), dialog.getFavoriteLocation());
        }
        if (dialog.getDialogType().equals(FavoriteAlertDialogConfirm.REMOVE)) {
            removeFavorite(dialog.getMarker());
        }
    }

    private void addFavorite(MyBusMarker marker, FavoriteLocation favLocation) {
        mMarkerFavoriteToUpdate = marker;
        //Open dialog to enter the favorite name
        FavoriteNameAlertDialog favoriteNameAlertDialog = FavoriteNameAlertDialog.
                newInstance(FavoriteNameAlertDialog.TYPE_ADD, null, favLocation);
        favoriteNameAlertDialog.show(getFragmentManager(), "Favorite Name Dialog");
    }

    private void removeFavorite(MyBusMarker marker) {
        FavoriteLocation favoriteLocation = FavoriteLocationDao.getInstance(mContext).getItemByLatLng(marker.getMapMarker().getPosition());
        if (favoriteLocation != null) {
            if (FavoriteLocationDao.getInstance(mContext).remove(favoriteLocation.getId())) {
                //Update marker if favorite was successfully removed
                if (marker.getType().equals(MyBusMarker.ORIGIN)) {
                    mMyBusMap.setMarkerTitle(marker, getString(R.string.start_location_title), marker.getMapMarker().getSnippet());
                } else if (marker.getType().equals(MyBusMarker.DESTINATION)) {
                    mMyBusMap.setMarkerTitle(marker, getString(R.string.end_location_title), marker.getMapMarker().getSnippet());
                }
                marker.setAsFavorite(false);
                marker.getMapMarker().showInfoWindow();
            }
        }
    }

    @Override
    public void onChargingPointsFound(List<ChargePoint> chargePoints) {
        //Removing all markers and states
        onBackArrowClick();
        cancelProgressDialog();

        if (chargePoints != null && !chargePoints.isEmpty()) {
            List<Marker> markerList = new ArrayList<>();
            MarkerOptions options = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.charge));
            for (ChargePoint chargePoint : chargePoints) {
                options.title(chargePoint.getName());
                options.snippet(chargePoint.getAddress());

                MyBusMarker chargingPointMarker = new MyBusMarker(options, false, null, MyBusMarker.CHARGING_POINT);
                mMyBusMap.addOrUpdateMarker(chargingPointMarker, chargePoint.getLatLng(), null);
                mMyBusMap.getChargingPointMarkers().put(chargePoint.getLatLng(), chargingPointMarker);
                mMyBusMap.getChargingPoints().put(chargingPointMarker, chargePoint);

                markerList.add(chargingPointMarker.getMapMarker());
            }
            markerList.add(mMyBusMap.getUserLocationMarker().getMapMarker());
            mMyBusMap.zoomOut(markerList, getResources().getInteger(R.integer.charging_point_padding));
        } else {
            Toast.makeText(this, R.string.toast_no_loading_point_found, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param results
     */
    private void startResultsActivity(ArrayList<BusRouteResult> results) {
        Intent busResultsIntent = new Intent(MainActivity.this, BusResultsActivity.class);
        busResultsIntent.putExtra(BusResultsActivity.RESULTS_EXTRA, results);
        GeoLocation startGeoLocation = new GeoLocation(mCompoundSearchBox.getFromAddress(), mMyBusMap.getStartLocationMarker().getMapMarker().getPosition());
        busResultsIntent.putExtra(BusResultsActivity.START_GEOLOCATION_EXTRA, startGeoLocation);
        GeoLocation endGeoLocation = new GeoLocation(mCompoundSearchBox.getToAddress(), mMyBusMap.getEndLocationMarker().getMapMarker().getPosition());
        busResultsIntent.putExtra(BusResultsActivity.END_GEOLOCATION_EXTRA, endGeoLocation);
        startActivityForResult(busResultsIntent, DISPLAY_BUS_LINES_RESULT);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onCompleteRouteFound(int busLineId, CompleteBusRoute completeBusRoute) {
        cancelProgressDialog();
        mMyBusMap.showCompleteRoute(busLineId, completeBusRoute);
    }

    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    public int getToolbarId() {
        return 0;
    }

    @Override
    protected int getToolbarTittle() {
        return 0;
    }
}