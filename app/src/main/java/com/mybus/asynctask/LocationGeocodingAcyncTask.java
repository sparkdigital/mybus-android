package com.mybus.asynctask;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.location.OnLocationGeocodingCompleteCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Location Geocoding Class
 */
public class LocationGeocodingAcyncTask extends AsyncTask<LatLng, Void, String> {

    private static final String TAG = "LocationGeocoding";
    private OnLocationGeocodingCompleteCallback callback;
    private Context mContext;

    public LocationGeocodingAcyncTask(Context c, OnLocationGeocodingCompleteCallback callback) {
        this.mContext = c;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(LatLng... latLngs) {
        LatLng latLng = latLngs[0];
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 4);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e(TAG, "service_not_available", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e(TAG, "invalid_lat_long_used" + ". " +
                    "Latitude = " + latLng.latitude +
                    ", Longitude = " + latLng.longitude, illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            Log.e(TAG, "no_address_found");
        } else {
            Address address = addresses.get(0);
            Log.i(TAG, "address_found");
            return address.getThoroughfare() + " " + address.getFeatureName();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String address) {
        super.onPostExecute(address);
        callback.onLocationGeocodingComplete(address);
    }
}