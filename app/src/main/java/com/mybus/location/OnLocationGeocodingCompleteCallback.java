package com.mybus.location;

import com.google.android.gms.maps.model.LatLng;

public interface OnLocationGeocodingCompleteCallback {
    void onLocationGeocodingComplete(String address);
}
