package com.mybus;

import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.asynctask.ChargePointSearchTask;
import com.mybus.asynctask.ChargePointSearchCallback;
import com.mybus.model.ChargePoint;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Used to test the async task which returns a list of charging points
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class ChargePointTaskTest extends InstrumentationTestCase implements ChargePointSearchCallback {
    private ChargePointSearchTask mAsycTask;
    private List<ChargePoint> mResults;
    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAsycTask = new ChargePointSearchTask(new LatLng(-37.979858, -57.589794), this);
        signal = new CountDownLatch(1); //CountDownLatch used to perform wait-notify behaviour
    }

    @Override
    public void onChargingPointsFound(List<ChargePoint> chargePoints) {
        this.mResults = chargePoints;
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
            assertFalse(mResults.isEmpty());
        } catch (InterruptedException e) {
            fail();
        }
    }
}
