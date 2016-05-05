package com.mybus.suite;

import com.mybus.builder.GisServiceUrlBuilderUnitTest;
import com.mybus.builder.MyBusServiceUrlBuilderUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit Testing Suite: All Unit test classes should be added to @Suite.SuiteClasses.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MyBusServiceUrlBuilderUnitTest.class, GisServiceUrlBuilderUnitTest.class})
public class UnitTestSuite {
}
