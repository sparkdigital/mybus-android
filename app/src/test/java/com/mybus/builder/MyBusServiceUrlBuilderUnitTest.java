package com.mybus.builder;

import com.mybus.model.road.RoadSearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * @author Enrique Pennimpede <epennimpede@devspark.com>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MyBusServiceUrlBuilderUnitTest {

    @Test
    public void buildSingleRoadUrl() {
        String expectedUrl = MyBusServiceUrlBuilder.buildBaseUri().build().toString()
                + "/SingleRoadApi.php?idline=a&direction=b&stop1=c&stop2=d";
        RoadSearch roadSearch = new RoadSearch("a", "b", "c", "d");
        String url = MyBusServiceUrlBuilder.buildSingleRoadUrl(roadSearch);
        assertTrue(url.contains(expectedUrl));
    }

    @Test
    public void buildCombinedRoadUrl() {
        String expectedUrl = MyBusServiceUrlBuilder.buildBaseUri().build().toString()
                + "/CombinedRoadApi.php?idline1=a&idline2=b&direction1=c"
                + "&direction2=d&L1stop1=e&L1stop2=f&L2stop1=g&L2stop2=h";
        RoadSearch roadSearch = new RoadSearch("a", "b", "c", "d", "e", "f", "g", "h");
        String url = MyBusServiceUrlBuilder.buildCombinedRoadUrl(roadSearch);
        assertTrue(url.contains(expectedUrl));
    }

    @Test
    public void buildNexusUrl() {
        String expectedUrl = MyBusServiceUrlBuilder.buildBaseUri().build().toString()
                + "/NexusApi.php?lat0=1.0&lng0=1.0&lat1=2.0&lng1=2.0";
        String url = MyBusServiceUrlBuilder.buildNexusUrl(1d, 1d, 2d, 2d);
        assertTrue(url.contains(expectedUrl));
    }

}
