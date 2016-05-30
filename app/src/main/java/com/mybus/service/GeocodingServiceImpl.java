package com.mybus.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.asynctask.AddressGeocodingAcyncTask;
import com.mybus.asynctask.LocationGeocodingAcyncTask;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.requirements.AddressValidator;

public class GeocodingServiceImpl implements GeocodingService {


    @Override
    public void performGeocodeByLocation(LatLng location, OnLocationGeocodingCompleteCallback callback, Context context) {
        LocationGeocodingAcyncTask locationGeocodingTask = new LocationGeocodingAcyncTask(context, callback);
        locationGeocodingTask.execute(new LatLng[]{location});
    }

    @Override
    public void performGeocodeByAddress(String address, OnAddressGeocodingCompleteCallback callback, Context context) {
        if (!AddressValidator.isValidAddress(address)) {
            callback.onAddressGeocodingComplete(null);
            return;
        }
        //TODO remove this hardcoded city, used to filter Mar del Plata results
        address += " mar del plata";
        AddressGeocodingAcyncTask addressGeocodingTask = new AddressGeocodingAcyncTask(context, callback);
        addressGeocodingTask.execute(new String[]{address});
    }
}
