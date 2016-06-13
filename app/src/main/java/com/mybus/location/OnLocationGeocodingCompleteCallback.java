package com.mybus.location;

import com.mybus.model.GeoLocation;

public interface OnLocationGeocodingCompleteCallback {
    void onLocationGeocodingComplete(GeoLocation geoLocation);
}
