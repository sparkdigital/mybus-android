package com.mybus.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.BusRouteResult;
import com.mybus.service.ServiceFacade;

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
        return ServiceFacade.getInstance().searchRoutes(latLngs[0], latLngs[1]);
    }

    @Override
    public void onPostExecute(List<BusRouteResult> results) {
        routeSearchCallback.onRouteFound(results);
    }
}
