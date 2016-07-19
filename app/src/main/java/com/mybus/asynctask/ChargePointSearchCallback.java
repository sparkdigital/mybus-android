package com.mybus.asynctask;

import com.mybus.model.ChargePoint;

import java.util.List;

/**
 * Created by ldimitroff on 05/07/16.
 */
public interface ChargePointSearchCallback {

    void onChargingPointsFound(List<ChargePoint> chargePoints);
}
