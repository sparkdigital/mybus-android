package com.mybus.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.RoadResult;
import com.mybus.model.Road.RoadSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ServiceFacade {

    private static ServiceFacade instance = null;
    private GisService gisService;
    private MyBusService myBusService;
    private GeocodingService geocodingService;

    private ServiceFacade() {
        gisService = new GisServiceImp();
        myBusService = new MyBusServiceImp();
        geocodingService = new GeocodingServiceImp();
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
     * @throws IOException
     * @throws JSONException
     */
    public List<String> findStreets(String constraint){
        return gisService.findStreets(constraint);
    }

    /**
     * @param origin
     * @param destiny
     * @return
     */
    public List<BusRouteResult> searchRoutes(LatLng origin, LatLng destiny) {
        return myBusService.searchRoutes(origin, destiny);
    }

    /**
     * @param mType
     * @param roadSearch
     * @return
     */
    public RoadResult searchRoads(int mType, RoadSearch roadSearch){
        return myBusService.searchRoads(mType, roadSearch);
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
}
