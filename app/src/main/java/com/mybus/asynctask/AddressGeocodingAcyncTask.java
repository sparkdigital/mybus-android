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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Address Geocoding Class
 */
public class AddressGeocodingAcyncTask extends AsyncTask<String, Void, GeoLocation> {

    private static final String TAG = AddressGeocodingAcyncTask.class.getSimpleName();
    //TODO remove this hardcoded city & CP codes, using a preferences to detect city
    private static final List<String> MDQ_POSTAL_CODES = Arrays.asList("08183", "B7600", "B7602", "B7603", "B7605", "B7606", "B7608", "B7611");
    private static final String MDQ_CITY_NAME = ", Mar del Plata, Buenos Aires, Argentina";

    private final Context mContext;
    private OnAddressGeocodingCompleteCallback callback;

    public AddressGeocodingAcyncTask(Context c, OnAddressGeocodingCompleteCallback callback) {
        this.mContext = c;
        this.callback = callback;
    }

    @Override
    protected GeoLocation doInBackground(String... strings) {
        String locationName = strings[0];
        if (!AddressValidator.isValidAddress(locationName)) {
            return null;
        }
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {

            addresses = geocoder.getFromLocationName(locationName + MDQ_CITY_NAME, 4);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e(TAG, "service_not_available", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e(TAG, "invalid_lat_long_used", illegalArgumentException);
        }

        return getGeoLocationFromAddresses(addresses);
    }

    @Override
    protected void onPostExecute(GeoLocation geoLocation) {
        callback.onAddressGeocodingComplete(geoLocation);
    }

    /**
     * Get's the first address with the same postal code as MDQ
     *
     * @param addresses
     * @return
     */
    private GeoLocation getGeoLocationFromAddresses(List<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            Log.e(TAG, "no_address_found");
            return null;
        }
        for (Address address : addresses) {
            if ((address.getPostalCode() != null) && (MDQ_POSTAL_CODES.contains(address.getPostalCode()))) {
                Log.i(TAG, "address_found");
                String addressString = AddressValidator.removeAccents(address.getAddressLine(0));
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                return new GeoLocation(addressString, latLng);
            }
        }
        return null;
    }
}
