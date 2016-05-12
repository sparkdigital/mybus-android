package com.mybus.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ServiceFacade {

    private static ServiceFacade instance = null;
    private GisService gisService;
    private MyBusService myBusService;
    private GeocodingService geocodingService;

    private ServiceFacade() {
        gisService = new GisService();
        myBusService = new MyBusService();
        geocodingService = new GeocodingService();
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
    public JSONArray findStreets(String constraint) throws IOException, JSONException {
        return gisService.findStreets(constraint);
    }

    /**
     * @param origin
     * @param destiny
     * @return
     */
    public JSONObject searchRoutes(LatLng origin, LatLng destiny) throws IOException, JSONException {
        return myBusService.searchRoutes(origin, destiny);
    }

    /**
     * @param mType
     * @param mIdLine
     * @param mDirection
     * @param mStop1
     * @param mStop2
     * @param mIdLine2
     * @param mDirection2
     * @param mStop2L2
     * @param mStop1L2
     * @return
     */
    public JSONObject searchRoads(int mType, String mIdLine, String mDirection, String mStop1, String mStop2, String mIdLine2, String mDirection2, String mStop2L2, String mStop1L2) throws IOException, JSONException {
        return myBusService.searchRoads(mType, mIdLine, mDirection, mStop1, mStop2, mIdLine2, mDirection2, mStop2L2, mStop1L2);
    }

    /**
     *
     * @param location
     * @param callback
     */
    public void performGeocodeByLocation(LatLng location, OnLocationGeocodingCompleteCallback callback, Context context) {
        geocodingService.performGeocodeByLocation(location, callback, context);
    }

    /**
     *
     * @param address
     * @param callback
     */
    public void performGeocodeByAddress(String address, OnAddressGeocodingCompleteCallback callback, Context context) {
        geocodingService.performGeocodeByAddress(address, callback, context);
    }
}
