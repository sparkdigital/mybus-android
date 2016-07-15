package com.mybus.asynctask;

import com.mybus.model.BusLine;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public interface BusLinesRequestCallback {

    void onBusLinesFound(List<BusLine> busLines);
}
