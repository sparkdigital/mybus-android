package com.mybus.asynctask;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.model.GeoLocation;
import com.mybus.requirements.AddressValidator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Address Geocoding Class
 */
public class AddressGeocodingAcyncTask extends AsyncTask<String, Void, GeoLocation> {

    private static final String TAG = "AddressGeocoding";
    private final Context mContext;
    private OnAddressGeocodingCompleteCallback callback;

    public AddressGeocodingAcyncTask(Context c, OnAddressGeocodingCompleteCallback callback) {
        this.mContext = c;
        this.callback = callback;
    }

    @Override
    protected GeoLocation doInBackground(String... strings) {
        String addressStr = strings[0];
        if (!AddressValidator.isValidAddress(addressStr)) {
            return null;
        }
        //TODO remove this hardcoded city, used to filter Mar del Plata results
        String locationName = addressStr + " mar del plata";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(locationName, 4);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e(TAG, "service_not_available", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e(TAG, "invalid_lat_long_used", illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.isEmpty()) {
            Log.e(TAG, "no_address_found");
        } else {
            Address address = addresses.get(0);
            Log.i(TAG, "address_found");
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            return new GeoLocation(addressStr, latLng);
        }
        return null;
    }

    @Override
    protected void onPostExecute(GeoLocation geoLocation) {
        callback.onAddressGeocodingComplete(geoLocation);
    }
}
