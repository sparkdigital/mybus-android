package com.mybus.asynctask;

import android.os.AsyncTask;

import com.mybus.model.BusRoute;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.RoadResult;
import com.mybus.model.Road.RoadSearch;
import com.mybus.service.ServiceFacade;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * AsyncTask used to return a RoadResult either simple or combined
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class RoadSearchTask extends AsyncTask<Void, Integer, RoadResult> {
    private final int mType;
    private RoadSearchCallback roadSearchCallback;
    RoadSearch roadSearch;

    /**
     * Private Default Constructor
     *
     * @param type
     * @param roadSearch
     * @param callback
     */
    public RoadSearchTask(int type, RoadSearch roadSearch, RoadSearchCallback callback) {
        this.mType = type;
        this.roadSearchCallback = callback;
        this.roadSearch = roadSearch;
    }


    /**
     * Constructor for Search with a BusRouteResult object
     *
     * @param type
     * @param route
     * @param callback
     */
    public RoadSearchTask(int type, BusRouteResult route, RoadSearchCallback callback) {
        this.mType = type;
        this.roadSearchCallback = callback;
        roadSearch = new RoadSearch();
        if (route != null) {
            BusRoute busRoute = route.getBusRoutes().get(0);
            roadSearch.setmIdLine(String.valueOf(busRoute.getIdBusLine()));
            roadSearch.setmDirection(String.valueOf(busRoute.getBusLineDirection()));
            roadSearch.setmStop1(String.valueOf(busRoute.getStartBusStopNumber()));
            roadSearch.setmStop2(String.valueOf(busRoute.getDestinationBusStopNumber()));
            if (type == 1) {
                busRoute = route.getBusRoutes().get(1);
                roadSearch.setmIdLine2(String.valueOf(busRoute.getIdBusLine()));
                roadSearch.setmDirection2(String.valueOf(busRoute.getBusLineDirection()));
                roadSearch.setmStop1L2(String.valueOf(busRoute.getStartBusStopNumber()));
                roadSearch.setmStop2L2(String.valueOf(busRoute.getDestinationBusStopNumber()));
            }
        }
    }


    @Override
    protected RoadResult doInBackground(Void... params) {
        return ServiceFacade.getInstance().searchRoads(mType, roadSearch);
    }

    @Override
    public void onPostExecute(RoadResult result) {
        roadSearchCallback.onRoadFound(result);
    }
}
