package com.mybus;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationUpdater implements LocationListener {

    MainActivity mapActivity;

    public LocationUpdater(MainActivity mapActivity) {
        this.mapActivity = mapActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        mapActivity.setCurrentLocation(location.getLatitude(), location.getLongitude());
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
