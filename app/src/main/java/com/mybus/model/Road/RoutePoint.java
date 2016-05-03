package com.mybus.model.Road;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class RoutePoint {

    private String mStopId, mLat, mLng, mAddress;
    private boolean isWaypoint;

    /**
     * @param jsonObject
     * @return
     */
    public static RoutePoint parse(JSONObject jsonObject) {
        RoutePoint point = new RoutePoint();
        try {
            point.setStopId(jsonObject.getString("StopId"));
            point.setLat(jsonObject.getString("Lat"));
            point.setLng(jsonObject.getString("Lng"));
            point.setAddress(jsonObject.getString("Address"));
            point.setWaypoint(jsonObject.getBoolean("isWaypoint"));
        } catch (JSONException e) {
            e.printStackTrace();
            point = null;
        }

        return point;
    }

    public String getStopId() {
        return mStopId;
    }

    public void setStopId(String StopId) {
        this.mStopId = StopId;
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String Lat) {
        this.mLat = Lat;
    }

    public String getLng() {
        return mLng;
    }

    public void setLng(String Lng) {
        this.mLng = Lng;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String Address) {
        this.mAddress = Address;
    }

    public boolean isWaypoint() {
        return isWaypoint;
    }

    public void setWaypoint(boolean waypoint) {
        isWaypoint = waypoint;
    }

    public LatLng getLatLng() {
        return new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLng));
    }
}
