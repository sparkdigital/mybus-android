package com.mybus.marker;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class MarkerStorage {
    private static MarkerStorage instance;
    private MyBusMarker mStartLocationMarker;
    private MyBusMarker mEndLocationMarker;

    public static MarkerStorage getInstance() {
        if (instance == null) {
            instance = new MarkerStorage();
            return instance;
        } else {
            return instance;
        }
    }

    public void setStartLocationMarker(MyBusMarker marker) {
        this.mStartLocationMarker = marker;
    }

    public void setEndLocationMarker(MyBusMarker marker) {
        this.mEndLocationMarker = marker;
    }

    public MyBusMarker getStartLocationMarker() {
        return this.mStartLocationMarker;
    }

    public MyBusMarker getEndLocationMarker() {
        return this.mEndLocationMarker;
    }

    public MyBusMarker isMarkerPresent(Marker marker) {
        if (mStartLocationMarker != null && mStartLocationMarker.getMarker() != null && mStartLocationMarker.getMarker().getId().equals(marker.getId())) {
            return mStartLocationMarker;
        }
        if (mEndLocationMarker != null && mEndLocationMarker.getMarker() != null && mEndLocationMarker.getMarker().getId().equals(marker.getId())) {
            return mEndLocationMarker;
        }
        return null;
    }
}
