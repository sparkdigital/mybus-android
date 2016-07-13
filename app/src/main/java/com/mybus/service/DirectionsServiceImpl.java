package com.mybus.service;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

/**
 * Created by ldimitroff on 17/05/16.
 */
public class DirectionsServiceImpl implements DirectionsService {

    private static final String API_KEY = "AIzaSyCXgPZbEkpO_KsQqr5_q7bShcOhRlvodyc";
    private final GeoApiContext mGeoApiContext;

    public DirectionsServiceImpl() {
        mGeoApiContext = new GeoApiContext().setApiKey(API_KEY);
    }

    @Override
    public DirectionsResult getDirections(LatLng origin, LatLng destination) {
        return DirectionsApi.newRequest(mGeoApiContext)
                .origin(getLatLng(origin))
                .destination(getLatLng(destination))
                .mode(TravelMode.WALKING)
                .awaitIgnoreError();
    }

    /**
     * Returns a LatLgn model from com.google.maps.model
     *
     * @param latLng
     * @return
     */
    private com.google.maps.model.LatLng getLatLng(LatLng latLng) {
        return new com.google.maps.model.LatLng(latLng.latitude, latLng.longitude);
    }
}
