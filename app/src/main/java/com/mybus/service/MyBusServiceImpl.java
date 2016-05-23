package com.mybus.service;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.builder.MyBusServiceUrlBuilder;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.RoadResult;
import com.mybus.model.Road.RoadSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MyBusServiceImpl extends GenericService implements MyBusService {

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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
        return RoadResult.parse(jsonObject);
    }
}
