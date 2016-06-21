package com.mybus.model.road;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class MapBusRoad {

    private List<Marker> mMarkerList = new ArrayList<>();
    private List<Polyline> mPolylineList = new ArrayList<>();

    public List<Marker> getMarkerList() {
        return mMarkerList;
    }

    /**
     * Adds the Specific road result into the map
     *  @param map
     * @param roadResult
     */
    public MapBusRoad addBusRoadOnMap(GoogleMap map, RoadResult roadResult) {
        for (MarkerOptions markerOptions : roadResult.getMarkerOptions()) {
            mMarkerList.add(map.addMarker(markerOptions));
        }
        for (PolylineOptions polylineOptions : roadResult.getPolylineOptions()) {
            mPolylineList.add(map.addPolyline(polylineOptions));
        }
        return this;
    }

    /**
     * Removes the road result from the map
     */
    public void clearBusRoadFromMap() {
        for (Marker marker : mMarkerList) {
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
        for (Polyline polyline : mPolylineList) {
            polyline.setVisible(show);
        }
    }

}
