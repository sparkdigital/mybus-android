package com.mybus.model;

import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mybus.MyBus;
import com.mybus.R;
import com.mybus.model.road.RoutePoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Model object to parse the complete route, allows save the complete route in both directions.
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class CompleteBusRoute {

    private static final String TAG = CompleteBusRoute.class.getSimpleName();
    private static final float POLYLINE_WIDTH = 7F;
    private String mBusLineName;
    private List<RoutePoint> mGoingPointList;
    private List<RoutePoint> mReturnPointList;

    public static CompleteBusRoute parseOneWayBusRoute(JSONObject jsonObject, String busLineName) {
        CompleteBusRoute completeBusRoute = new CompleteBusRoute();
        completeBusRoute.setBusLineName(busLineName);
        try {
            List<RoutePoint> points = new ArrayList<>();
            JSONArray results = jsonObject.getJSONArray("Results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonobject = results.getJSONObject(i);
                RoutePoint point = RoutePoint.parse(jsonobject);
                if (point != null) {
                    points.add(point);
                }
            }
            completeBusRoute.setGoingPointList(points);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            completeBusRoute = null;
        }
        return completeBusRoute;
    }

    public String getBusLineName() {
        return mBusLineName;
    }

    public void setBusLineName(String busLineName) {
        this.mBusLineName = busLineName;
    }

    public void setGoingPointList(List<RoutePoint> pointList) {
        this.mGoingPointList = pointList;
    }

    public List<RoutePoint> getGoingPointList() {
        return this.mGoingPointList;
    }

    public List<RoutePoint> getReturnPointList() {
        return mReturnPointList;
    }

    public void setReturnPointList(List<RoutePoint> returnPointList) {
        this.mReturnPointList = returnPointList;
    }

    public List<MarkerOptions> getMarkerOptionsGoing() {
        List<MarkerOptions> list = new ArrayList<>();
        list.add(new MarkerOptions()
                .title(MyBus.getContext().getString(R.string.start_complete_route, mBusLineName))
                .snippet(mGoingPointList.get(0).getAddress())
                .position(mGoingPointList.get(0).getLatLng())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.from_route)));
        list.add(new MarkerOptions()
                .title(MyBus.getContext().getString(R.string.mid_complete_route, mBusLineName))
                .snippet(mGoingPointList.get(mGoingPointList.size() - 1).getAddress())
                .position(mGoingPointList.get(mGoingPointList.size() - 1).getLatLng())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.to_route)));
        return list;
    }

    /**
     * @return a list of Marker options to be drawed on the map
     */
    public List<MarkerOptions> getMarkerOptionsReturn() {
        List<MarkerOptions> list = new ArrayList<>();
        list.add(new MarkerOptions()
                .title(MyBus.getContext().getString(R.string.end_complete_route, mBusLineName))
                .snippet(mReturnPointList.get(mReturnPointList.size() - 1).getAddress())
                .position(mReturnPointList.get(mReturnPointList.size() - 1).getLatLng())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.to_route)));
        list.add(new MarkerOptions()
                .title(MyBus.getContext().getString(R.string.mid_complete_route, mBusLineName))
                .snippet(mGoingPointList.get(mGoingPointList.size() - 1).getAddress())
                .position(mGoingPointList.get(mGoingPointList.size() - 1).getLatLng())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.from_route)));
        return list;
    }

    public List<PolylineOptions> getPolylineOptionsReturn() {
        List<PolylineOptions> list = new ArrayList<>();
        //Return route:
        PolylineOptions rectOptions = new PolylineOptions();
        rectOptions.color(ContextCompat.getColor(MyBus.getContext(), R.color.colorSecondary));
        rectOptions.width(POLYLINE_WIDTH);
        rectOptions.geodesic(true);
        for (RoutePoint point : mReturnPointList) {
            rectOptions.add(point.getLatLng());
        }
        list.add(rectOptions);
        return list;
    }

    public List<PolylineOptions> getPolylineOptionsGoing() {
        List<PolylineOptions> list = new ArrayList<>();
        PolylineOptions rectOptions = new PolylineOptions();
        //Going route:
        rectOptions.color(ContextCompat.getColor(MyBus.getContext(), R.color.goingRouteColor));
        rectOptions.width(POLYLINE_WIDTH);
        rectOptions.geodesic(true);
        for (RoutePoint point : mGoingPointList) {
            rectOptions.add(point.getLatLng());
        }
        list.add(rectOptions);
        return list;
    }
}
