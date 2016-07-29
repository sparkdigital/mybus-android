package com.mybus.asynctask;

import com.mybus.model.CompleteBusRoute;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public interface CompleteBusRouteCallback {

    void onCompleteRouteFound(int busLineId, CompleteBusRoute completeBusRoute);
}
