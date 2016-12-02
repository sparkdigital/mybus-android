package com.mybus.model.road;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class MapBusRoad implements GoogleMap.OnCameraChangeListener{

    private List<Marker> mMarkerList = new ArrayList<>();
    private List<Marker> mStopsMarkers = new ArrayList<>();
    private List<Polyline> mPolylineList = new ArrayList<>();
    public static final float MIN_MAP_STOPS_DISTANCE = 13.5f;
    private GoogleMap mMap;

    public List<Marker> getMarkerList() {
        return mMarkerList;
    }

    /**
     * Adds the Specific road result into the map
     *
     * @param map
     * @param markerOptionsList
     * @param polylineOptionsList
     */
    public MapBusRoad addBusRoadOnMap(GoogleMap map, List<MarkerOptions> markerOptionsList, List<MarkerOptions> stopsMarkers, List<PolylineOptions> polylineOptionsList) {
        mMap = map;
        for (MarkerOptions markerOptions : markerOptionsList) {
            mMarkerList.add(map.addMarker(markerOptions));
        }
        if (stopsMarkers != null) {
            for (MarkerOptions markerOptions : stopsMarkers) {
                mStopsMarkers.add(map.addMarker(markerOptions));
            }
        }
        for (PolylineOptions polylineOptions : polylineOptionsList) {
            mPolylineList.add(map.addPolyline(polylineOptions));
        }
        map.setOnCameraChangeListener(this);
        return this;
    }

    private void showStopMarkers(boolean show) {
        for (Marker marker : mStopsMarkers) {
            marker.setVisible(show);
        }
    }

    /**
     * Removes the road result from the map
     */
    public void clearBusRoadFromMap() {
        for (Marker marker : mMarkerList) {
            marker.remove();
        }
        for (Marker marker : mStopsMarkers) {
            marker.remove();
        }
        for (Polyline polyline : mPolylineList) {
            polyline.remove();
        }
        mMarkerList.clear();
        mPolylineList.clear();
    }

    /**
     * Shows or hides the markers and polilynes from the map
     *
     * @param show
     */
    public void showBusRoadFromMap(boolean show) {
        for (Marker marker : mMarkerList) {
            marker.setVisible(show);
        }
        for (Marker marker : mStopsMarkers) {
            marker.setVisible(show);
        }
        for (Polyline polyline : mPolylineList) {
            polyline.setVisible(show);
        }
        if (show) {
            mMap.setOnCameraChangeListener(this);
        } else {
            mMap.setOnCameraChangeListener(null);
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        showStopMarkers(cameraPosition.zoom > MIN_MAP_STOPS_DISTANCE);
    }
}
