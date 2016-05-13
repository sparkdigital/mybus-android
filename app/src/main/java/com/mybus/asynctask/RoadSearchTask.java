package com.mybus.asynctask;

import android.net.Uri;
import android.os.AsyncTask;

import com.mybus.model.BusRoute;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.RoadResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        OkHttpClient client = new OkHttpClient();
        //Create request to get a list of possible routes between two locations.
        String url = getUrl();
        Request request = new Request.Builder()
                .url(url)
                .build();
        JSONObject jsonObject;
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String jsonData = response.body().string();
            jsonObject = new JSONObject(jsonData);
        } catch (IOException | JSONException e) {
            //TODO
            e.printStackTrace();
            return null;
        }

        return RoadResult.parse(jsonObject);
    }

    private String getUrl() {
        String url;
        if (mType == 0) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("www.mybus.com.ar")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("SingleRoadApi.php")
                    .appendQueryParameter("idline", mIdLine)
                    .appendQueryParameter("direction", mDirection)
                    .appendQueryParameter("stop1", mStop1)
                    .appendQueryParameter("stop2", mStop2)
                    .appendQueryParameter("tk", "94a08da1fecbb6e8b46990538c7b50b2");
            url = builder.build().toString();
        } else {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("www.mybus.com.ar")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("CombinedRoadApi.php")
                    .appendQueryParameter("idline1", mIdLine)
                    .appendQueryParameter("idline2", mIdLine2)
                    .appendQueryParameter("direction1", mDirection)
                    .appendQueryParameter("direction2", mDirection2)
                    .appendQueryParameter("L1stop1", mStop1)
                    .appendQueryParameter("L1stop2", mStop2)
                    .appendQueryParameter("L2stop1", mStop1L2)
                    .appendQueryParameter("L2stop2", mStop2L2)
                    .appendQueryParameter("tk", "94a08da1fecbb6e8b46990538c7b50b2");
            url = builder.build().toString();
        }
        return url;
    }

    @Override
    public void onPostExecute(RoadResult result) {
        roadSearchCallback.onRoadFound(result);
    }
}
