package com.mybus.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.BusRouteResult;
import com.mybus.service.MyBusServiceImpl;

import java.util.List;


/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>-
 * <p/>
 * AsyncTask used to return a list of possible routes between two locations.
 */
public class RouteSearchTask extends AsyncTask<Void, Integer, List<BusRouteResult>> {

    private final LatLng mOrigin, mDestiny;
    private RouteSearchCallback routeSearchCallback;

    public RouteSearchTask(LatLng origin, LatLng destiny, RouteSearchCallback rsCallback) {
        this.mOrigin = origin;
        this.mDestiny = destiny;
        routeSearchCallback = rsCallback;
    }

    @Override
    protected List<BusRouteResult> doInBackground(Void... params) {
        return new MyBusServiceImpl().searchRoutes(mOrigin, mDestiny);
    }

    @Override
    public void onPostExecute(List<BusRouteResult> results) {
        routeSearchCallback.onRouteFound(results);
    }
}
