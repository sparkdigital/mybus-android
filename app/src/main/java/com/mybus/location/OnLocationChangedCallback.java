package com.mybus.location;

import com.google.android.gms.maps.model.LatLng;

public interface OnLocationChangedCallback {
    void onLocationChanged(LatLng latLng);
}
