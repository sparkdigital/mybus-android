package com.mybus.asynctask;

import com.mybus.model.Fare;

import java.util.List;

/**
 * Created by Lucas De Lio on 6/23/2016.
 */
public interface FaresRequestCallback {

    void onFaresFound(String fares);
}
