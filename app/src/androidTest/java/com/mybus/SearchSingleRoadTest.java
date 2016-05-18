package com.mybus;

import android.test.InstrumentationTestCase;

import com.mybus.asynctask.RoadSearchCallback;
import com.mybus.asynctask.RoadSearchTask;
import com.mybus.model.Road.RoadResult;
import com.mybus.model.Road.RoadSearch;

import java.util.concurrent.CountDownLatch;

/**
 * Used to test the async task which returns a single Road result
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class SearchSingleRoadTest extends InstrumentationTestCase implements RoadSearchCallback {
    private RoadSearchTask mAsycTask;
    private RoadResult mResult;
    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RoadSearch roadSearch = new RoadSearch("1", "0", "24", "39");
        mAsycTask = new RoadSearchTask(0, roadSearch, this);
        signal = new CountDownLatch(1); //CountDownLatch used to perform wait-notify behaviour
    }

    @Override
    public void onRoadFound(RoadResult roadResult) {
        this.mResult = roadResult;
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
            signal.await();// wait for callback
            assertTrue(mResult != null);
        } catch (InterruptedException e) {
            fail();
        }
    }
}
