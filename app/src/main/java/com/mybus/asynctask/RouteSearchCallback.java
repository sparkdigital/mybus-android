package com.mybus.asynctask;

import org.json.JSONArray;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public interface RouteSearchCallback {
    void onRouteFound(JSONArray results);
}
