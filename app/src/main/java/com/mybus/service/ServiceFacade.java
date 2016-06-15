package com.mybus.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.DirectionsResult;
import com.mybus.asynctask.RoadSearchCallback;
import com.mybus.asynctask.RoadSearchTask;
import com.mybus.asynctask.RouteSearchCallback;
import com.mybus.asynctask.RouteSearchTask;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.model.BusRouteResult;

import java.util.List;

public final class ServiceFacade {

    private static ServiceFacade instance = null;
    private DirectionsServiceImpl directionsService;
    private GisService gisService;
    private GeocodingService geocodingService;

    private ServiceFacade() {
        gisService = new GisServiceImpl();
        geocodingService = new GeocodingServiceImpl();
        directionsService = new DirectionsServiceImpl();
    }

    public static ServiceFacade getInstance() {
        if (instance == null) {
            instance = new ServiceFacade();
        }
        return instance;
    }

    /**
     * @param constraint
     * @return
     */
    public List<String> findStreets(String constraint) {
        return gisService.findStreets(constraint);
    }

    /**
     * @param origin
     * @param destiny
     * @return
     */
    public void searchRoutes(LatLng origin, LatLng destiny, RouteSearchCallback rsCallback) {
        RouteSearchTask routeSearchTask = new RouteSearchTask(origin, destiny, rsCallback);
        routeSearchTask.execute();
    }

    /**
     * @param type
     * @param route
     * @param startLocation
     * @param endLocation
     * @param callback
     */
    public void searchRoads(int type, BusRouteResult route, LatLng startLocation, LatLng endLocation, RoadSearchCallback callback) {
        RoadSearchTask routeSearchTask = new RoadSearchTask(type, route, startLocation, endLocation, callback);
        routeSearchTask.execute();
    }

    /**
     * @param location
     * @param callback
     */
    public void performGeocodeByLocation(LatLng location, OnLocationGeocodingCompleteCallback callback, Context context) {
        geocodingService.performGeocodeByLocation(location, callback, context);
    }

    /**
     * @param address
     * @param callback
     */
    public void performGeocodeByAddress(String address, OnAddressGeocodingCompleteCallback callback, Context context) {
        geocodingService.performGeocodeByAddress(address, callback, context);
    }

    /**
     * @param origin
     * @param destination
     */
    public DirectionsResult getDirection(LatLng origin, LatLng destination) {
        return directionsService.getDirections(origin, destination);
    }
}
