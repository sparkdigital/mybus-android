package com.mybus.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationGeocoding {

    private Context mContext;

    public LocationGeocoding(Context context){
        mContext = context;
    }

    public String reverseGeocode(LatLng latLng){
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,4);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e("TAG", "string.service_not_available", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e("TAG", "string.invalid_lat_long_used" + ". " +
                    "Latitude = " + latLng.latitude +
                    ", Longitude = " + latLng.longitude, illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            Log.e("TAG", "no_address_found");
        } else {
            Address address = addresses.get(0);

            // Fetch the address lines using {@code getAddressLine},
            // join them, and send them to the thread. The {@link android.location.address}
            // class provides other options for fetching address details that you may prefer
            // to use. Here are some examples:
            // getLocality() ("Mountain View", for example)
            // getAdminArea() ("CA", for example)
            // getPostalCode() ("94043", for example)
            // getCountryCode() ("US", for example)
            // getCountryName() ("United States", for example)
            Log.i("TAG", "address_found");
            Toast.makeText(mContext,address.getThoroughfare() +" "+address.getFeatureName(),Toast.LENGTH_LONG).show();
            return address.getThoroughfare() +" "+address.getFeatureName();
        }
        return null;
    }

    public LatLng geocode(String locationName){
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(locationName,4);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e("TAG", "string.service_not_available", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e("TAG", "string.invalid_lat_long_used" , illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            Log.e("TAG", "no_address_found");
        } else {
            Address address = addresses.get(0);

            Log.i("TAG", "address_found");
            Toast.makeText(mContext,address.getThoroughfare() +" "+address.getFeatureName(),Toast.LENGTH_LONG).show();
            return new LatLng(address.getLatitude(),address.getLongitude());
        }
        return null;
    }

}
