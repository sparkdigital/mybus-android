package com.mybus.service;

import android.content.Context;

import com.mybus.model.BusLine;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public interface BusLineService {

    List<BusLine> getBusLines(Context context);
}
