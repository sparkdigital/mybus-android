package com.mybus.model.road;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.model.DirectionsResult;
import com.mybus.MyBus;
import com.mybus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class RoadResult {

    private static final String TAG = RoadResult.class.getSimpleName();
    private static final int HASH_MULTIPLIER = 31;
    private int mType;
    private float mTotalDistance;
    private int mTravelTime;
    private int mArrivalTime;
    private List<Route> mRouteList = new ArrayList<>();
    private List<DirectionsResult> mWalkingList = new ArrayList<>();
    private String mIdBusLine1;
    private String mIdBusLine2;
    private String mBusLine1Color;
    private String mBusLine2Color;

    private static final float POLYLINE_WIDTH = 7F;

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
            Log.e(TAG, e.toString());
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
                    .title(MyBus.getContext().getString(R.string.bus_stop_origin, ""))
                    .snippet(mRouteList.get(0).getFirstAddress())
                    .position(mRouteList.get(0).getFirstLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_origen)));
            list.add(new MarkerOptions()
                    .title(MyBus.getContext().getString(R.string.bus_stop_destination, ""))
                    .snippet(mRouteList.get(0).getLastAddress())
                    .position(mRouteList.get(0).getLastLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_destino)));
        } else {
            list.add(new MarkerOptions()
                    .title(MyBus.getContext().getString(R.string.bus_stop_origin, "1"))
                    .snippet(mRouteList.get(0).getFirstAddress())
                    .position(mRouteList.get(0).getFirstLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_origen)));
            list.add(new MarkerOptions()
                    .title(MyBus.getContext().getString(R.string.bus_stop_destination, "1"))
                    .snippet(mRouteList.get(0).getLastAddress())
                    .position(mRouteList.get(0).getLastLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_origen)));

            list.add(new MarkerOptions()
                    .title(MyBus.getContext().getString(R.string.bus_stop_origin, "2"))
                    .snippet(mRouteList.get(1).getFirstAddress())
                    .position(mRouteList.get(1).getFirstLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parada_destino)));
            list.add(new MarkerOptions()
                    .title(MyBus.getContext().getString(R.string.bus_stop_destination, "2"))
                    .snippet(mRouteList.get(1).getLastAddress())
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
        rectOptions.color(Color.parseColor("#" + mBusLine1Color));
        rectOptions.width(POLYLINE_WIDTH);
        rectOptions.geodesic(true);
        Route route = mRouteList.get(0);
        for (RoutePoint point : route.getPointList()) {
            rectOptions.add(point.getLatLng());
        }
        list.add(rectOptions);

        if (mType == 1) {
            rectOptions = new PolylineOptions();
            rectOptions.color(Color.parseColor("#" + mBusLine2Color));
            rectOptions.width(POLYLINE_WIDTH);
            rectOptions.geodesic(true);
            route = mRouteList.get(1);
            for (RoutePoint point : route.getPointList()) {
                rectOptions.add(point.getLatLng());
            }
            list.add(rectOptions);
        }
        //Draws the walking direction lines
        for (DirectionsResult directionsResult : mWalkingList) {
            List<com.google.maps.model.LatLng> asd = directionsResult.routes[0].overviewPolyline.decodePath();
            rectOptions = new PolylineOptions();
            rectOptions.color(ContextCompat.getColor(MyBus.getContext(), R.color.walkingLine));
            rectOptions.width(POLYLINE_WIDTH);
            for (com.google.maps.model.LatLng latLng : asd) {
                rectOptions.add(new LatLng(latLng.lat, latLng.lng));
            }
            list.add(rectOptions);
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoadResult that = (RoadResult) o;

        return (mType == that.mType) && (Float.compare(that.mTotalDistance, mTotalDistance) == 0)
                && (mTravelTime == that.mTravelTime) && (mArrivalTime == that.mArrivalTime);

    }

    @Override
    public int hashCode() {
        int result = mType;
        result = HASH_MULTIPLIER * result + (mTotalDistance != +0.0f ? Float.floatToIntBits(mTotalDistance) : 0);
        result = HASH_MULTIPLIER * result + mTravelTime;
        result = HASH_MULTIPLIER * result + mArrivalTime;
        result = HASH_MULTIPLIER * result + mRouteList.hashCode();
        return result;
    }

    /**
     * @param walkingDirection
     */
    public void addWalkingDirection(DirectionsResult walkingDirection) {
        if (walkingDirection != null) {
            mWalkingList.add(walkingDirection);
        }
    }

    public void setBusLine1Color(String busLine1Color) {
        this.mBusLine1Color = busLine1Color;
    }

    public void setBusLine2Color(String busLine2Color) {
        this.mBusLine2Color = busLine2Color;
    }
}
