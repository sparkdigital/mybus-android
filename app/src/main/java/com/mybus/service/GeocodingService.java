package com.mybus.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;

public interface GeocodingService {

    void performGeocodeByLocation(LatLng location, OnLocationGeocodingCompleteCallback callback, Context context);

    void performGeocodeByAddress(String address, OnAddressGeocodingCompleteCallback callback, Context context);
}
