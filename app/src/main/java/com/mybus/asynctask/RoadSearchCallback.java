package com.mybus.asynctask;

import com.mybus.model.road.RoadResult;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public interface RoadSearchCallback {
    void onRoadFound(RoadResult roadResult);
}
