package com.mybus.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.BusRoute;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.RoadResult;
import com.mybus.model.Road.RoadSearch;
import com.mybus.service.ServiceFacade;


/**
 * AsyncTask used to return a RoadResult either simple or combined
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class RoadSearchTask extends AsyncTask<Void, Integer, RoadResult> {
    private final int mType;
    private LatLng midEndStop;
    private LatLng midStartStop;
    private LatLng endBusStop;
    private LatLng startLocation;
    private LatLng endLocation;
    private LatLng firstBusStop;
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
    public RoadSearchTask(int type, BusRouteResult route, LatLng startLocation, LatLng endLocation, RoadSearchCallback callback) {
        this.mType = type;
        this.roadSearchCallback = callback;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        roadSearch = new RoadSearch();
        if (route != null) {
            BusRoute busRoute = route.getBusRoutes().get(0);
            roadSearch.setmIdLine(String.valueOf(busRoute.getIdBusLine()));
            roadSearch.setmDirection(String.valueOf(busRoute.getBusLineDirection()));
            roadSearch.setmStop1(String.valueOf(busRoute.getStartBusStopNumber()));
            roadSearch.setmStop2(String.valueOf(busRoute.getDestinationBusStopNumber()));
            firstBusStop = busRoute.getStartBusStopLatLng();
            endBusStop = busRoute.getEndBusStopLatLng();
            if (type == 1) {
                midStartStop = busRoute.getEndBusStopLatLng();
                busRoute = route.getBusRoutes().get(1);
                midEndStop = busRoute.getStartBusStopLatLng();
                endBusStop = busRoute.getEndBusStopLatLng();
                roadSearch.setmIdLine2(String.valueOf(busRoute.getIdBusLine()));
                roadSearch.setmDirection2(String.valueOf(busRoute.getBusLineDirection()));
                roadSearch.setmStop1L2(String.valueOf(busRoute.getStartBusStopNumber()));
                roadSearch.setmStop2L2(String.valueOf(busRoute.getDestinationBusStopNumber()));
            }
        }
    }


    @Override
    protected RoadResult doInBackground(Void... params) {
        RoadResult result = ServiceFacade.getInstance().searchRoads(mType, roadSearch);
        result.addWalkingDirection(ServiceFacade.getInstance().getDirection(startLocation, firstBusStop));
        if (midStartStop != null && midEndStop != null) {
            result.addWalkingDirection(ServiceFacade.getInstance().getDirection(midStartStop, midEndStop));
        }
        result.addWalkingDirection(ServiceFacade.getInstance().getDirection(endLocation, endBusStop));
        return result;
    }

    @Override
    public void onPostExecute(RoadResult result) {
        roadSearchCallback.onRoadFound(result);
    }
}
