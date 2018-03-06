package com.mybus.model;

import android.util.Log;

import com.mybus.helper.FileLoaderHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Lucas De Lio on 6/29/2016.
 */
@Ignore
public class FareParseUnitTest {

    @Test
    public void parseSimpleFareTest() throws JSONException {

        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromResource(this, "fare/fares_mock.json");
        JSONArray faresFromJson = null;
        try {
            faresFromJson = jsonMockedResponse.getJSONArray("Fares");
        } catch (JSONException e) {
            Log.e("FareParseUnitTest", e.getMessage());
        }
        List<Fare> busRoutesReceived = Fare.parseResults(faresFromJson);
        Fare fareFromMock = busRoutesReceived.get(0);
        assertEquals(fareFromMock.getCost(), "4.8");
        assertEquals(fareFromMock.getTitle(), "Local (Todas las líneas)");
    }
}
