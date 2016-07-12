package com.mybus.service;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.BusRouteResult;
import com.mybus.model.ChargePoint;
import com.mybus.model.road.RoadResult;
import com.mybus.model.road.RoadSearch;

import java.util.List;

public interface MyBusService {

    List<BusRouteResult> searchRoutes(LatLng origin, LatLng destiny);

    RoadResult searchRoads(int mType, RoadSearch roadSearch);

    List<ChargePoint> getNearChargePoints(LatLng location);
}
