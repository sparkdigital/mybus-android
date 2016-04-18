package com.mybus.model;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusRoute {
    int mIdBusLine;
    String mBusLineName;
    int mBusLineDirection;
    String mBusLineColor;
    int mStartBusStopNumber;
    String mStartBusStopLat;
    String mStartBusStopLng;
    String mStartBusStopStreetName;
    int mStartBusStopStreetNumber;
    double mStartBusStopDistanceToOrigin;
    int mDestinationBusStopNumber;
    String mDestinationBusStopLat;
    String mDestinationBusStopLng;
    String mDestinationBusStopStreetName;
    int mDestinationBusStopStreetNumber;
    double mDestinationBusStopDistanceToDestination;

    public int getIdBusLine() {
        return mIdBusLine;
    }

    public void setIdBusLine(int idBusLine) {
        this.mIdBusLine = idBusLine;
    }

    public String getBusLineName() {
        return mBusLineName;
    }

    public void setBusLineName(String busLineName) {
        this.mBusLineName = busLineName;
    }

    public int getBusLineDirection() {
        return mBusLineDirection;
    }

    public void setBusLineDirection(int busLineDirection) {
        this.mBusLineDirection = busLineDirection;
    }

    public String getBusLineColor() {
        return mBusLineColor;
    }

    public void setBusLineColor(String busLineColor) {
        this.mBusLineColor = busLineColor;
    }

    public int getStartBusStopNumber() {
        return mStartBusStopNumber;
    }

    public void setStartBusStopNumber(int startBusStopNumber) {
        this.mStartBusStopNumber = startBusStopNumber;
    }

    public String getStartBusStopLat() {
        return mStartBusStopLat;
    }

    public void setStartBusStopLat(String startBusStopLat) {
        this.mStartBusStopLat = startBusStopLat;
    }

    public String getStartBusStopLng() {
        return mStartBusStopLng;
    }

    public void setStartBusStopLng(String startBusStopLng) {
        this.mStartBusStopLng = startBusStopLng;
    }

    public String getStartBusStopStreetName() {
        return mStartBusStopStreetName;
    }

    public void setStartBusStopStreetName(String startBusStopStreetName) {
        this.mStartBusStopStreetName = startBusStopStreetName;
    }

    public int getStartBusStopStreetNumber() {
        return mStartBusStopStreetNumber;
    }

    public void setStartBusStopStreetNumber(int startBusStopStreetNumber) {
        this.mStartBusStopStreetNumber = startBusStopStreetNumber;
    }

    public double getStartBusStopDistanceToOrigin() {
        return mStartBusStopDistanceToOrigin;
    }

    public void setStartBusStopDistanceToOrigin(double startBusStopDistanceToOrigin) {
        this.mStartBusStopDistanceToOrigin = startBusStopDistanceToOrigin;
    }

    public int getDestinationBusStopNumber() {
        return mDestinationBusStopNumber;
    }

    public void setDestinationBusStopNumber(int destinationBusStopNumber) {
        this.mDestinationBusStopNumber = destinationBusStopNumber;
    }

    public String getDestinationBusStopLat() {
        return mDestinationBusStopLat;
    }

    public void setDestinationBusStopLat(String destinationBusStopLat) {
        this.mDestinationBusStopLat = destinationBusStopLat;
    }

    public String getDestinationBusStopLng() {
        return mDestinationBusStopLng;
    }

    public void setDestinationBusStopLng(String destinationBusStopLng) {
        this.mDestinationBusStopLng = destinationBusStopLng;
    }

    public String getDestinationBusStopStreetName() {
        return mDestinationBusStopStreetName;
    }

    public void setDestinationBusStopStreetName(String destinationBusStopStreetName) {
        this.mDestinationBusStopStreetName = destinationBusStopStreetName;
    }

    public int getDestinationBusStopStreetNumber() {
        return mDestinationBusStopStreetNumber;
    }

    public void setDestinationBusStopStreetNumber(int destinationBusStopStreetNumber) {
        this.mDestinationBusStopStreetNumber = destinationBusStopStreetNumber;
    }

    public double getDestinationBusStopDistanceToDestination() {
        return mDestinationBusStopDistanceToDestination;
    }

    public void setDestinationBusStopDistanceToDestination(double destinationBusStopDistanceToDestination) {
        this.mDestinationBusStopDistanceToDestination = destinationBusStopDistanceToDestination;
    }
}
