package com.mybus.service;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.DirectionsResult;

/**
 * Created by ldimitroff on 17/05/16.
 */
public interface DirectionsService {

    DirectionsResult getDirections(LatLng origin, LatLng destination);
}
