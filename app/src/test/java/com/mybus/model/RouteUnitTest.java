package com.mybus.model;

import com.mybus.helper.FileLoaderHelper;
import com.mybus.model.Road.Route;
import com.mybus.model.Road.RoutePoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class RouteUnitTest {
    /**
     * Tests parsing for a Route
     */
    @Test
    public void parseSimpleBusRouteResultTest() throws JSONException {
        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromResource(this, "route/route.json");
        JSONArray routeArray = jsonMockedResponse.getJSONArray("Route1");
        Route parsedRoute = Route.parse(routeArray);
        // Checks PointList
        assertNotNull(parsedRoute.getPointList());
        assertEquals(parsedRoute.getPointList().size(), 2);

        Route expectedRoute = new Route();
        RoutePoint rPoint1 = new RoutePoint("3710", "-38.017799", "-57.572887", "Av Juan B Justo 3385", false);
        RoutePoint rPoint2 = new RoutePoint("3711", "-38.019493", "-57.570572", "Av Juan B Justo 3125", false);
        expectedRoute.getPointList().add(rPoint1);
        expectedRoute.getPointList().add(rPoint2);
        // Checks entire object
        assertEquals(expectedRoute, parsedRoute);
    }
}
