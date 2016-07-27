package com.mybus.service;

import com.mybus.model.CompleteBusRoute;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public interface CompleteBusRouteService {

    CompleteBusRoute getCompleteRoute(int busLineId, String busLineName);
}
