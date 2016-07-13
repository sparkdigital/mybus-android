package com.mybus.model.road;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class Route {

    private static final String TAG = Route.class.getSimpleName();
    private List<RoutePoint> mPointList = new ArrayList<>();

    /**
     * @param jsonArray
     * @return
     */
    public static Route parse(JSONArray jsonArray) {
        Route route = new Route();
        List<RoutePoint> points = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                RoutePoint point = RoutePoint.parse(jsonobject);
                if (point != null) {
                    points.add(point);
                }
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }
        }
        route.setPointList(points);
        return route;
    }

    private void setPointList(List<RoutePoint> points) {
        this.mPointList = points;
    }

    public List<RoutePoint> getPointList() {
        return mPointList;
    }

    public LatLng getFirstLatLng() {
        return mPointList.get(0).getLatLng();
    }

    public LatLng getLastLatLng() {
        return mPointList.get(mPointList.size() - 1).getLatLng();
    }

    public String getFirstAddress() {
        return mPointList.get(0).getAddress();
    }

    public String getLastAddress() {
        return mPointList.get(mPointList.size() - 1).getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Route route = (Route) o;

        return mPointList != null ? mPointList.equals(route.mPointList) : route.mPointList == null;
    }

    @Override
    public int hashCode() {
        return mPointList != null ? mPointList.hashCode() : 0;
    }
}
