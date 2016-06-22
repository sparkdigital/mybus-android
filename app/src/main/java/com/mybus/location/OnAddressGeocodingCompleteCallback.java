package com.mybus.location;

import com.mybus.model.GeoLocation;

public interface OnAddressGeocodingCompleteCallback {
    void onAddressGeocodingComplete(GeoLocation geoLocation);
}
