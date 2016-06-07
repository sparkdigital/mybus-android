package com.mybus;

import android.test.InstrumentationTestCase;

import com.mybus.service.GisServiceImpl;

import java.util.List;

/**
 * Created by ldimitroff on 24/05/16.
 */
public class GisServiceTest extends InstrumentationTestCase {

    private GisServiceImpl gisService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gisService = new GisServiceImpl();
    }

    public void testFindStreets() throws Throwable {
        List<String> result = gisService.findStreets("luro");
        assertTrue(result != null);
    }
}
