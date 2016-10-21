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
public class AddressGeocodingAcyncTask extends AsyncTask<String, Void, GeoLocation> {

    private static final String TAG = AddressGeocodingAcyncTask.class.getSimpleName();
    //TODO remove this hardcoded city & CP codes, using a preferences to detect city
    //private static final List<String> MDQ_POSTAL_CODES = Arrays.asList("08183", "B7600", "B7601", "B7602", "B7603", "B7604", "B7605", "B7606", "B7608", "B7609", "B7611", "B7612");
    private static final String MDQ_CITY_NAME = ", Mar del Plata, Buenos Aires, Argentina";

    private final Context mContext;
    private OnAddressGeocodingCompleteCallback callback;

    public AddressGeocodingAcyncTask(Context c, OnAddressGeocodingCompleteCallback callback) {
        this.mContext = c;
        this.callback = callback;
    }

    protected GeoLocation getLocationFromHttp(String address) {
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
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONObject jsonObjectLocation = ((JSONArray) jsonObject.get("results"))
                    .getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location");
            double longitude = jsonObjectLocation.getDouble("lng");
            double latitude = jsonObjectLocation.getDouble("lat");
            //get the clean address from json
            String formatted_address = ((JSONArray) jsonObject.get("results"))
                    .getJSONObject(0).getString("formatted_address");
            //if the address fround is not from Mar del plata
            if (formatted_address == null || !formatted_address.contains("Mar del Plata")) {
                return null;
            }
            formatted_address = formatted_address.split(",")[0];
            return getGeoLocationFromAddresses(new LatLng(latitude, longitude), formatted_address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected GeoLocation doInBackground(String... strings) {
        String locationName = strings[0];
        if (!AddressValidator.isValidAddress(locationName)) {
            return null;
        }
        return getLocationFromHttp(locationName + MDQ_CITY_NAME);
    }

    @Override
    protected void onPostExecute(GeoLocation geoLocation) {
        callback.onAddressGeocodingComplete(geoLocation);
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
