package com.mybus.model;

import com.mybus.helper.FileLoaderHelper;
import com.mybus.model.Road.RoadResult;

import org.json.JSONObject;
import org.junit.Test;

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

        RoadResult roadResult = RoadResult.parse(FileLoaderHelper.loadJSONObjectFromResource(this, "road_result/road_result_simple.json"));
        assertNotNull("Result is 'Null'", roadResult);

        assertTrue(expectedResult.getType() == roadResult.getType());
        assertTrue(expectedResult.getTotalDistance() == roadResult.getTotalDistance());
        assertTrue(expectedResult.getTravelTime() == roadResult.getTravelTime());
        assertTrue(roadResult.getRoutes().size() == 1);
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

        RoadResult roadResult = RoadResult.parse(FileLoaderHelper.loadJSONObjectFromResource(this, "road_result/road_result_combinated.json"));
        assertNotNull("Result is 'Null'", roadResult);

        assertTrue(expectedResult.getType() == roadResult.getType());
        assertTrue(expectedResult.getTotalDistance() == roadResult.getTotalDistance());
        assertTrue(expectedResult.getTravelTime() == roadResult.getTravelTime());
        assertTrue(expectedResult.getIdBusLine1().equals(roadResult.getIdBusLine1()));
        assertTrue(expectedResult.getIdBusLine2().equals(roadResult.getIdBusLine2()));
        assertTrue(roadResult.getRoutes().size() == 2);
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
