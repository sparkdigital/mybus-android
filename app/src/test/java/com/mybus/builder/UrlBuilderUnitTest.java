package com.mybus.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * @author Enrique Pennimpede <epennimpede@devspark.com>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class UrlBuilderUnitTest {

    @Test
    public void buildSingleRoadUri() {
        String expectedUrl = "http://www.mybus.com.ar/api/v1/SingleRoadApi.php?idline=a" +
                "&direction=b&stop1=c&stop2=d&tk=94a08da1fecbb6e8b46990538c7b50b2";
        String url = UrlBuilder.buildSingleRoadUrl("a", "b", "c", "d");
        assertEquals(expectedUrl, url);
    }

    @Test
    public void buildCombinedRoadUri() {
        String expectedUrl = "http://www.mybus.com.ar/api/v1/CombinedRoadApi.php?idline1=a" +
                "&idline2=b&direction1=c&direction2=d&L1stop1=e&L1stop2=f&L2stop1=g" +
                "&L2stop2=h&tk=94a08da1fecbb6e8b46990538c7b50b2";
        String url = UrlBuilder.buildCombinedRoadUrl("a", "b", "c", "d", "e" , "f", "g" , "h");
        assertEquals(expectedUrl, url);
    }

}
