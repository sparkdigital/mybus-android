package com.mybus.marker;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * Singleton used to save the start & end location MyBusMarker's
 */
public class MyBusMarkerStorage {
    private static MyBusMarkerStorage instance;
    private MyBusMarker mStartLocationMarker;
    private MyBusMarker mEndLocationMarker;

    public static MyBusMarkerStorage getInstance() {
        if (instance == null) {
            instance = new MyBusMarkerStorage();
        }
        return instance;
    }

    public void setStartLocationMyBusMarker(MyBusMarker marker) {
        this.mStartLocationMarker = marker;
    }

    public void setEndLocationMyBusMarker(MyBusMarker marker) {
        this.mEndLocationMarker = marker;
    }

    //Checks if the given marker is StartLocation, EndLocation or other.
    public MyBusMarker isMarkerPresent(Marker marker) {
        if (mStartLocationMarker != null && mStartLocationMarker.getMapMarker() != null && mStartLocationMarker.getMapMarker().getId().equals(marker.getId())) {
            return mStartLocationMarker;
        }
        if (mEndLocationMarker != null && mEndLocationMarker.getMapMarker() != null && mEndLocationMarker.getMapMarker().getId().equals(marker.getId())) {
            return mEndLocationMarker;
        }
        return null;
    }
}
