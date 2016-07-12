package com.mybus.model;

import com.mybus.helper.FileLoaderHelper;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by ldimitroff on 05/07/16.
 */
public class ChargePointUnitTest {

    @Test
    public void parseChargePoint() {
        ChargePoint expectedResult = new ChargePoint();
        expectedResult.setID("108");
        expectedResult.setName("Matheu 3302");
        expectedResult.setAddress("Matheu 3302");
        expectedResult.setLatitude("-38.012596");
        expectedResult.setLongitude("-57.567554");
        expectedResult.setOpenTime("08-21hr");
        expectedResult.setDistance("334");

        ChargePoint chargePoint = ChargePoint.parseRoutePoint(FileLoaderHelper.loadJSONObjectFromResource(this, "charge_point/charge_point.json"));
        assertNotNull("Result is 'Null'", chargePoint);
        assertEquals(chargePoint, expectedResult);
    }

    /**
     * Tests an empty JSON must return a null object
     */
    @Test
    public void parseEmptyJsonTest() {
        ChargePoint chargePoint = ChargePoint.parseRoutePoint(new JSONObject());
        assertNull("Result must be 'Null'", chargePoint);
    }
}
