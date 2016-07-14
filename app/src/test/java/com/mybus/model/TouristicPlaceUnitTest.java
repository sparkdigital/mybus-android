package com.mybus.model;

import android.util.Log;

import com.mybus.helper.FileLoaderHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by ldimitroff on 11/07/16.
 */
public class TouristicPlaceUnitTest {

    private static final String TAG = TouristicPlaceUnitTest.class.getSimpleName();

    @Test
    public void parseTouristicPlaceTest() throws JSONException {
        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromResource(this, "touristic_places/tour_places_mock.json");
        JSONArray jsonArray = null;
        try {
            if (jsonMockedResponse != null) {
                jsonArray = jsonMockedResponse.getJSONArray("Places");
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            jsonArray = null;
        }
        if (jsonArray == null) {
            fail("JSON Array was null");
        }
        List<TouristicPlace> touristicPlaces = TouristicPlace.parseJSONArray(jsonArray);
        TouristicPlace place = touristicPlaces.get(0);
        assertEquals(place.getName(), "Casino");
        assertEquals(place.getLatitude(), -38.0041782);
        assertEquals(place.getLongitude(), -57.5420009);
    }
}
