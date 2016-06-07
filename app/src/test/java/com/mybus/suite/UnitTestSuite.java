package com.mybus.suite;

import com.mybus.builder.GisServiceUrlBuilderUnitTest;
import com.mybus.builder.MyBusServiceUrlBuilderUnitTest;
import com.mybus.model.BusRouteResultUnitTest;
import com.mybus.model.RoadResultUnitTest;
import com.mybus.model.RoutePointUnitTest;
import com.mybus.model.RouteUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit Testing Suite: All Unit test classes should be added to @Suite.SuiteClasses.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MyBusServiceUrlBuilderUnitTest.class, GisServiceUrlBuilderUnitTest.class,
        RouteUnitTest.class, RoutePointUnitTest.class, RoadResultUnitTest.class,
        BusRouteResultUnitTest.class})
public class UnitTestSuite {
}
