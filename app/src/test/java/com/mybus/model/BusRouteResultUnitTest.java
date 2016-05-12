package com.mybus.model;

import com.mybus.helper.FileLoaderHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusRouteResultUnitTest {
    /**
     * Tests Route results with type 0
     */
    @Test
    public void parseSimpleBusRouteResultTest() throws JSONException {
        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromResource(this, "bus_route_result/bus_route_result_simple.json");
        JSONArray resultsFromMockedResponse = jsonMockedResponse.getJSONArray("Results");
        // Checks results not null
        assertNotNull(resultsFromMockedResponse);
        List<BusRouteResult> busRoutesReceived = BusRouteResult.parseResults(resultsFromMockedResponse, 0);
        // Checks list not null
        assertNotNull(busRoutesReceived);
        assertTrue(busRoutesReceived.size() > 0);
        // Gets first bus route result
        BusRouteResult busRouteResult = busRoutesReceived.get(0);
        assertNotNull(busRouteResult);
        // Checks type
        assertTrue(busRouteResult.getType() == 0);
        // Checks BusRoutes list not null
        assertNotNull(busRouteResult.getBusRoutes());
        // Because is type 0, should have only one BusRoute
        assertTrue(busRouteResult.getBusRoutes().size() == 1);
        // Gets unique BusRoute
        BusRoute busRouteReceived = busRouteResult.getBusRoutes().get(0);
        // Set BusRoute expected
        BusRoute busRouteExpected = new BusRoute(1, "542", 0, "d60405", 24, "-38.013550", "-57.575436", "Dorrego", 4230, 214.24D, 39, "-38.010307", "-57.552765", "Santa Fe", 2930, 30.33D);
        // Checks objects:
        assertEquals(busRouteExpected, busRouteReceived);
    }
}
