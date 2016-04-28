package com.mybus.model.Road;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mybus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class RoadResult {

    private int mType;
    private float mTotalDistance;
    private int mTravelTime;
    private int mArrivalTime;
    private List<Route> mRouteList = new ArrayList<>();
    private String mIdBusLine1;
    private String mIdBusLine2;

    /**
     * @param jsonObject
     * @return
     */
    public static RoadResult parse(JSONObject jsonObject) {
        RoadResult singleRoad = new RoadResult();
        try {
            singleRoad.setType(jsonObject.getInt("Type"));
            singleRoad.setTotalDistance(jsonObject.getInt("TotalDistance"));
            singleRoad.setTravelTime(jsonObject.getInt("TravelTime"));
            singleRoad.setArrivalTime(jsonObject.optInt("ArrivalTime"));
            singleRoad.getRoutes().add(Route.parse(jsonObject.getJSONArray("Route1")));
            if (jsonObject.has("Route2")) {
                singleRoad.getRoutes().add(Route.parse(jsonObject.getJSONArray("Route2")));
                singleRoad.setIdBusLine1(jsonObject.getString("IdBusLine1"));
                singleRoad.setIdBusLine2(jsonObject.getString("IdBusLine2"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            singleRoad = null;
        }
        return singleRoad;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public float getTotalDistance() {
        return mTotalDistance;
    }

    public void setTotalDistance(float mDistance) {
        this.mTotalDistance = mDistance;
    }

    public int getTravelTime() {
        return mTravelTime;
    }

    public void setTravelTime(int mTravelTime) {
        this.mTravelTime = mTravelTime;
    }

    public int getArrivalTime() {
        return mArrivalTime;
    }

    public void setArrivalTime(int mArrivalTime) {
        this.mArrivalTime = mArrivalTime;
    }

    public String getIdBusLine2() {
        return mIdBusLine2;
    }

    public void setIdBusLine2(String mIdBusLine2) {
        this.mIdBusLine2 = mIdBusLine2;
    }

    public String getIdBusLine1() {
        return mIdBusLine1;
    }

    public void setIdBusLine1(String mIdBusLine1) {
        this.mIdBusLine1 = mIdBusLine1;
    }

    public List<Route> getRoutes() {
        return mRouteList;
    }

    /**
     * @return a set of RoutePoint for all the Routes on the RoadResult
     */
    public List<RoutePoint> getPointList() {
        if (mRouteList == null || mRouteList.isEmpty()) {
            return null;
        }
        ArrayList<RoutePoint> points = new ArrayList<>();
        for (Route route : mRouteList) {
            points.addAll(route.getPointList());
        }
        return points;
    }

    /**
     * @return a list of Marker options to be drawed on the map
     */
    public List<MarkerOptions> getMarkerOptions() {
        List<MarkerOptions> list = new ArrayList<>();
        if (mType == 0) {
            list.add(new MarkerOptions()
                    .title("Parada Origen")
                    .position(mRouteList.get(0).getFirstLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_origen)));
            list.add(new MarkerOptions()
                    .title("Parada Destino")
                    .position(mRouteList.get(0).getLastLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_destino)));
        } else {
            list.add(new MarkerOptions()
                    .title("Parada Origen 1")
                    .position(mRouteList.get(0).getFirstLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_origen)));
            list.add(new MarkerOptions()
                    .title("Parada Destino 1")
                    .position(mRouteList.get(0).getLastLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_origen)));

            list.add(new MarkerOptions()
                    .title("Parada Origen 2")
                    .position(mRouteList.get(1).getFirstLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_destino)));
            list.add(new MarkerOptions()
                    .title("Parada Destino 2")
                    .position(mRouteList.get(1).getLastLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_destino)));
        }
        return list;
    }

    /**
     * @return a list of polyline options to be drawed on the map
     */
    public List<PolylineOptions> getPolylineOptions() {
        List<PolylineOptions> list = new ArrayList<>();

        PolylineOptions rectOptions = new PolylineOptions();
        Route route = mRouteList.get(0);
        for (RoutePoint point : route.getPointList()) {
            rectOptions.add(point.getLatLng());
        }
        list.add(rectOptions);

        if (mType == 1) {
            rectOptions = new PolylineOptions();
            route = mRouteList.get(1);
            for (RoutePoint point : route.getPointList()) {
                rectOptions.add(point.getLatLng());
            }
            list.add(rectOptions);
        }
        return list;
    }

}
