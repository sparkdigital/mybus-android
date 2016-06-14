package com.mybus.model;

import com.mybus.helper.FileLoaderHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
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
        // Checks size
        assertTrue(busRoutesReceived.size() == 4);
        // Checks type for each BusRouteResult
        for (BusRouteResult result : busRoutesReceived) {
            assertEquals(result.getType(), 0);
            assertFalse(result.isCombined());
        }
        // Gets first bus route result
        BusRouteResult busRouteResult = busRoutesReceived.get(0);
        assertNotNull(busRouteResult);
        // Checks BusRoutes list not null
        assertNotNull(busRouteResult.getBusRoutes());
        // Because is type 0, should have only one BusRoute
        assertTrue(busRouteResult.getBusRoutes().size() == 1);
        // Gets unique BusRoute
        BusRoute busRouteReceived = busRouteResult.getBusRoutes().get(0);
        // Set BusRoute expected
        BusRoute busRouteExpected = new BusRoute(1, "542", 0, "d60405", 24, "-38.013550", "-57.575436",
                "Dorrego", 4230, 214.24D, 39, "-38.010307", "-57.552765", "Santa Fe", 2930, 30.33D);
        // Checks objects:
        assertEquals(busRouteExpected, busRouteReceived);
    }

    /**
     * Tests Route results with type 1
     */
    @Test
    public void parseCombinedBusRouteResultTest() throws JSONException {
        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromResource(this, "bus_route_result/bus_route_result_combined.json");
        JSONArray resultsFromMockedResponse = jsonMockedResponse.getJSONArray("Results");
        // Checks results not null
        assertNotNull(resultsFromMockedResponse);
        List<BusRouteResult> busRoutesReceived = BusRouteResult.parseResults(resultsFromMockedResponse, 1);
        // Checks list not null
        assertNotNull(busRoutesReceived);
        // Checks size
        assertTrue(busRoutesReceived.size() == 5);
        // Checks type for each BusRouteResult
        for (BusRouteResult result : busRoutesReceived) {
            assertEquals(result.getType(), 1);
            assertTrue(result.isCombined());
        }
        // Gets first bus route result
        BusRouteResult busRouteResult = busRoutesReceived.get(0);
        assertNotNull(busRouteResult);
        // Checks BusRoutes list not null
        assertNotNull(busRouteResult.getBusRoutes());
        // Because is type 1, should have two BusRoute's
        assertTrue(busRouteResult.getBusRoutes().size() == 2);

        BusRoute firstBusRouteReceived = busRouteResult.getBusRoutes().get(0); // Gets first BusRoute
        BusRoute firstBusRouteExpected = new BusRoute(2, "563a", 0, "00338e", 107, "-38.017799",
                "-57.572887", "Av Juan B Justo", 3385, 406.39D, 114, "-38.027122", "-57.560394",
                "Bernardo O Higgins", 2050, null); // Set first BusRoute expected
        BusRoute secondBusRouteReceived = busRouteResult.getBusRoutes().get(1); // Gets second BusRoute
        BusRoute secondBusRouteExpected = new BusRoute(5, "523", 0, "d60405", 92, "-38.026207",
                "-57.560745", "Av Juan B Justo", 1975, null, 129, "-38.069633", "-57.555370",
                "Gral Pacheco", 2980, 365.23D); // Set second BusRoute expected
        // Checks objects:
        assertEquals(firstBusRouteExpected, firstBusRouteReceived);
        assertEquals(secondBusRouteExpected, secondBusRouteReceived);
        // Checks combination distance (is te distance between the FirstLine's destination stop and SecondLine's initial stop)
        assertEquals(busRouteResult.getCombinationDistance(), 98.05D);
    }
}
