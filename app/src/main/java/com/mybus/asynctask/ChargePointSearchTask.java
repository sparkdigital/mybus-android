package com.mybus.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.ChargePoint;
import com.mybus.service.MyBusServiceImpl;

import java.util.List;

/**
 * Created by ldimitroff on 05/07/16.
 */
public class ChargePointSearchTask extends AsyncTask<Void, Integer, List<ChargePoint>> {

    private final LatLng mLocation;
    private final ChargePointSearchCallback mListener;

    public ChargePointSearchTask(LatLng location, ChargePointSearchCallback callback) {
        this.mLocation = location;
        this.mListener = callback;
    }

    @Override
    protected List<ChargePoint> doInBackground(Void... params) {
        return new MyBusServiceImpl().getNearChargePoints(mLocation);
    }

    @Override
    protected void onPostExecute(List<ChargePoint> chargePoints) {
        if (mListener != null) {
            mListener.onChargingPointsFound(chargePoints);
        }
    }
}
