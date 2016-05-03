package com.mybus.suite;

import com.mybus.builder.UrlBuilderUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit Testing Suite: All Unit test classes should be added to @Suite.SuiteClasses.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UrlBuilderUnitTest.class})
public class UnitTestSuite {
}