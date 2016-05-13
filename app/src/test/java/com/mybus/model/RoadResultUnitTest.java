package com.mybus.model;

import com.mybus.helper.FileLoaderHelper;
import com.mybus.model.Road.RoadResult;
import com.mybus.model.Road.Route;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ldimitroff on 12/05/16.
 */
public class RoadResultUnitTest {

    /**
     * Test a simple road result with type 0
     */
    @Test
    public void parseSimpleRoadResult() {
        RoadResult expectedResult = new RoadResult();
        expectedResult.setType(0);
        expectedResult.setTotalDistance(3F);
        expectedResult.setTravelTime(12);
        expectedResult.getRoutes().add(new Route());

        RoadResult roadResult = RoadResult.parse(FileLoaderHelper.loadJSONObjectFromResource(this, "road_result/road_result_simple.json"));
        assertNotNull("Result is 'Null'", roadResult);
        assertEquals(roadResult, expectedResult);
    }

    /**
     * Test a combinated road result with type 1
     */
    @Test
    public void parseCombinatedRoadResult() {
        RoadResult expectedResult = new RoadResult();
        expectedResult.setType(1);
        expectedResult.setTotalDistance(11F);
        expectedResult.setTravelTime(45);
        expectedResult.setIdBusLine1("20");
        expectedResult.setIdBusLine2("10");
        expectedResult.getRoutes().add(new Route());
        expectedResult.getRoutes().add(new Route());

        RoadResult roadResult = RoadResult.parse(FileLoaderHelper.loadJSONObjectFromResource(this, "road_result/road_result_combinated.json"));
        assertNotNull("Result is 'Null'", roadResult);
        assertEquals(roadResult, expectedResult);
    }

    /**
     * Tests an empty JSON must return a null object
     */
    @Test
    public void parseEmptyJsonTest() {
        RoadResult roadResult = RoadResult.parse(new JSONObject());
        assertNull("Result must be 'Null'", roadResult);
    }
}
