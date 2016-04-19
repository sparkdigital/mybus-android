package com.mybus.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.BusRouteResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        OkHttpClient client = new OkHttpClient();
        //Create request to get a list of possible routes between two locations.
        //TODO: Remove token from url and use a system property
        String url = "http://www.mybus.com.ar/api/v1/NexusApi.php?lat0=" + latLngs[0].latitude + "&lng0=" + latLngs[0].longitude + "&lat1=" + latLngs[1].latitude + "&lng1=" + latLngs[1].longitude + "&tk=94a08da1fecbb6e8b46990538c7b50b2";
        Request request = new Request.Builder()
                .url(url)
                .build();
        JSONObject jsonObject;
        Call call = client.newCall(request);
        int type;
        JSONArray results;
        try {
            Response response = call.execute();
            String jsonData = response.body().string();
            jsonObject = new JSONObject(jsonData);
            type = jsonObject.getInt("Type"); //Gets type of results
            results = jsonObject.getJSONArray("Results"); //Gets results
        } catch (IOException | JSONException e) {
            //TODO
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
