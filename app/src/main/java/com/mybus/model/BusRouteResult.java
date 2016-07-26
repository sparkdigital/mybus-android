package com.mybus.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * This object represents a bus route between two locations (could be type 0: using 1 bus line or type 1: using 2 bus lines)
 */
public class BusRouteResult implements Parcelable {
    private static final String TAG = BusRouteResult.class.getSimpleName();
    private int mType;
    private List<BusRoute> mBusRoutes = new ArrayList<BusRoute>();
    private double mCombinationDistance; //Only used when type is 1

    public BusRouteResult(){}

    protected BusRouteResult(Parcel in) {
        mType = in.readInt();
        mBusRoutes = in.createTypedArrayList(BusRoute.CREATOR);
        mCombinationDistance = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mType);
        dest.writeTypedList(mBusRoutes);
        dest.writeDouble(mCombinationDistance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusRouteResult> CREATOR = new Creator<BusRouteResult>() {
        @Override
        public BusRouteResult createFromParcel(Parcel in) {
            return new BusRouteResult(in);
        }

        @Override
        public BusRouteResult[] newArray(int size) {
            return new BusRouteResult[size];
        }
    };

    public static List<BusRouteResult> parseResults(JSONArray results, int type) {
        if (results == null) {
            return null;
        }

        List<BusRouteResult> list = new ArrayList<>();
        JSONObject route = null;

        //NOTE: In a future we can read the type from each JSONObject and support multiples type results
        if (type == 0) { //Single routes
            for (int i = 0; i < results.length(); i++) {
                try {
                    route = results.getJSONObject(i);
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
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
                    Log.e(TAG, e.toString());
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
        String doubleString = route.optString("StartBusStopDistanceToOrigin");
        busRoute.setStartBusStopDistanceToOrigin(Double.valueOf(doubleString.replaceAll(",", ".")));
        busRoute.setDestinationBusStopNumber(route.optInt("DestinationBusStopNumber"));
        busRoute.setDestinationBusStopLat(route.optString("DestinationBusStopLat"));
        busRoute.setDestinationBusStopLng(route.optString("DestinatioBusStopLng")); //Destination is misspelled
        busRoute.setDestinationBusStopStreetName(route.optString("DestinatioBusStopStreetName")); //Destination is misspelled
        busRoute.setDestinationBusStopStreetNumber(route.optInt("DestinatioBusStopStreetNumber")); //Destination is misspelled
        doubleString = route.optString("DestinatioBusStopDistanceToDestination"); //Destination is misspelled
        busRoute.setDestinationBusStopDistanceToDestination(Double.valueOf(doubleString.replaceAll(",", ".")));
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
        //Bus Line information
        firstBusRoute.setIdBusLine(route.optInt("IdFirstBusLine"));
        firstBusRoute.setBusLineName(route.optString("FirstBusLineName"));
        firstBusRoute.setBusLineDirection(route.optInt("FirstBusLineDirection"));
        firstBusRoute.setBusLineColor(route.optString("FirstBusLineColor"));
        //Route information
        firstBusRoute.setStartBusStopNumber(route.optInt("FirstLineStartBusStopNumber"));
        firstBusRoute.setStartBusStopStreetName(route.optString("FirstLineStartBusStopStreet"));
        firstBusRoute.setStartBusStopStreetNumber(route.optInt("FirstLineStartBusStopStreetNumber"));
        firstBusRoute.setStartBusStopLat(route.optString("FirstLineStartBusStopLat"));
        firstBusRoute.setStartBusStopLng(route.optString("FirstLineStartBusStopLng"));
        firstBusRoute.setDestinationBusStopNumber(route.optInt("FirstLineDestinationBusStopNumber"));
        firstBusRoute.setDestinationBusStopStreetName(route.optString("FirstLineDestinatioBusStopStreet")); //Destination is misspelled
        firstBusRoute.setDestinationBusStopStreetNumber(route.optInt("FirstLineDestinatioBusStopStreetNumber")); //Destination is misspelled
        firstBusRoute.setDestinationBusStopLat(route.optString("FirstLineDestinatioBusStopLat")); //Destination is misspelled
        firstBusRoute.setDestinationBusStopLng(route.optString("FirstLineDestinatioBusStopLng")); //Destination is misspelled
        //Distance to start BusStop
        String doubleString = route.optString("FirstLineStartBusStopDistance");
        firstBusRoute.setStartBusStopDistanceToOrigin(Double.valueOf(doubleString.replaceAll(",", ".")));

        //Adding the first line route
        busRouteResult.getBusRoutes().add(firstBusRoute);

        //Parsing Second Line:
        BusRoute secondBusRoute = new BusRoute();
        //Bus Line information
        secondBusRoute.setIdBusLine(route.optInt("IdSecondBusLine"));
        secondBusRoute.setBusLineName(route.optString("SecondBusLineName"));
        secondBusRoute.setBusLineDirection(route.optInt("SecondBusLineDirection"));
        secondBusRoute.setBusLineColor(route.optString("SecondBusLineColor"));
        //Route information
        secondBusRoute.setStartBusStopNumber(route.optInt("SecondLineStartBusStopNumber"));
        secondBusRoute.setStartBusStopStreetName(route.optString("SecondLineStartBusStopStreet"));
        secondBusRoute.setStartBusStopStreetNumber(route.optInt("SecondLineStartBusStopStreetNumber"));
        secondBusRoute.setStartBusStopLat(route.optString("SecondLineStartBusStopLat"));
        secondBusRoute.setStartBusStopLng(route.optString("SecondLineStartBusStopLng"));
        secondBusRoute.setDestinationBusStopNumber(route.optInt("SecondLineDestinationBusStopNumber"));
        secondBusRoute.setDestinationBusStopStreetName(route.optString("SecondLineDestinationBusStopStreet"));
        secondBusRoute.setDestinationBusStopStreetNumber(route.optInt("SecondLineDestinationBusStopStreetNumber"));
        secondBusRoute.setDestinationBusStopLat(route.optString("SecondLineDestinationBusStopLat"));
        secondBusRoute.setDestinationBusStopLng(route.optString("SecondLineDestinationBusStopLng"));
        //Distance to Destination from DestinationBusStop
        doubleString = route.optString("SecondLineDestinationBusStopDistance");
        secondBusRoute.setDestinationBusStopDistanceToDestination(Double.valueOf(doubleString.replaceAll(",", ".")));
        //Adding the second line route
        busRouteResult.getBusRoutes().add(secondBusRoute);

        //Set the combination distance
        doubleString = route.optString("CombinationDistance");
        busRouteResult.setCombinationDistance(Double.valueOf(doubleString.replaceAll(",", ".")));
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

    public boolean isCombined() {
        return mType == 1;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (BusRoute route : getBusRoutes()) {
            result.append(route.toString())
                    .append("; ");
        }
        return result.toString();
    }
}
