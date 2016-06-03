package com.mybus.model;

import com.mybus.helper.FileLoaderHelper;
import com.mybus.model.Road.RoutePoint;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ldimitroff on 13/05/16.
 */
public class RoutePointUnitTest {

    @Test
    public void parseRoutePointNoWaypoint() {
        RoutePoint expectedPoint = new RoutePoint();
        expectedPoint.setStopId("2399");
        expectedPoint.setLat("-38.010307");
        expectedPoint.setLng("-57.552765");
        expectedPoint.setAddress("Santa Fe 2930");
        expectedPoint.setWaypoint(false);

        RoutePoint routePoint = RoutePoint.parse(FileLoaderHelper.loadJSONObjectFromResource(this, "route_point/route_point.json"));
        assertNotNull("Result is 'Null'", routePoint);

        assertTrue(routePoint.equals(expectedPoint));
    }

    @Test
    public void parseRoutePointWaypoint() {
        RoutePoint expectedPoint = new RoutePoint();
        expectedPoint.setStopId("2398");
        expectedPoint.setLat("-38.010872");
        expectedPoint.setLng("-57.553188");
        expectedPoint.setAddress("");
        expectedPoint.setWaypoint(true);

        RoutePoint routePoint = RoutePoint.parse(FileLoaderHelper.loadJSONObjectFromResource(this, "route_point/route_point_waypoint.json"));
        assertNotNull("Result is 'Null'", routePoint);

        assertTrue(routePoint.equals(expectedPoint));
    }

    @Test
    public void parseEmptyJSONRoutePoint() {
        RoutePoint routePoint = RoutePoint.parse(new JSONObject());
        assertNull("Result must be 'Null'", routePoint);
    }
}
