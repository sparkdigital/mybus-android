package com.mybus.model.Road;

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

    private List<RoutePoint> mPointList;

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
                e.printStackTrace();
                route = null;
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
}
