package com.mybus.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 *
 * This object represents a bus route between two locations (could be type 0: using 1 bus line or type 1: using 2 bus lines)
 */
public class BusRouteResult {
    private int mType;
    private List<BusRoute> mBusRoutes = new ArrayList<BusRoute>();
    private double mCombinationDistance; //Only used when type is 1

    public static List<BusRouteResult> parseResults(JSONArray results, int type) {
        List<BusRouteResult> list = new ArrayList<>();
        if (results == null) {
            return null;
        }

        JSONObject route = null;

        //NOTE: In a future we can read the type from each JSONObject and support multiples type results
        if (type == 0) { //Single routes
            for (int i = 0; i < results.length(); i++) {
                try {
                    route = results.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BusRouteResult busRoute = parseSingleRoute(route);
                if (busRoute != null) {
                    list.add(busRoute);
                }
            }
            return list;
        }

        if (type == 1) { //Combined routes routes
            for (int i = 0; i < results.length(); i++) {
                try {
                    route = results.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BusRouteResult busRoute = parseCombinedRoute(route);
                if (busRoute != null) {
                    list.add(busRoute);
                }
            }
            return list;
        }

        return null;
    }


    private static BusRouteResult parseSingleRoute(JSONObject route) {
        if (route == null) {
            return null;
        }
        BusRouteResult busRouteResult = new BusRouteResult();
        busRouteResult.setType(0);
        BusRoute busRoute = new BusRoute();
        busRoute.setIdBusLine(route.optInt("IdBusLine"));
        busRoute.setBusLineName(route.optString("BusLineName"));
        busRoute.setBusLineDirection(route.optInt("BusLineDirection"));
        busRoute.setBusLineColor(route.optString("BusLineColor"));
        busRoute.setStartBusStopNumber(route.optInt("StartBusStopNumber"));
        busRoute.setStartBusStopLat(route.optString("StartBusStopLat"));
        busRoute.setStartBusStopLng(route.optString("StartBusStopLng"));
        busRoute.setStartBusStopStreetName(route.optString("StartBusStopStreetName"));
        busRoute.setStartBusStopStreetNumber(route.optInt("StartBusStopStreetNumber"));
        busRoute.setStartBusStopDistanceToOrigin(route.optDouble("StartBusStopDistanceToOrigin"));
        busRoute.setDestinationBusStopNumber(route.optInt("DestinationBusStopNumber"));
        busRoute.setDestinationBusStopLat(route.optString("DestinationBusStopLat"));
        busRoute.setDestinationBusStopLng(route.optString("DestinationBusStopLng"));
        busRoute.setDestinationBusStopStreetName(route.optString("DestinationBusStopStreetName"));
        busRoute.setDestinationBusStopStreetNumber(route.optInt("DestinationBusStopStreetNumber"));
        busRoute.setDestinationBusStopDistanceToDestination(route.optDouble("DestinationBusStopDistanceToDestination"));
        busRouteResult.getBusRoutes().add(busRoute);
        return busRouteResult;
    }

    private static BusRouteResult parseCombinedRoute(JSONObject route) {
        if (route == null) {
            return null;
        }

        BusRouteResult busRouteResult = new BusRouteResult();
        busRouteResult.setType(1);

        //Parsing First Line:
        BusRoute firstBusRoute = new BusRoute();
        firstBusRoute.setIdBusLine(route.optInt("IdFirstBusLine"));
        firstBusRoute.setBusLineName(route.optString("FirstBusLineName"));
        firstBusRoute.setBusLineDirection(route.optInt("FirstBusLineDirection"));
        firstBusRoute.setBusLineColor(route.optString("FirstBusLineColor"));
        firstBusRoute.setStartBusStopNumber(route.optInt("FirstLineStartBusStopNumber"));
        firstBusRoute.setStartBusStopLat(route.optString("FirstLineStartBusStopLat"));
        firstBusRoute.setStartBusStopLng(route.optString("FirstLineStartBusStopLng"));
        firstBusRoute.setStartBusStopStreetName(route.optString("FirstLineStartBusStopStreetName"));
        firstBusRoute.setStartBusStopStreetNumber(route.optInt("FirstLineStartBusStopStreetNumber"));
        firstBusRoute.setStartBusStopDistanceToOrigin(route.optDouble("FirstLineStartBusStopDistanceToOrigin"));
        firstBusRoute.setDestinationBusStopNumber(route.optInt("FirstLineDestinationBusStopNumber"));
        firstBusRoute.setDestinationBusStopLat(route.optString("FirstLineDestinationBusStopLat"));
        firstBusRoute.setDestinationBusStopLng(route.optString("FirstLineDestinationBusStopLng"));
        firstBusRoute.setDestinationBusStopStreetName(route.optString("FirstLineDestinationBusStopStreetName"));
        firstBusRoute.setDestinationBusStopStreetNumber(route.optInt("FirstLineDestinationBusStopStreetNumber"));
        firstBusRoute.setDestinationBusStopDistanceToDestination(route.optDouble("FirstLineDestinationBusStopDistanceToDestination"));
        busRouteResult.getBusRoutes().add(firstBusRoute);

        //Parsing First Line:
        BusRoute secondBusRoute = new BusRoute();
        secondBusRoute.setIdBusLine(route.optInt("IdSecondBusLine"));
        secondBusRoute.setBusLineName(route.optString("SecondBusLineName"));
        secondBusRoute.setBusLineDirection(route.optInt("SecondBusLineDirection"));
        secondBusRoute.setBusLineColor(route.optString("SecondBusLineColor"));
        secondBusRoute.setStartBusStopNumber(route.optInt("SecondLineStartBusStopNumber"));
        secondBusRoute.setStartBusStopLat(route.optString("SecondLineStartBusStopLat"));
        secondBusRoute.setStartBusStopLng(route.optString("SecondLineStartBusStopLng"));
        secondBusRoute.setStartBusStopStreetName(route.optString("SecondLineStartBusStopStreetName"));
        secondBusRoute.setStartBusStopStreetNumber(route.optInt("SecondLineStartBusStopStreetNumber"));
        secondBusRoute.setStartBusStopDistanceToOrigin(route.optDouble("SecondLineStartBusStopDistanceToOrigin"));
        secondBusRoute.setDestinationBusStopNumber(route.optInt("SecondLineDestinationBusStopNumber"));
        secondBusRoute.setDestinationBusStopLat(route.optString("SecondLineDestinationBusStopLat"));
        secondBusRoute.setDestinationBusStopLng(route.optString("SecondLineDestinationBusStopLng"));
        secondBusRoute.setDestinationBusStopStreetName(route.optString("SecondLineDestinationBusStopStreetName"));
        secondBusRoute.setDestinationBusStopStreetNumber(route.optInt("SecondLineDestinationBusStopStreetNumber"));
        secondBusRoute.setDestinationBusStopDistanceToDestination(route.optDouble("SecondLineDestinationBusStopDistanceToDestination"));
        busRouteResult.getBusRoutes().add(secondBusRoute);

        busRouteResult.setCombinationDistance(route.optDouble("CombinationDistance"));
        return busRouteResult;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public int getType() {
        return this.mType;
    }

    public List<BusRoute> getBusRoutes() {
        return mBusRoutes;
    }

    public void setCombinationDistance(double combinationDistance) {
        this.mCombinationDistance = combinationDistance;
    }

    public double getCombinationDistance() {
        return this.mCombinationDistance;
    }
}