package com.mybus.service;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.RoadResult;
import com.mybus.model.Road.RoadSearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public interface MyBusService {

    List<BusRouteResult> searchRoutes(LatLng origin, LatLng destiny);

    RoadResult searchRoads(int mType, RoadSearch roadSearch);
}
