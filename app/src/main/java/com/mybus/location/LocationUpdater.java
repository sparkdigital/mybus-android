package com.mybus.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

@SuppressWarnings("MissingPermission")
public class LocationUpdater implements LocationListener {

    private OnLocationChangedCallback onLocationChangedCallback;
    private LocationUpdater locationUpdater;
    private Context context;
    private boolean isFirstLocation;
    LocationManager locationManager;

    public LocationUpdater(OnLocationChangedCallback onLocationChangedCallback, Context context) {
        this.onLocationChangedCallback = onLocationChangedCallback;
        this.context = context;
        isFirstLocation = false;
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    /**
     * Get the last known gps location, null otherwise
     */
    public LatLng getLastKnownLocation() {
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if (lastKnownLocation == null) {
            return null;
        }
        return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
    }

    public void startListening() {
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    public boolean isFirstLocation() {
        if (isFirstLocation == true) {
            isFirstLocation = false;
            return isFirstLocation;
        } else {
            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        onLocationChangedCallback.onLocationChanged(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }


}
