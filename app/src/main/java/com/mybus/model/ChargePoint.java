package com.mybus.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldimitroff on 05/07/16.
 */
public class ChargePoint {

    private static final String TAG = ChargePoint.class.getSimpleName();
    private static final int HASH_MULTIPLIER = 31;

    private String mID;
    private String mName;
    private String mAddress;
    private String mLatitude;
    private String mLongitude;
    private String mOpenTime;
    private String mDistance;

    /**
     * Parses a list of Charging Points
     *
     * @param results
     * @return
     */
    public static List<ChargePoint> parseResults(JSONArray results) {
        if (results == null) {
            return null;
        }
        List<ChargePoint> list = new ArrayList<>();
        JSONObject chargePoint = null;
        for (int i = 0; i < results.length(); i++) {
            try {
                chargePoint = results.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }
            ChargePoint point = parseRoutePoint(chargePoint);
            if (point != null) {
                list.add(point);
            }
        }
        return list;
    }

    /**
     * Parse a single Charge point given a JSONObject
     *
     * @param point
     * @return
     */
    public static ChargePoint parseRoutePoint(JSONObject point) {
        if (point == null) {
            return null;
        }
        ChargePoint result = new ChargePoint();
        try {
            result.setID(point.getString("Id"));
            result.setName(point.getString("Name"));
            result.setAddress(point.getString("Adress"));
            result.setLatitude(point.getString("Lat"));
            result.setLongitude(point.getString("Lng"));
            result.setOpenTime(point.getString("OpenTime"));
            result.setDistance(point.getString("Distance"));
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            result = null;
        }
        return result;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getOpenTime() {
        return mOpenTime;
    }

    public void setOpenTime(String mOpenTime) {
        this.mOpenTime = mOpenTime;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String mDistance) {
        this.mDistance = mDistance;
    }

    /**
     * @return
     */
    public LatLng getLatLng() {
        return new LatLng(Double.parseDouble(mLatitude), Double.parseDouble(mLongitude));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChargePoint that = (ChargePoint) o;

        if (!mID.equals(that.mID)) {
            return false;
        }
        if (!mName.equals(that.mName)) {
            return false;
        }
        if (!mAddress.equals(that.mAddress)) {
            return false;
        }
        if (!mLatitude.equals(that.mLatitude)) {
            return false;
        }
        if (!mLongitude.equals(that.mLongitude)) {
            return false;
        }
        if (!mOpenTime.equals(that.mOpenTime)) {
            return false;
        }
        return mDistance.equals(that.mDistance);

    }

    @Override
    public int hashCode() {
        int result = mID.hashCode();
        result = HASH_MULTIPLIER * result + mName.hashCode();
        result = HASH_MULTIPLIER * result + mAddress.hashCode();
        result = HASH_MULTIPLIER * result + mLatitude.hashCode();
        result = HASH_MULTIPLIER * result + mLongitude.hashCode();
        result = HASH_MULTIPLIER * result + mOpenTime.hashCode();
        result = HASH_MULTIPLIER * result + mDistance.hashCode();
        return result;
    }
}
