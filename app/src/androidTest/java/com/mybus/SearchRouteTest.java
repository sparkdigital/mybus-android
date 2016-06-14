package com.mybus;

import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.asynctask.RouteSearchCallback;
import com.mybus.asynctask.RouteSearchTask;
import com.mybus.model.BusRouteResult;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * Used to test the async task which returns the possible routes between two locations.
 */
public class SearchRouteTest extends InstrumentationTestCase implements RouteSearchCallback {
    private RouteSearchTask mAsycTask;
    private List<BusRouteResult> mResults;
    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAsycTask = new RouteSearchTask(new LatLng(-37.979858, -57.589794), new LatLng(-38.000452, -57.556120), this);
        signal = new CountDownLatch(1); //CountDownLatch used to perform wait-notify behaviour
    }

    @Override
    public void onRouteFound(List<BusRouteResult> results) {
        this.mResults = results;
        signal.countDown();
    }

    public void testCallBack() throws Throwable {
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAsycTask.execute();
            }
        });
        try {
            signal.await(); // wait for callback
            assertTrue(mResults.size() > 0);
        } catch (InterruptedException e) {
            fail();
        }
    }
}
