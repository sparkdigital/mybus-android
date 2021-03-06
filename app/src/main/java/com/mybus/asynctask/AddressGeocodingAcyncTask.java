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
import java.nio.charset.Charset;

/**
 * Address Geocoding Class
 */
public class AddressGeocodingAcyncTask extends AsyncTask<String, Void, String> {

    private static final String TAG = AddressGeocodingAcyncTask.class.getSimpleName();
    //TODO remove this hardcoded city & CP codes, using a preferences to detect city
    //private static final List<String> MDQ_POSTAL_CODES = Arrays.asList("08183", "B7600", "B7601", "B7602", "B7603", "B7604", "B7605", "B7606", "B7608", "B7609", "B7611", "B7612");
    private static final String MDQ_CITY_NAME = ", Mar del Plata, Buenos Aires, Argentina";
    private static final String CITY_FILTER = "Gral Pueyrredón";
    private static final String ADMINISTRATIVE_AREA_LEVEL = "[\"administrative_area_level_2\",\"political\"]";

    private final Context mContext;
    private OnAddressGeocodingCompleteCallback callback;

    public AddressGeocodingAcyncTask(Context c, OnAddressGeocodingCompleteCallback callback) {
        this.mContext = c;
        this.callback = callback;
    }

    protected String getLocationFromHttp(String address) {
        address = address.replaceAll(" ", "%20");
        address = AddressValidator.normalizeAddress(address);
        try {
            URL url = new URL(mContext.getString(R.string.geocoding_url, address));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
            br.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
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

    /**
     * get the short_name from the administrative_area_level_2 section in the json
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private String getBigLocation(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = ((JSONArray) jsonObject.get("results"))
                .getJSONObject(0).getJSONArray("address_components");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String types = obj.getString("types");
            if (ADMINISTRATIVE_AREA_LEVEL.equals(types)) {
                return obj.getString("short_name");
            }
        }
        return null;
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
                String formattedAddress = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0).getString("formatted_address");
                String bigLocation = getBigLocation(jsonObject);
                //if the address fround is not from Mar del plata
                if (bigLocation.equals(CITY_FILTER)) {
                    formattedAddress = formattedAddress.split(",")[0];
                    formattedAddress = formattedAddress.replaceAll("&", "y");
                    geoLocation = getGeoLocationFromAddresses(new LatLng(latitude, longitude), formattedAddress);
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
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
