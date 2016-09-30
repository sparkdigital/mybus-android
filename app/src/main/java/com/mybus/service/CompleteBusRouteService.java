package com.mybus.service;

import com.mybus.model.CompleteBusRoute;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public interface CompleteBusRouteService {

    /**
     * Return the complete bus line route in both directions.
     * @param busLineId
     * @param busLineName
     * @return
     */
    CompleteBusRoute getCompleteRoute(int busLineId, String busLineName);
}
