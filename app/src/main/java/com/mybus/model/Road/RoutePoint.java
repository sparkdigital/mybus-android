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

    public RoutePoint() {}

    public RoutePoint(String mStopId, String mLat, String mLng, String mAddress, boolean isWaypoint) {
        this.mStopId = mStopId;
        this.mLat = mLat;
        this.mLng = mLng;
        this.mAddress = mAddress;
        this.isWaypoint = isWaypoint;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoutePoint that = (RoutePoint) o;

        if (isWaypoint != that.isWaypoint) return false;
        if (!mStopId.equals(that.mStopId)) return false;
        if (!mLat.equals(that.mLat)) return false;
        if (!mLng.equals(that.mLng)) return false;
        return mAddress.equals(that.mAddress);

    }

    @Override
    public int hashCode() {
        int result = mStopId.hashCode();
        result = 31 * result + mLat.hashCode();
        result = 31 * result + mLng.hashCode();
        result = 31 * result + mAddress.hashCode();
        result = 31 * result + (isWaypoint ? 1 : 0);
        return result;
    }
}
