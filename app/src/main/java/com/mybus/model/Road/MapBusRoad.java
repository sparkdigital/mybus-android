package com.mybus.model.Road;

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

    /**
     * Adds the Specific road result into the map
     *
     * @param map
     * @param roadResult
     */
    public void addBusRoadOnMap(GoogleMap map, RoadResult roadResult) {
        for (MarkerOptions markerOptions : roadResult.getMarkerOptions()) {
            mMarkerList.add(map.addMarker(markerOptions));
        }
        for (PolylineOptions polylineOptions : roadResult.getPolylineOptions()) {
            mPolylineList.add(map.addPolyline(polylineOptions));
        }
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

}
