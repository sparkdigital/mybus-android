package com.mybus.facade;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Facade {

    private static Facade instance = null;
    private OkHttpClient client;

    private Facade(){
        client = new OkHttpClient();
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /**
     * @param constraint
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public JSONArray findStreets(String constraint) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url("http://gis.mardelplata.gob.ar/opendata/ws.php?method=rest&endpoint=callejero_mgp&token=rwef3253465htrt546dcasadg4343&nombre_calle=" + constraint)
                .build();
        Call mLastCall = client.newCall(request);
        Response response = mLastCall.execute();

        String jsonData = response.body().string();
        return new JSONArray(jsonData);
    }

    /**
     * @param origin
     * @param destiny
     * @return
     */
    public JSONObject searchRoutes(LatLng origin, LatLng destiny) throws IOException, JSONException {
        //Create request to get a list of possible routes between two locations.
        //TODO: Remove token from url and use a system property
        String url = "http://www.mybus.com.ar/api/v1/NexusApi.php?lat0=" + origin.latitude + "&lng0=" + origin.longitude + "&lat1=" + destiny.latitude + "&lng1=" + destiny.longitude + "&tk=94a08da1fecbb6e8b46990538c7b50b2";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        int type;
        JSONArray results;
        Response response = call.execute();
        String jsonData = response.body().string();
        return new JSONObject(jsonData);
    }


    /**
     * @param mType
     * @param mIdLine
     * @param mDirection
     * @param mStop1
     * @param mStop2
     * @param mIdLine2
     * @param mDirection2
     * @param mStop2L2
     * @param mStop1L2
     * @return
     */
    public JSONObject searchRoads(int mType, String mIdLine, String mDirection, String mStop1, String mStop2, String mIdLine2, String mDirection2, String mStop2L2, String mStop1L2) throws IOException, JSONException {
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

        //Create request to get a list of possible routes between two locations.
        Request request = new Request.Builder()
                .url(url)
                .build();
        JSONObject jsonObject;
        Call call = client.newCall(request);
        Response response = call.execute();
        String jsonData = response.body().string();
        return new JSONObject(jsonData);
    }
}
