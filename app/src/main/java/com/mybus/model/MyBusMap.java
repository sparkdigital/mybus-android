package com.mybus.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mybus.R;
import com.mybus.activity.DisplayFavoritesActivity;
import com.mybus.activity.MainActivity;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnLocationChangedCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.marker.MyBusInfoWindowsAdapter;
import com.mybus.marker.MyBusMarker;
import com.mybus.model.road.MapBusRoad;
import com.mybus.service.ServiceFacade;
import com.mybus.view.FavoriteAlertDialogConfirm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to handle all the features related with the GoogleMap
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class MyBusMap implements OnLocationChangedCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLongClickListener {
    /*-- Local Variables --*/
    private Context mContext;
    private GoogleMap mMap;
    private LocationUpdater mLocationUpdater;
    //Marker used to update the location on the map
    private MyBusMarker mUserLocationMarker;
    //Marker used to show the Start Location
    private MyBusMarker mStartLocationMarker;
    //Marker used to show the End Location
    private MyBusMarker mEndLocationMarker;
    //List of chargingPoint markers
    private HashMap<LatLng, MyBusMarker> mChargingPointMarkers;
    private HashMap<MyBusMarker, ChargePoint> mChargingPoints;
    //Favorite MyBusMarker List
    private HashMap<LatLng, MyBusMarker> mFavoritesMarkers;
    //HashMap used as cache for complete bus routes
    private HashMap<Integer, MapBusRoad> mCompleteRoutes = new HashMap<>();
    private MainActivity mMainActivity;

    /**
     * Public contractor, this object need the MainActivity object to interact with it
     * in order to modify the toolbar, compoundSearchBox and bottomSheet.
     *
     * @param mainActivity
     */
    public MyBusMap(MainActivity mainActivity) {
        this.mContext = mainActivity;
        mMainActivity = mainActivity;
    }

    /*--- GETTERS / SETTERS---*/
    public GoogleMap getMap() {
        return mMap;
    }

    public MyBusMarker getUserLocationMarker() {
        return mUserLocationMarker;
    }

    public MyBusMarker getStartLocationMarker() {
        return mStartLocationMarker;
    }

    public MyBusMarker getEndLocationMarker() {
        return mEndLocationMarker;
    }

    public LocationUpdater getLocationUpdater() {
        return mLocationUpdater;
    }

    public void setLocationUpdater(LocationUpdater mLocationUpdater) {
        this.mLocationUpdater = mLocationUpdater;
    }

    public HashMap<LatLng, MyBusMarker> getChargingPointMarkers() {
        return mChargingPointMarkers;
    }

    public HashMap<MyBusMarker, ChargePoint> getChargingPoints() {
        return mChargingPoints;
    }

    public Context getContext() {
        return mContext;
    }

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
        mLocationUpdater.startListening();
        mUserLocationMarker = new MyBusMarker(new MarkerOptions()
                .title(mContext.getString(R.string.current_location_marker))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot)), false, null, MyBusMarker.USER_LOCATION);
        centerToLastKnownLocation();

        mMap.setInfoWindowAdapter(new MyBusInfoWindowsAdapter(this));
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(mOnMarkerDragListener);
        mMap.setOnInfoWindowClickListener(this);
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    /**
     * This method restart the local variables to avoid old apps's states
     */
    public void resetLocalVariables() {
        mFavoritesMarkers = new HashMap<>();
        mChargingPointMarkers = new HashMap<>();
        mChargingPoints = new HashMap<>();
        mStartLocationMarker = new MyBusMarker(new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.from_mappin))
                .title(mContext.getString(R.string.start_location_title)), false, null, MyBusMarker.ORIGIN);
        mEndLocationMarker = new MyBusMarker(new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.to_mappin))
                .title(mContext.getString(R.string.end_location_title)), false, null, MyBusMarker.DESTINATION);
    }

    /*--- LOCAL LISTENERS: ---*/

    /**
     * Listener for start marker geocoding process
     */
    private final OnLocationGeocodingCompleteCallback mStartLocationGeocodingCompleted =
            new OnLocationGeocodingCompleteCallback() {
                @Override
                public void onLocationGeocodingComplete(GeoLocation geoLocation) {
                    if (geoLocation != null) {
                        mStartLocationMarker.setAsFavorite(false);
                        setAddressFromGeoCoding(geoLocation.getAddress(), mStartLocationMarker, mContext.getString(R.string.start_location_title));
                        mMainActivity.getCompoundSearchBox().setFromAddress(geoLocation.getAddress());
                    }
                }
            };

    /**
     * Listener for end marker geocoding process
     */
    private final OnLocationGeocodingCompleteCallback mEndLocationGeocodingCompleted =
            new OnLocationGeocodingCompleteCallback() {
                @Override
                public void onLocationGeocodingComplete(GeoLocation geoLocation) {
                    if (geoLocation != null) {
                        mEndLocationMarker.setAsFavorite(false);
                        setAddressFromGeoCoding(geoLocation.getAddress(), mEndLocationMarker, mContext.getString(R.string.end_location_title));
                        mMainActivity.getCompoundSearchBox().setToAddress(geoLocation.getAddress());
                    }
                }
            };

    /**
     * Listener for the marker drag
     */
    private final GoogleMap.OnMarkerDragListener mOnMarkerDragListener = new GoogleMap.OnMarkerDragListener() {

        @Override
        public void onMarkerDragStart(Marker marker) {
            marker.hideInfoWindow();
            removeChargingPointMarkers();
            mMainActivity.clearBusRouteOnMap();
            mMainActivity.showBottomSheetResults(false);
        }

        @Override
        public void onMarkerDrag(Marker marker) {
            marker.hideInfoWindow();
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            marker.hideInfoWindow();
            OnLocationGeocodingCompleteCallback listener = null;
            if (marker.getId().equals(mStartLocationMarker.getMapMarker().getId())) {
                listener = mStartLocationGeocodingCompleted;
            } else if (marker.getId().equals(mEndLocationMarker.getMapMarker().getId())) {
                listener = mEndLocationGeocodingCompleted;
            }
            ServiceFacade.getInstance().performGeocodeByLocation(marker.getPosition(), listener, mContext);
            zoomOutStartEndMarkers();
        }
    };

    /**
     * Sets the marker title with the specified address
     * Hides the toolbar and shows the compound search box
     *
     * @param address
     * @param marker
     * @param title
     */
    private void setAddressFromGeoCoding(String address, MyBusMarker marker, String title) {
        setMarkerTitle(marker, title, address);
        mMainActivity.getToolbar().setVisibility(View.GONE);
        mMainActivity.getCompoundSearchBox().setVisible(true);
    }

    public void cleanMap() {
        if (mStartLocationMarker.getMapMarker() != null) {
            mStartLocationMarker.getMapMarker().remove();
            mStartLocationMarker.setMapMarker(null);
            mStartLocationMarker.setAsFavorite(false);
        }
        if (mEndLocationMarker.getMapMarker() != null) {
            mEndLocationMarker.getMapMarker().remove();
            mEndLocationMarker.setMapMarker(null);
            mEndLocationMarker.setAsFavorite(false);
        }
        removeChargingPointMarkers();
        mMainActivity.showBottomSheetResults(false);
        mMainActivity.clearBusRouteOnMap();
        mMainActivity.getCompoundSearchBox().setVisible(false);
        mMainActivity.getToolbar().setVisibility(View.VISIBLE);
        removeAllFavoritesMarkers();
    }

    public void flipMarkers() {
        if (mStartLocationMarker.getMapMarker() == null || mEndLocationMarker.getMapMarker() == null) {
            return;
        }
        LatLng latLngAux = mStartLocationMarker.getMapMarker().getPosition();
        String addressAux = mStartLocationMarker.getMapMarker().getTitle();
        addOrUpdateMarker(mStartLocationMarker, mEndLocationMarker.getMapMarker().getPosition(), null);
        mStartLocationMarker.getMapMarker().setTitle(mEndLocationMarker.getMapMarker().getTitle());
        mStartLocationMarker.getMapMarker().hideInfoWindow();
        addOrUpdateMarker(mEndLocationMarker, latLngAux, null);
        mEndLocationMarker.getMapMarker().setTitle(addressAux);
        mEndLocationMarker.getMapMarker().hideInfoWindow();

        zoomOutStartEndMarkers();
    }

    /**
     * Add or update a specified marker on the map
     *
     * @param marker   the marker to be updated.
     * @param latLng   the LatLng where the marker is going to be
     * @param listener null if no Geocoding By Location needed.
     * @return the marker from the map
     */
    public void addOrUpdateMarker(MyBusMarker marker, LatLng latLng, OnLocationGeocodingCompleteCallback listener) {
        mMainActivity.clearBusRouteOnMap();
        mMainActivity.showBottomSheetResults(false);
        if (marker.getMapMarker() == null) {
            marker.getMarkerOptions().position(latLng);
            marker.setMapMarker(mMap.addMarker(marker.getMarkerOptions()));
        } else {
            marker.getMapMarker().setPosition(latLng);
        }
        if (listener != null) {
            ServiceFacade.getInstance().performGeocodeByLocation(latLng, listener, mContext);
        }
        //Update searchButton status
        boolean enableSearch = mStartLocationMarker.getMapMarker() != null && mEndLocationMarker.getMapMarker() != null;
        mMainActivity.getCompoundSearchBox().setSearchEnabled(enableSearch);
    }

    /**
     * Update myBusMarker's information with favorite name or default title.
     *
     * @param myBusMarker
     * @param title
     * @param defaultTitle
     * @param address
     * @param isFavorite
     */
    private void updateMyBusMarkerInfo(MyBusMarker myBusMarker, String title, String defaultTitle, String address, boolean isFavorite) {
        if (isFavorite) {
            myBusMarker.setAsFavorite(true);
            myBusMarker.setFavoriteName(title);
            setMarkerTitle(myBusMarker, title, address);
        } else {
            setMarkerTitle(myBusMarker, defaultTitle, address);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        removeChargingPointMarkers();
        updateMarkers(latLng);
    }


    private void updateMarkers(LatLng latLng) {
        if (mStartLocationMarker.getMapMarker() == null) {
            addOrUpdateMarker(mStartLocationMarker, latLng, mStartLocationGeocodingCompleted);
            zoomTo(mStartLocationMarker.getMapMarker().getPosition());
        } else {
            addOrUpdateMarker(mEndLocationMarker, latLng, mEndLocationGeocodingCompleted);
            zoomOutStartEndMarkers(); // Makes a zoom out in the map to see both markers at the same time.
        }
    }

    public void setMarkerTitle(MyBusMarker marker, String title, String address) {
        if (marker.getMapMarker() != null) {
            marker.getMapMarker().setTitle(title);
            marker.getMapMarker().setSnippet(address);
            marker.getMapMarker().showInfoWindow();
        }
        marker.getMarkerOptions().title(title);
        marker.getMarkerOptions().snippet(address);
    }

    public void hideBusRoutes() {
        //Hide complete bus routes
        for (MapBusRoad route : mCompleteRoutes.values()) {
            route.showBusRoadFromMap(false);
        }
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        //Some infoWindow was clicked
        //Detect which type of marker is:
        MyBusMarker myBusMarker = isMyBusMarker(marker);

        if (myBusMarker != null) {
            myBusMarker.getMapMarker().hideInfoWindow();
            switch (myBusMarker.getType()) {
                case MyBusMarker.ORIGIN: //Is a favorite creation or remove
                case MyBusMarker.DESTINATION: //Is a favorite creation or remove
                    if (myBusMarker.isFavorite()) { //Remove
                        FavoriteAlertDialogConfirm favAlert = FavoriteAlertDialogConfirm.newInstance(FavoriteAlertDialogConfirm.REMOVE,
                                mContext.getString(R.string.favorite_confirm_delete_title), mContext.getString(R.string.favorite_confirm_delete_message), null, myBusMarker);
                        favAlert.show(mMainActivity.getFragmentManager(), "Confirm Remove Dialog");
                    } else { //Add
                        FavoriteLocation newFavorite = new FavoriteLocation();
                        newFavorite.setAddress(myBusMarker.getMapMarker().getSnippet());
                        newFavorite.setLatitude(myBusMarker.getMapMarker().getPosition().latitude);
                        newFavorite.setLongitude(myBusMarker.getMapMarker().getPosition().longitude);
                        FavoriteAlertDialogConfirm favAlert = FavoriteAlertDialogConfirm.newInstance(FavoriteAlertDialogConfirm.ADD,
                                mContext.getString(R.string.favorite_confirm_add_title), mContext.getString(R.string.favorite_confirm_add_message), newFavorite, myBusMarker);
                        favAlert.show(mMainActivity.getFragmentManager(), "Confirm Add Dialog");
                    }
                    break;
                case MyBusMarker.USER_LOCATION:
                    useKnownLocationForRoute(mUserLocationMarker, mContext.getString(R.string.user_location_dialog_title), mContext.getString(R.string.user_location_dialog_message));
                    break;
                case MyBusMarker.FAVORITE:
                    useKnownLocationForRoute(myBusMarker, mContext.getString(R.string.use_favorite_dialog_title), mContext.getString(R.string.use_favorite_dialog_message));
                    break;
                case MyBusMarker.CHARGING_POINT:
                    marker.showInfoWindow();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Checks if the given marker is MyBusMarker (StartLocation, EndLocation, UserLocation or FavoriteMarker).
     *
     * @param marker
     * @return a MyBusMarker (StartLocation/EndLocation) or null
     */
    public MyBusMarker isMyBusMarker(Marker marker) {
        if (mStartLocationMarker != null && mStartLocationMarker.getMapMarker() != null && mStartLocationMarker.getMapMarker().getId().equals(marker.getId())) {
            return mStartLocationMarker;
        }
        if (mEndLocationMarker != null && mEndLocationMarker.getMapMarker() != null && mEndLocationMarker.getMapMarker().getId().equals(marker.getId())) {
            return mEndLocationMarker;
        }
        if (mUserLocationMarker != null && mUserLocationMarker.getMapMarker() != null && mUserLocationMarker.getMapMarker().getId().equals(marker.getId())) {
            return mUserLocationMarker;
        }
        if (!mFavoritesMarkers.isEmpty() && mFavoritesMarkers.containsKey(marker.getPosition())) {
            return mFavoritesMarkers.get(marker.getPosition());
        }
        if (!mChargingPointMarkers.isEmpty() && mChargingPointMarkers.containsKey(marker.getPosition())) {
            return mChargingPointMarkers.get(marker.getPosition());
        }
        return null;
    }

    public void showCompleteRoute(int busLineId, CompleteBusRoute completeBusRoute) {
        if (completeBusRoute.getGoingPointList().size() == 0 || completeBusRoute.getReturnPointList().size() == 0) {
            Toast.makeText(mContext, R.string.toast_no_complete_route, Toast.LENGTH_LONG).show();
        } else {
            //Save in the local HashMap
            mCompleteRoutes.put(busLineId, new MapBusRoad().addBusRoadOnMap(mMap, completeBusRoute.getMarkerOptions(), completeBusRoute.getPolylineOptions()));
            //Draw complete route:
            mCompleteRoutes.get(busLineId).showBusRoadFromMap(true);
            zoomOutCompleteBusRoute(busLineId);
        }
    }

    public boolean CompleteRouteExists(int busLineId) {
        return mCompleteRoutes.containsKey(busLineId);
    }

    public void showCompleteBusRoute(int busLineId) {
        mCompleteRoutes.get(busLineId).showBusRoadFromMap(true);
        zoomOutCompleteBusRoute(busLineId);
    }

    public void updateFromInfo(GeoLocation geoLocation, String favName, boolean isFavorite) {
        addOrUpdateMarker(mStartLocationMarker, geoLocation.getLatLng(), null);
        updateMyBusMarkerInfo(mStartLocationMarker, favName, mContext.getString(R.string.start_location_title), geoLocation.getAddress(), isFavorite);
        mMainActivity.getCompoundSearchBox().setFromAddress(geoLocation.getAddress());
        zoomTo(mStartLocationMarker.getMapMarker().getPosition());
        mMainActivity.getToolbar().setVisibility(View.GONE);
        mMainActivity.getCompoundSearchBox().setVisible(true, true);
    }

    public void updateToInfo(GeoLocation geoLocation, String favName, boolean isFavorite) {
        addOrUpdateMarker(mEndLocationMarker, geoLocation.getLatLng(), null);
        updateMyBusMarkerInfo(mEndLocationMarker, favName, mContext.getString(R.string.end_location_title), geoLocation.getAddress(), isFavorite);
        mMainActivity.getCompoundSearchBox().setFromAddress(geoLocation.getAddress());
        zoomTo(mEndLocationMarker.getMapMarker().getPosition());
        mMainActivity.getToolbar().setVisibility(View.GONE);
        mMainActivity.getCompoundSearchBox().setVisible(true, true);
    }

    /*--- ZOOM METHODS ---*/

    /**
     * Makes a zoom out in the map to keep all the markers received in view.
     */
    public void zoomOut(List<Marker> markerList, int padding) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markerList) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, dpToPx(padding));
        mMap.animateCamera(cu);
    }

    /**
     * Makes a zoom out in the map to keep all the markers received in view.
     */
    public void zoomOut(List<Marker> markerList) {
        zoomOut(markerList, mContext.getResources().getInteger(R.integer.map_padding));
    }

    /**
     * Makes a zoom in the map using a LatLng
     *
     * @param latLng
     */
    private void zoomTo(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mContext.getResources().getInteger(R.integer.default_map_zoom)));
    }

    /**
     * @param markerList
     */
    private void zoomOutFavorites(List<MyBusMarker> markerList) {
        List<Marker> markers = new ArrayList<Marker>();
        for (MyBusMarker myBusMarker : markerList) {
            markers.add(myBusMarker.getMapMarker());
        }
        zoomOut(markers, mContext.getResources().getInteger(R.integer.map_padding_favorites));
    }

    /**
     * Makes a zoom out in the map to keep mStartLocationMarker and mEndLocationMarker visible.
     */
    private void zoomOutStartEndMarkers() {
        if (mStartLocationMarker.getMapMarker() != null && mStartLocationMarker.getMapMarker().isVisible() && mEndLocationMarker.getMapMarker() != null && mEndLocationMarker.getMapMarker().isVisible()) {
            List<Marker> markerList = new ArrayList<>();
            markerList.add(mStartLocationMarker.getMapMarker());
            markerList.add(mEndLocationMarker.getMapMarker());
            zoomOut(markerList);
        }
    }

    private void zoomOutCompleteBusRoute(int busLineId) {
        List<Marker> markerList = new ArrayList<>();
        markerList.addAll(mCompleteRoutes.get(busLineId).getMarkerList());
        zoomOut(markerList, mContext.getResources().getInteger(R.integer.complete_route_padding));
    }

    /**
     * Returns pixels dimension from DensityPoints given the display metrics from the device
     *
     * @param dp
     * @return
     */
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Asks to user how want to use the marker selected (Origin or Destination)
     *
     * @param myBusMarker
     * @param title
     * @param message
     */
    private void useKnownLocationForRoute(final MyBusMarker myBusMarker, final String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(mContext.getString(R.string.start_location_title),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LatLng newLatLng = myBusMarker.getMapMarker().getPosition();
                        if (myBusMarker.isFavorite()) {
                            addOrUpdateMarker(mStartLocationMarker, newLatLng, null);
                            updateMyBusMarkerInfo(mStartLocationMarker, myBusMarker.getFavoriteName(), null, myBusMarker.getMapMarker().getSnippet(), true);
                            mMainActivity.getCompoundSearchBox().setFromAddress(myBusMarker.getMapMarker().getSnippet());
                            mMainActivity.getToolbar().setVisibility(View.GONE);
                            mMainActivity.getCompoundSearchBox().setVisible(true);
                            removeFavoriteMarker(myBusMarker);
                        } else {
                            addOrUpdateMarker(mStartLocationMarker, newLatLng, mStartLocationGeocodingCompleted);
                        }
                        zoomTo(mStartLocationMarker.getMapMarker().getPosition()); // Makes a zoom out in the map to see both markers at the same time.
                    }
                });
        builder.setPositiveButton(mContext.getString(R.string.end_location_title),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LatLng newLatLng = myBusMarker.getMapMarker().getPosition();
                        if (myBusMarker.isFavorite()) {
                            addOrUpdateMarker(mEndLocationMarker, newLatLng, null);
                            updateMyBusMarkerInfo(mEndLocationMarker, myBusMarker.getFavoriteName(), null, myBusMarker.getMapMarker().getSnippet(), true);
                            mMainActivity.getCompoundSearchBox().setToAddress(myBusMarker.getMapMarker().getSnippet());
                            mMainActivity.getToolbar().setVisibility(View.GONE);
                            mMainActivity.getCompoundSearchBox().setVisible(true);
                            removeFavoriteMarker(myBusMarker);
                        } else {
                            addOrUpdateMarker(mEndLocationMarker, newLatLng, mEndLocationGeocodingCompleted);
                        }
                        zoomOutStartEndMarkers(); // Makes a zoom out in the map to see both markers at the same time.
                    }
                });
        builder.setNegativeButton(mContext.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    /*--- FAVORITES: ---*/
    public void disPlayFavoritesResults(Intent data) {
        ArrayList<MyBusMarker> favoriteMarkers = data.getExtras().getParcelableArrayList(DisplayFavoritesActivity.RESULT_MYBUSMARKER);
        if (favoriteMarkers != null && !favoriteMarkers.isEmpty()) {
            mMainActivity.onBackArrowClick();
            for (MyBusMarker favMarker : favoriteMarkers) {
                favMarker.setMapMarker(mMap.addMarker(favMarker.getMarkerOptions()));
                //favMarker.getMapMarker().showInfoWindow();
                mFavoritesMarkers.put(favMarker.getMapMarker().getPosition(), favMarker); //TODO: Check if exists
            }
            zoomOutFavorites(favoriteMarkers);
        }
    }

    /**
     * Remove the given favorite marker from he HashMap and Map
     */
    private void removeFavoriteMarker(MyBusMarker favoriteMarker) {
        Marker marker = favoriteMarker.getMapMarker();
        if (marker != null) {
            mFavoritesMarkers.remove(marker.getPosition());
            marker.remove();
        }
    }

    /**
     * Remove all favorite markers present on the map and reset the HashMap
     */
    public void removeAllFavoritesMarkers() {
        for (MyBusMarker myBusMarker : mFavoritesMarkers.values()) {
            Marker marker = myBusMarker.getMapMarker();
            if (marker != null) {
                marker.remove();
            }
        }
        mFavoritesMarkers = new HashMap<>();
    }

    /*--- CHARGING POINTS: ---*/

    /**
     * Removes all charging point markers and clears the list
     */
    public void removeChargingPointMarkers() {
        for (MyBusMarker marker : mChargingPointMarkers.values()) {
            marker.getMapMarker().remove();
        }
        mChargingPointMarkers.clear();
        mChargingPoints.clear();
    }

    /**
     * Checks if given a MyBusMarker corresponds into a ChargePoint displayed in the Map
     *
     * @param myBusMarker
     * @return corresponding ChargePoint or null
     */
    public ChargePoint getChargePointPresent(MyBusMarker myBusMarker) {
        if (!mChargingPoints.isEmpty() && mChargingPoints.containsKey(myBusMarker)) {
            return mChargingPoints.get(myBusMarker);
        }
        return null;
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        if (mUserLocationMarker != null) {
            mUserLocationMarker.getMapMarker().setPosition(latLng);
        }
    }

    public void centerToLastKnownLocation() {
        //get the last gps location
        LatLng lastLocation = mLocationUpdater.getLastKnownLocation();
        if (lastLocation != null) {
            mUserLocationMarker.getMarkerOptions().position(lastLocation);
            //if the marker is not on the map, add it
            if (mUserLocationMarker.getMapMarker() == null) {
                mUserLocationMarker.setMapMarker(mMap.addMarker(mUserLocationMarker.getMarkerOptions()));
            } else {
                mUserLocationMarker.getMapMarker().setPosition(lastLocation);
            }
            zoomTo(mLocationUpdater.getLastKnownLocation());
        }
    }
}