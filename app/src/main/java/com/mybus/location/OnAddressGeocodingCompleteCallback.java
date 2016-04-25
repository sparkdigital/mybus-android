package com.mybus.location;

import com.google.android.gms.maps.model.LatLng;

public interface OnAddressGeocodingCompleteCallback {
    void onAddressGeocodingComplete(LatLng location);
}
