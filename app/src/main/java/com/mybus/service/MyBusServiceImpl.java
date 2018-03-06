package com.mybus.service;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.builder.MyBusServiceUrlBuilder;
import com.mybus.model.BusRouteResult;
import com.mybus.model.ChargePoint;
import com.mybus.model.road.RoadResult;
import com.mybus.model.road.RoadSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;

public class MyBusServiceImpl extends GenericService implements MyBusService {

    private static final String TAG = MyBusService.class.getSimpleName();

    /**
     * @param origin
     * @param destiny
     * @return
     */
    @Override
    public List<BusRouteResult> searchRoutes(LatLng origin, LatLng destiny) {
        try {
            //Create request to get a list of possible routes between two locations.
            String url = MyBusServiceUrlBuilder.buildNexusUrl(origin.latitude, origin.longitude, destiny.latitude, destiny.longitude);
            JSONObject jsonObject = new JSONObject(executeUrl(url));
            int type = jsonObject.getInt("Type"); //Gets type of results
            JSONArray results = jsonObject.getJSONArray("Results"); //Gets result
            return BusRouteResult.parseResults(results, type); //Parse results from JSONA
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * @param mType
     * @param roadSearch
     * @return
     */
    @Override
    public RoadResult searchRoads(int mType, RoadSearch roadSearch) {
        JSONObject jsonObject;
        try {
            String url;
            if (mType == 0) {
                url = MyBusServiceUrlBuilder.buildSingleRoadUrl(roadSearch);
            } else {
                url = MyBusServiceUrlBuilder.buildCombinedRoadUrl(roadSearch);
            }
            jsonObject = new JSONObject(executeUrl(url));
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }
        return RoadResult.parse(jsonObject);
    }

    @Override
    public List<ChargePoint> getNearChargePoints(LatLng location) {
        try {
            String url = MyBusServiceUrlBuilder.buildRechargeCardUrl();
            RequestBody requestBody = MyBusServiceUrlBuilder.buildRechargeCarForm(location.latitude, location.longitude);
            JSONObject jsonObject = new JSONObject(executePOST(url, requestBody));
            JSONArray jsonArray = jsonObject.getJSONArray("Results");
            return ChargePoint.parseResults(jsonArray);
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public String getBusFares() {
        try {
            return executePOST(MyBusServiceUrlBuilder.FARES_URL, null);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
