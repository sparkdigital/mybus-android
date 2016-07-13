package com.mybus.model;

import android.util.Log;

import com.mybus.helper.FileLoaderHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusLinesParseUnitTest {

    @Test
    public void parseBusLinesTest() throws JSONException {

        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromResource(this, "bus_lines/bus_lines_mock.json");
        JSONArray busLinesFromJSON = null;
        try {
            busLinesFromJSON = jsonMockedResponse.getJSONArray("Results");
        } catch (JSONException e) {
            Log.e("BusLinesParseUnitTest", e.getMessage());
        }
        List<BusLine> busLinesReceived = BusLine.parseResults(busLinesFromJSON);
        BusLine busLineFromMock = busLinesReceived.get(0);
        assertEquals(busLineFromMock.getId(), 3);
        assertEquals(busLineFromMock.getName(), "720");
        assertEquals(busLineFromMock.getColor(), "#01755e");
    }
}
