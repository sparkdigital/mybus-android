package com.mybus.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.facade.Facade;
import com.mybus.model.BusRouteResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>-
 *
 * AsyncTask used to return a list of possible routes between two locations.
 */
public class RouteSearchTask extends AsyncTask<LatLng, Integer, List<BusRouteResult>> {
    private RouteSearchCallback routeSearchCallback;

    public RouteSearchTask(RouteSearchCallback rsCallback) {
        routeSearchCallback = rsCallback;
    }

    @Override
    protected List<BusRouteResult> doInBackground(LatLng... latLngs) {
        JSONObject jsonObject;
        int type;
        JSONArray results;
        try {
            jsonObject = Facade.getInstance().searchRoutes(latLngs[0], latLngs[1]);
            type = jsonObject.getInt("Type"); //Gets type of results
            results = jsonObject.getJSONArray("Results"); //Gets results
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return BusRouteResult.parseResults(results, type); //Parse results from JSONArray to model's objects
    }

    @Override
    public void onPostExecute(List<BusRouteResult> results) {
        routeSearchCallback.onRouteFound(results);
    }
}
