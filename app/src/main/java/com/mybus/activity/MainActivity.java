package com.mybus.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mybus.R;
import com.mybus.adapter.StreetAutoCompleteAdapter;
import com.mybus.adapter.ViewPagerAdapter;
import com.mybus.asynctask.RoadSearchCallback;
import com.mybus.asynctask.RoadSearchTask;
import com.mybus.asynctask.RouteSearchCallback;
import com.mybus.asynctask.RouteSearchTask;
import com.mybus.fragment.BusRouteFragment;
import com.mybus.listener.AppBarStateChangeListener;
import com.mybus.listener.CustomAutoCompleteClickListener;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationChangedCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.MapBusRoad;
import com.mybus.model.Road.RoadResult;
import com.mybus.requirements.DeviceRequirementsChecker;
import com.mybus.service.ServiceFacade;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, OnLocationChangedCallback,
        OnAddressGeocodingCompleteCallback, OnLocationGeocodingCompleteCallback, RouteSearchCallback, RoadSearchCallback, NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";
    private GoogleMap mMap;
    private LocationUpdater mLocationUpdater;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.floating_action_button)
    FloatingActionButton mFloatingSearchButton;
    @Bind(R.id.center_location_action_button)
    FloatingActionButton mCenterLocationButton;
    @Bind(R.id.perform_search_action_button)
    FloatingActionButton mPerformSearchButton;
    @Bind(R.id.from_field)
    AppCompatAutoCompleteTextView mFromInput;
    @Bind(R.id.to_field)
    AppCompatAutoCompleteTextView mToInput;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    MarkerOptions mUserLocationMarkerOptions;
    //Marker used to update the location on the map
    Marker mUserLocationMarker;
    //Marker used to show the Start Location
    Marker mStartLocationMarker;
    //Marker used to show the End Location
    Marker mEndLocationMarker;
    //Temporary Marker
    MarkerOptions mStartLocationMarkerOptions;
    MarkerOptions lastAddressGeocodingType;
    MarkerOptions lastLocationGeocodingType;

    MarkerOptions mEndLocationMarkerOptions;
    /*---Bottom Sheet------*/
    //Keeps the state of the app bar
    private AppBarStateChangeListener.State mAppBarState;
    private BottomSheetBehavior<LinearLayout> mBottomSheetBehavior;
    private ViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.bottom_sheet)
    LinearLayout mBottomSheet;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    private final int BOTTOM_SHEET_PEEK_HEIGHT = 100;
    private ProgressDialog mDialog;
    private Context mContext;

    /**
     * Checks the state of the AppBarLayout
     */
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            mAppBarState = state;
            if (state.equals(State.COLLAPSED)) {
                showSoftKeyBoard(false);
            }
        }
    };

    /**
     * Listener for To_TextView, makes the search when the user hits the magnifying glass
     */
    private TextView.OnEditorActionListener mOnEditorAndroidListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
            String address = tv.getText().toString();
            if ((tv.getId() == mFromInput.getId()) && actionId == EditorInfo.IME_ACTION_NEXT) {
                setMarkerTitle(mStartLocationMarker, mStartLocationMarkerOptions, address);
                lastAddressGeocodingType = mStartLocationMarkerOptions;
                ServiceFacade.getInstance().performGeocodeByAddress(address, MainActivity.this, mContext);
                mToInput.requestFocus();
                return true;
            }
            if ((tv.getId() == mToInput.getId()) && actionId == EditorInfo.IME_ACTION_SEARCH) {
                setMarkerTitle(mEndLocationMarker, mEndLocationMarkerOptions, address);
                lastAddressGeocodingType = mEndLocationMarkerOptions;
                ServiceFacade.getInstance().performGeocodeByAddress(address, MainActivity.this, mContext);
                showSoftKeyBoard(false);
                return true;
            }
            return false;
        }
    };

    /**
     * Listener for Map Long Click Listener for setting start or end locations.
     */
    private GoogleMap.OnMapLongClickListener mMapOnLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            if (mStartLocationMarker == null) {
                lastLocationGeocodingType = mStartLocationMarkerOptions;
                mStartLocationMarker = positionMarker(mStartLocationMarker, mStartLocationMarkerOptions, latLng, true);
                zoomTo(mStartLocationMarker.getPosition());
            } else {
                lastLocationGeocodingType = mEndLocationMarkerOptions;
                mEndLocationMarker = positionMarker(mEndLocationMarker, mEndLocationMarkerOptions, latLng, true);
                zoomOutStartEndMarkers(); // Makes a zoom out in the map to see both markers at the same time.
            }
        }
    };

    /**
     * Makes a zoom in the map using a LatLng
     * @param latLng
     */
    private void zoomTo(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, getResources().getInteger(R.integer.default_map_zoom)));
    }

    /**
     * Makes a zoom in the map using a LatLngBounds (created with one or several LatLng's)
     * @param bounds
     */
    private void zoomTo(LatLngBounds bounds) {
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, getResources().getInteger(R.integer.map_padding));
        mMap.animateCamera(cu);
    }

    /**
     * Makes a zoom out in the map to keep all the markers received in view.
     */
    private void zoomOut(List<Marker> markerList) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markerList) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            zoomTo(bounds);
    }

    /**
     * Makes a zoom out in the map to keep mStartLocationMarker and mEndLocationMarker visible.
     */
    private void zoomOutStartEndMarkers() {
        if (mStartLocationMarker != null && mStartLocationMarker.isVisible() && mEndLocationMarker != null && mEndLocationMarker.isVisible()) {
            List<Marker> markerList = new ArrayList<>();
            markerList.add(mStartLocationMarker);
            markerList.add(mEndLocationMarker);
            zoomOut(markerList);
        }
    }

    public Marker positionMarker(Marker marker, MarkerOptions markerOptions, LatLng latLng, boolean performGeocoding) {
        if (marker == null) {
            markerOptions.position(latLng);
            marker = mMap.addMarker(markerOptions);
        } else {
            marker.setPosition(latLng);
        }
        if (performGeocoding) {
            ServiceFacade.getInstance().performGeocodeByLocation(latLng, MainActivity.this, mContext);
        }
        //Update searchButton status
        if (mStartLocationMarker != null && markerOptions == mEndLocationMarkerOptions
                || mEndLocationMarker != null && markerOptions == mStartLocationMarkerOptions) {
            mPerformSearchButton.setAlpha(255);
            mPerformSearchButton.setEnabled(true);
        }
        return marker;
    }

    /**
     * Listener for the marker drag
     */
    private GoogleMap.OnMarkerDragListener mOnMarkerDragListener = new GoogleMap.OnMarkerDragListener() {

        @Override
        public void onMarkerDragStart(Marker marker) {
            marker.hideInfoWindow();
        }

        @Override
        public void onMarkerDrag(Marker marker) {
            marker.hideInfoWindow();
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            marker.hideInfoWindow();
            if (marker.getId().equals(mStartLocationMarker.getId())) {
                lastLocationGeocodingType = mStartLocationMarkerOptions;
            } else if (marker.getId().equals(mEndLocationMarker.getId())) {
                lastLocationGeocodingType = mEndLocationMarkerOptions;
            }
            ServiceFacade.getInstance().performGeocodeByLocation(marker.getPosition(), MainActivity.this, mContext);
            zoomOutStartEndMarkers();
        }
    };

    /**
     * Bottom Sheet Tab selected listener
     * <p/>
     * Expands the bottom sheet when the user re-selects any tab
     */
    private TabLayout.ViewPagerOnTabSelectedListener mOnTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mTabLayout.getTabAt(tab.getPosition()).getCustomView().setSelected(true);
            mTabLayout.setScrollPosition(tab.getPosition(), 0, true);
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
                    markerList.add(mStartLocationMarker);
                    markerList.add(mEndLocationMarker);
                    zoomOut(markerList);
                } else {
                    BusRouteResult busRouteResult = mViewPagerAdapter.getItem(tab.getPosition()).getBusRouteResult();
                    performRoadSearch(busRouteResult);
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            if (isBusRouteFragmentPresent(tab.getPosition())) {
                mViewPagerAdapter.getItem(tab.getPosition()).showMapBusRoad(false);
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    };


    @OnClick(R.id.floating_action_button)
    public void onFloatingSearchButton(View view) {
        mAppBarLayout.setExpanded(true, true);
        mFromInput.requestFocus();
        showSoftKeyBoard(true);
    }

    @OnClick(R.id.center_location_action_button)
    public void onCenterLocationButtonClick(View view) {
        if (DeviceRequirementsChecker.checkGpsEnabled(this)) {
            centerToLastKnownLocation();
        }
    }

    @OnClick(R.id.perform_search_action_button)
    public void onPerformSearchButtonClick(View view) {
        performRoutesSearch();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mAppBarLayout.addOnOffsetChangedListener(mOnOffsetChangedListener);

        StreetAutoCompleteAdapter autoCompleteAdapter = new StreetAutoCompleteAdapter(MainActivity.this);

        mFromInput.setAdapter(autoCompleteAdapter);
        mFromInput.setOnEditorActionListener(mOnEditorAndroidListener);
        mFromInput.setOnItemClickListener(new CustomAutoCompleteClickListener(mFromInput));

        mToInput.setAdapter(autoCompleteAdapter);
        mToInput.setOnItemClickListener(new CustomAutoCompleteClickListener(mToInput));
        mToInput.setOnEditorActionListener(mOnEditorAndroidListener);

        mLocationUpdater = new LocationUpdater(this, this);
        mUserLocationMarkerOptions = new MarkerOptions()
                .title(getString(R.string.current_location_marker))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot));
        //Disable the mPerformSearchButton action
        mPerformSearchButton.setAlpha(50);
        mPerformSearchButton.setEnabled(false);
        resetLocalVariables();
        setupBottomSheet();
        DeviceRequirementsChecker.checkGpsEnabled(this);
    }

    private void setupBottomSheet() {
        mBottomSheet.setVisibility(View.INVISIBLE);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setPeekHeight(BOTTOM_SHEET_PEEK_HEIGHT);
    }

    /**
     * This method restart the local variables to avoid old apps's states
     */
    private void resetLocalVariables() {
        mUserLocationMarkerOptions = null;
        mUserLocationMarker = null;
        mStartLocationMarker = null;
        mEndLocationMarker = null;
        lastAddressGeocodingType = null;
        lastLocationGeocodingType = null;
        mStartLocationMarkerOptions = new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_origen))
                .title("origen");
        mEndLocationMarkerOptions = new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_destino))
                .title("destino");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mUserLocationMarkerOptions = new MarkerOptions()
                .title(getString(R.string.current_location_marker))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot));
        mMap = googleMap;
        mLocationUpdater.startListening();
        centerToLastKnownLocation();

        mMap.setOnMapLongClickListener(mMapOnLongClickListener);
        mMap.setOnMarkerDragListener(mOnMarkerDragListener);
    }

    public void centerToLastKnownLocation() {
        //get the last gps location
        LatLng lastLocation = mLocationUpdater.getLastKnownLocation();
        if (lastLocation != null) {
            mUserLocationMarkerOptions.position(lastLocation);
            //if the marker is not on the map, add it
            if (mUserLocationMarker == null) {
                mUserLocationMarker = mMap.addMarker(mUserLocationMarkerOptions);
            }
            zoomTo(mLocationUpdater.getLastKnownLocation());
        }
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        if (mUserLocationMarker != null) {
            mUserLocationMarker.setPosition(latLng);
        }
    }

    /**
     * Show or hide the soft keyboard
     *
     * @param show
     */
    private void showSoftKeyBoard(boolean show) {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            if (show) {
                imm.showSoftInput(mFromInput, InputMethodManager.SHOW_IMPLICIT);
            } else {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onAddressGeocodingComplete(LatLng location) {
        if (location != null) {
            if (lastAddressGeocodingType == mStartLocationMarkerOptions) {
                mStartLocationMarker = positionMarker(mStartLocationMarker, mStartLocationMarkerOptions, location, false);
                if (mEndLocationMarker == null || !mEndLocationMarker.isVisible()) {
                    zoomTo(location);
                }
            }
            if (lastAddressGeocodingType == mEndLocationMarkerOptions) {
                mEndLocationMarker = positionMarker(mEndLocationMarker, mEndLocationMarkerOptions, location, false);
                if (mStartLocationMarker ==  null || !mStartLocationMarker.isVisible()) {
                    zoomTo(location);
                }
            }
            zoomOutStartEndMarkers();
        }
    }

    private void setMarkerTitle(Marker marker, MarkerOptions markerOptions, String title) {
        if (marker != null) {
            marker.setTitle(title);
            marker.showInfoWindow();
        }
        markerOptions.title(title);
    }

    @Override
    public void onLocationGeocodingComplete(String address) {
        if (address != null) {
            if (lastLocationGeocodingType == mStartLocationMarkerOptions) {
                setMarkerTitle(mStartLocationMarker, mStartLocationMarkerOptions, address);
                mFromInput.setText(address);
            }
            if (lastLocationGeocodingType == mEndLocationMarkerOptions) {
                setMarkerTitle(mEndLocationMarker, mEndLocationMarkerOptions, address);
                mToInput.setText(address);
            }
        }
    }

    /**
     * Searches between two points in the map
     */
    private void performRoutesSearch() {
        if (mStartLocationMarker == null || mEndLocationMarker == null) {
            return;
        }
        if (DeviceRequirementsChecker.isNetworkAvailable(this)) {
            clearBusRouteOnMap();
            showBottomSheetResults(false);
            showProgressDialog(getString(R.string.toast_searching));
            RouteSearchTask routeSearchTask = new RouteSearchTask(this);
            routeSearchTask.execute(mStartLocationMarker.getPosition(), mEndLocationMarker.getPosition());
        } else {
            Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
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
        clearBusRouteOnMap();
        showProgressDialog(getString(R.string.dialog_searching_specific_route));
        RoadSearchTask routeSearchTask = new RoadSearchTask(busRouteResult.getType(), busRouteResult, MainActivity.this);
        routeSearchTask.execute();
    }

    @Override
    public void onRouteFound(List<BusRouteResult> results) {
        cancelProgressDialog();
        populateBottomSheet(results);
    }

    @Override
    public void onRoadFound(RoadResult roadResult) {
        cancelProgressDialog();
        MapBusRoad mapBusRoad = new MapBusRoad().addBusRoadOnMap(mMap, roadResult);
        if (isBusRouteFragmentPresent(mViewPager.getCurrentItem())) {
            mViewPagerAdapter.getItem(mViewPager.getCurrentItem()).setMapBusRoad(mapBusRoad);
            List<Marker> markerList = new ArrayList<>();
            markerList.addAll(mapBusRoad.getMarkerList());
            markerList.add(mStartLocationMarker);
            markerList.add(mEndLocationMarker);
            zoomOut(markerList);
        }
    }

    /**
     * Populates the bottom sheet with the Routes found
     *
     * @param results
     */
    private void populateBottomSheet(List<BusRouteResult> results) {
        if (results == null || results.isEmpty()) {
            showBottomSheetResults(false);
            mViewPagerAdapter = null;
            Toast.makeText(this, R.string.toast_no_result_found, Toast.LENGTH_LONG).show();
            return;
        }
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
        //After populating the bottom sheet, show the first result
        BusRouteResult busRouteResult = mViewPagerAdapter.getItem(0).getBusRouteResult();
        performRoadSearch(busRouteResult);

    }

    /**
     * Shows a progress dialog with specified text
     *
     * @param text
     */
    private void showProgressDialog(String text) {
        cancelProgressDialog();
        mDialog = ProgressDialog.show(MainActivity.this, "", text, true, false);
    }

    /**
     * Cancels the current progress dialog if any
     */
    private void cancelProgressDialog() {
        if (mDialog != null) {
            mDialog.cancel();
            mDialog = null;
        }
    }

    /**
     * Clears all markers and polyline for a previous route
     */
    private void clearBusRouteOnMap() {
        if (isBusRouteFragmentPresent(mViewPager.getCurrentItem())) {
            mViewPagerAdapter.getItem(mViewPager.getCurrentItem()).showMapBusRoad(false);
        }
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
    private void showBottomSheetResults(boolean show) {
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
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.drawerCosts:
                startActivity(new Intent(MainActivity.this, DisplayFaresActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.drawerFavorites:
                startActivity(new Intent(MainActivity.this, DisplayFavoritesActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
        }
        return true;
    }


}