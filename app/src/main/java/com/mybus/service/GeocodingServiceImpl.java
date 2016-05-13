package com.mybus.service;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingServiceImpl implements GeocodingService{

    private static Context mContext;
    private static GeocodingServiceImpl instance;
    private static final String TAG = "LocationGeocoding";

    public GeocodingServiceImpl() {
    }

    @Override
    public void performGeocodeByLocation(LatLng location, OnLocationGeocodingCompleteCallback callback, Context context) {
        this.mContext = context;
        LocationGeocodingAcyncTask locationGeocodingTask = new LocationGeocodingAcyncTask(callback);
        locationGeocodingTask.execute(new LatLng[]{location});
    }

    @Override
    public void performGeocodeByAddress(String address, OnAddressGeocodingCompleteCallback callback, Context context) {
        this.mContext = context;
        //TODO remove this hardcoded city, used to filter Mar del Plata results
        address+=", mar del plata";
        AddressGeocodingAcyncTask addressGeocodingTask = new AddressGeocodingAcyncTask(callback);
        addressGeocodingTask.execute(new String[]{address});
    }

    private class AddressGeocodingAcyncTask extends AsyncTask<String, Void, LatLng> {

        private OnAddressGeocodingCompleteCallback callback;

        public AddressGeocodingAcyncTask(OnAddressGeocodingCompleteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected LatLng doInBackground(String... strings) {
            String locationName = strings[0];
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
            if (addresses == null || addresses.size() == 0) {
                Log.e(TAG, "no_address_found");
            } else {
                Address address = addresses.get(0);
                Log.i(TAG, "address_found");
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
            return null;
        }

        @Override
        protected void onPostExecute(LatLng location) {
            super.onPostExecute(location);
            callback.onAddressGeocodingComplete(location);
        }
    }

    private class LocationGeocodingAcyncTask extends AsyncTask<LatLng, Void, String> {

        private OnLocationGeocodingCompleteCallback callback;

        public LocationGeocodingAcyncTask(OnLocationGeocodingCompleteCallback callback) {
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

}
