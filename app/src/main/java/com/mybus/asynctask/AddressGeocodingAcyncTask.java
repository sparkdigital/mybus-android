package com.mybus.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.R;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.model.GeoLocation;
import com.mybus.requirements.AddressValidator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Address Geocoding Class
 */
public class AddressGeocodingAcyncTask extends AsyncTask<String, Void, String> {

    private static final String TAG = AddressGeocodingAcyncTask.class.getSimpleName();
    //TODO remove this hardcoded city & CP codes, using a preferences to detect city
    //private static final List<String> MDQ_POSTAL_CODES = Arrays.asList("08183", "B7600", "B7601", "B7602", "B7603", "B7604", "B7605", "B7606", "B7608", "B7609", "B7611", "B7612");
    private static final String MDQ_CITY_NAME = ", Mar del Plata, Buenos Aires, Argentina";
    private static final String CITY_FILTER = "Gral Pueyrredón";

    private final Context mContext;
    private OnAddressGeocodingCompleteCallback callback;

    public AddressGeocodingAcyncTask(Context c, OnAddressGeocodingCompleteCallback callback) {
        this.mContext = c;
        this.callback = callback;
    }

    protected String getLocationFromHttp(String address) {
        address = address.replaceAll(" ", "%20");
        try {
            URL url = new URL(mContext.getString(R.string.geocoding_url, address));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected String doInBackground(String... strings) {
        String locationName = strings[0];
        if (!AddressValidator.isValidAddress(locationName)) {
            return null;
        }
        return getLocationFromHttp(locationName + MDQ_CITY_NAME);
    }

    @Override
    protected void onPostExecute(String result) {
        GeoLocation geoLocation = null;
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jsonObjectLocation = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location");
                double longitude = jsonObjectLocation.getDouble("lng");
                double latitude = jsonObjectLocation.getDouble("lat");
                //get the clean address from json
                String formatted_address = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0).getString("formatted_address");
                String bigLocation = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0).getJSONArray("address_components")
                        .getJSONObject(2)
                        .getString("short_name");
                String bigLocation2 = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0).getJSONArray("address_components")
                        .getJSONObject(3)
                        .getString("short_name");
                //if the address fround is not from Mar del plata
                if (bigLocation.equals(CITY_FILTER) || bigLocation2.equals(CITY_FILTER)) {
                    formatted_address = formatted_address.split(",")[0];
                    geoLocation = getGeoLocationFromAddresses(new LatLng(latitude, longitude), formatted_address);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            callback.onAddressGeocodingComplete(geoLocation);
        }
    }

    /**
     * Get's the first address with the same postal code as MDQ
     *
     * @param latLng
     * @param address
     * @return
     */
    private GeoLocation getGeoLocationFromAddresses(LatLng latLng, String address) {
        if (latLng == null) {
            Log.e(TAG, "no_address_found");
            return null;
        }
        Log.i(TAG, "address_found");
        String addressString = AddressValidator.normalizeAddress(address);
        return new GeoLocation(addressString, latLng);
    }
}
