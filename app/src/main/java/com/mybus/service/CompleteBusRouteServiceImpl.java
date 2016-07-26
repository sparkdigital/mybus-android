package com.mybus.service;

import android.util.Log;

import com.mybus.builder.MyBusServiceUrlBuilder;
import com.mybus.model.CompleteBusRoute;
import com.mybus.model.road.RoutePoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class CompleteBusRouteServiceImpl extends GenericService implements CompleteBusRouteService {
    private static final String TAG = CompleteBusRouteService.class.getSimpleName();

    @Override
    public CompleteBusRoute getCompleteRoute(int busLineId) {
        try {
            //Create request to get the first direction for the complete route.
            String url = MyBusServiceUrlBuilder.buildCompleteBusRouteUrl(busLineId, 0);
            JSONObject jsonObject = new JSONObject(executeUrl(url)); //Gets the first direction
            CompleteBusRoute completeBusRoute = CompleteBusRoute.parseCompleteBusRoute(jsonObject); //Parse JSON for the first direction

            //Gets the first half of the complete route
            List<RoutePoint> initialPointList = completeBusRoute.getPointList();
            //Set middle stop bus
            completeBusRoute.setMidStopBus(initialPointList.get(initialPointList.size() - 1));

            url = MyBusServiceUrlBuilder.buildCompleteBusRouteUrl(busLineId, 1); // Url for the second direction
            jsonObject = new JSONObject(executeUrl(url)); // Gets the second half of the complete route

            //Extract the point list for the second half
            List<RoutePoint> sndPointList = CompleteBusRoute.parseCompleteBusRoute(jsonObject).getPointList();
            // Combine both lists
            initialPointList.addAll(sndPointList);
            // Sets the complete point list to the result object
            completeBusRoute.setPointList(initialPointList);
            return completeBusRoute;
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
