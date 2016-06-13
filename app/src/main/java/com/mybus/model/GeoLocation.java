package com.mybus.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ldimitroff on 13/06/16.
 */
public class GeoLocation {

    private LatLng mLatLng;
    private String mAddress;

    public GeoLocation(String address, LatLng latLng) {
        this.mLatLng = latLng;
        this.mAddress = address;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        this.mLatLng = latLng;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }
}
