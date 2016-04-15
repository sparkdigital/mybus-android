package com.mybus.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>-
 * <p/>
 * AsyncTask used to return the route from two locations
 */
public class RouteSearchTask extends AsyncTask<LatLng, Integer, JSONArray> {
    private RouteSearchCallback routeSearchCallback;

    public RouteSearchTask(RouteSearchCallback rsCallback) {
        routeSearchCallback = rsCallback;
    }

    @Override
    protected JSONArray doInBackground(LatLng... latLngs) {
        OkHttpClient client = new OkHttpClient();
        // Create request to get the route between two locations.
        //TODO: Remove token from url and use a system property
        Request request = new Request.Builder()
                .url("http://www.mybus.com.ar/api/v1/NexusApi.php?lat0=" + latLngs[0].latitude + "&lng0=" + latLngs[0].longitude + "&lat1=" + latLngs[1].latitude + "&lng1=" + latLngs[1].longitude + "&tk=94a08da1fecbb6e8b46990538c7b50b2")
                .build();
        JSONArray jsonArray = null;
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String jsonData = response.body().string();
            jsonArray = new JSONArray(jsonData);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        } catch (JSONException e) {
            //TODO
            e.printStackTrace();
        }

        return jsonArray;
    }

    @Override
    public void onPostExecute(JSONArray results) {
        routeSearchCallback.onRouteFound(results);
    }
}
