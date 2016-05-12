package com.mybus.service;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.builder.MyBusServiceUrlBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyBusService extends GenericService {

    /**
     * @param origin
     * @param destiny
     * @return
     */
    public JSONObject searchRoutes(LatLng origin, LatLng destiny) throws IOException, JSONException {
        //Create request to get a list of possible routes between two locations.
        String url = MyBusServiceUrlBuilder.buildNexusUrl(origin.latitude, origin.longitude, destiny.latitude, destiny.longitude);
        return new JSONObject(executeUrl(url));
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
        String url;
        if (mType == 0) {
            url = MyBusServiceUrlBuilder.buildSingleRoadUrl(mIdLine, mDirection, mStop1, mStop2);
        } else {
            url = MyBusServiceUrlBuilder.buildCombinedRoadUrl(mIdLine, mIdLine2, mDirection, mDirection2, mStop1, mStop2, mStop1L2, mStop2L2);
        }
        return new JSONObject(executeUrl(url));
    }
}
