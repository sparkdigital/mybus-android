package com.mybus.model;

import android.util.Log;

import com.mybus.model.road.RoutePoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class CompleteBusRoute {

    private static final String TAG = CompleteBusRoute.class.getSimpleName();
    private int mType;
    private String mColor;
    private List<RoutePoint> mPointList;

    public static CompleteBusRoute parseCompleteBusRoute(JSONObject jsonObject) {
        CompleteBusRoute completeBusRoute = new CompleteBusRoute();
        try {
            completeBusRoute.setType(jsonObject.getInt("Type"));
            completeBusRoute.setColor("#" + jsonObject.getString("Color"));
            List<RoutePoint> points = new ArrayList<>();
            JSONArray results = jsonObject.getJSONArray("Results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonobject = results.getJSONObject(i);
                RoutePoint point = RoutePoint.parse(jsonobject);
                if (point != null) {
                    points.add(point);
                }
            }
            completeBusRoute.setPointList(points);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            completeBusRoute = null;
        }
        return completeBusRoute;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public void setPointList(List<RoutePoint> pointList) {
        this.mPointList = pointList;
    }

    public List<RoutePoint> getPointList(){
        return this.mPointList;
    }
}
