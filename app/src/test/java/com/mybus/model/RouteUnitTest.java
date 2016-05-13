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
        List<RoutePoint> pointList = parsedRoute.getPointList();
        assertNotNull(pointList);
        assertEquals(pointList.size(), 8);
    }
}
