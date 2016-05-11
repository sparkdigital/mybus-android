package com.mybus.asynctask;

import android.os.AsyncTask;

import com.mybus.facade.Facade;
import com.mybus.model.BusRoute;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.RoadResult;

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
    private String mIdLine;
    private String mDirection;
    private String mStop1;
    private String mStop2;
    private String mIdLine2;
    private String mDirection2;
    private String mStop2L2;
    private String mStop1L2;

    /**
     * Private Default Constructor
     *
     * @param type
     * @param callback
     */
    private RoadSearchTask(int type, RoadSearchCallback callback) {
        this.mType = type;
        this.roadSearchCallback = callback;
    }

    /**
     * Constructor for SingleRoad Search
     *
     * @param type
     * @param idLine
     * @param direction
     * @param stop1
     * @param stop2
     * @param callback
     */
    public RoadSearchTask(int type, String idLine, String direction, String stop1, String stop2, RoadSearchCallback callback) {
        this(type, callback);
        this.mIdLine = idLine;
        this.mDirection = direction;
        this.mStop1 = stop1;
        this.mStop2 = stop2;
    }

    /**
     * Constructor for CombinedRoad Search
     *
     * @param type
     * @param idLine
     * @param idLine2
     * @param direction
     * @param direction2
     * @param stop1
     * @param stop2
     * @param stop1Line2
     * @param stop2Line2
     * @param callback
     */
    public RoadSearchTask(int type, String idLine, String idLine2, String direction, String direction2, String stop1, String stop2, String stop1Line2, String stop2Line2, RoadSearchCallback callback) {
        this(type, idLine, direction, stop1, stop2, callback);
        this.mIdLine2 = idLine2;
        this.mDirection2 = direction2;
        this.mStop1L2 = stop1Line2;
        this.mStop2L2 = stop2Line2;
    }

    /**
     * Constructor for Search with a BusRouteResult object
     *
     * @param type
     * @param route
     * @param callback
     */
    public RoadSearchTask(int type, BusRouteResult route, RoadSearchCallback callback) {
        this(type, callback);
        if (route != null) {
            BusRoute busRoute = route.getBusRoutes().get(0);
            this.mIdLine = String.valueOf(busRoute.getIdBusLine());
            this.mDirection = String.valueOf(busRoute.getBusLineDirection());
            this.mStop1 = String.valueOf(busRoute.getStartBusStopNumber());
            this.mStop2 = String.valueOf(busRoute.getDestinationBusStopNumber());
            if (type == 1) {
                busRoute = route.getBusRoutes().get(1);
                this.mIdLine2 = String.valueOf(busRoute.getIdBusLine());
                this.mDirection2 = String.valueOf(busRoute.getBusLineDirection());
                this.mStop1L2 = String.valueOf(busRoute.getStartBusStopNumber());
                this.mStop2L2 = String.valueOf(busRoute.getDestinationBusStopNumber());
            }
        }
    }


    @Override
    protected RoadResult doInBackground(Void... params) {
        JSONObject jsonObject;
        try {
            jsonObject = Facade.getInstance().searchRoads(mType, mIdLine, mDirection, mStop1, mStop2, mIdLine2, mDirection2, mStop2L2, mStop1L2);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return RoadResult.parse(jsonObject);
    }

    @Override
    public void onPostExecute(RoadResult result) {
        roadSearchCallback.onRoadFound(result);
    }
}
