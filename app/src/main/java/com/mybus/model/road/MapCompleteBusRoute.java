package com.mybus.model.road;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mybus.model.CompleteBusRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas De Lio on 23-Nov-16.
 */

public class MapCompleteBusRoute {

    private List<Marker> mMarkerListGoing = new ArrayList<>();
    private List<Marker> mMarkerListReturn = new ArrayList<>();
    private List<Polyline> mPolylineListGoing = new ArrayList<>();
    private List<Polyline> mPolylineListReturn = new ArrayList<>();

    public MapCompleteBusRoute(GoogleMap map, CompleteBusRoute completeBusRoute) {
        for (MarkerOptions markerOptions : completeBusRoute.getMarkerOptionsGoing()) {
            mMarkerListGoing.add(map.addMarker(markerOptions));
        }
        for (MarkerOptions markerOptions : completeBusRoute.getMarkerOptionsReturn()) {
            mMarkerListReturn.add(map.addMarker(markerOptions));
        }
        for (PolylineOptions polylineOptions : completeBusRoute.getPolylineOptionsGoing()) {
            mPolylineListGoing.add(map.addPolyline(polylineOptions));
        }
        for (PolylineOptions polylineOptions : completeBusRoute.getPolylineOptionsReturn()) {
            mPolylineListReturn.add(map.addPolyline(polylineOptions));
        }
    }

    public List<Marker> getMarkerListGoing() {
        return mMarkerListGoing;
    }

    public void showBusRouteGoing() {
        for (Marker marker : mMarkerListGoing) {
            marker.setVisible(true);
        }
        for (Marker marker : mMarkerListReturn) {
            marker.setVisible(false);
        }
        for (Polyline polyline : mPolylineListGoing) {
            polyline.setVisible(true);
        }
        for (Polyline polyline : mPolylineListReturn) {
            polyline.setVisible(false);
        }
    }

    public void showBusRouteReturn() {
        for (Marker marker : mMarkerListGoing) {
            marker.setVisible(false);
        }
        for (Marker marker : mMarkerListReturn) {
            marker.setVisible(true);
        }
        for (Polyline polyline : mPolylineListGoing) {
            polyline.setVisible(false);
        }
        for (Polyline polyline : mPolylineListReturn) {
            polyline.setVisible(true);
        }
    }

    public void hideBusRoutes() {
        for (Marker marker : mMarkerListGoing) {
            marker.setVisible(false);
        }
        for (Marker marker : mMarkerListReturn) {
            marker.setVisible(false);
        }
        for (Polyline polyline : mPolylineListGoing) {
            polyline.setVisible(false);
        }
        for (Polyline polyline : mPolylineListReturn) {
            polyline.setVisible(false);
        }
    }

}
