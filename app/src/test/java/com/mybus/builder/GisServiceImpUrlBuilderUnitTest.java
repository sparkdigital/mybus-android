package com.mybus.builder;

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
public class GisServiceImpUrlBuilderUnitTest {

    @Test
    public void buildFindStreetsUrl() {
        String expectedUrl = GisServiceUrlBuilder.buildBaseUri().build().toString() +
                "/ws.php?method=rest&endpoint=callejero_mgp&nombre_calle=indep";
        String url = GisServiceUrlBuilder.buildFindStreetsUrl("indep");
        assertTrue(url.contains(expectedUrl));
    }

}
