package com.mybus.model;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusRoute {
    Integer mIdBusLine;
    String mBusLineName;
    Integer mBusLineDirection;
    String mBusLineColor;
    Integer mStartBusStopNumber;
    String mStartBusStopLat;
    String mStartBusStopLng;
    String mStartBusStopStreetName;
    Integer mStartBusStopStreetNumber;
    Double mStartBusStopDistanceToOrigin;
    Integer mDestinationBusStopNumber;
    String mDestinationBusStopLat;
    String mDestinationBusStopLng;
    String mDestinationBusStopStreetName;
    Integer mDestinationBusStopStreetNumber;
    Double mDestinationBusStopDistanceToDestination;

    public Integer getIdBusLine() {
        return mIdBusLine;
    }

    public void setIdBusLine(Integer idBusLine) {
        this.mIdBusLine = idBusLine;
    }

    public String getBusLineName() {
        return mBusLineName;
    }

    public void setBusLineName(String busLineName) {
        this.mBusLineName = busLineName;
    }

    public Integer getBusLineDirection() {
        return mBusLineDirection;
    }

    public void setBusLineDirection(Integer busLineDirection) {
        this.mBusLineDirection = busLineDirection;
    }

    public String getBusLineColor() {
        return mBusLineColor;
    }

    public void setBusLineColor(String busLineColor) {
        this.mBusLineColor = busLineColor;
    }

    public Integer getStartBusStopNumber() {
        return mStartBusStopNumber;
    }

    public void setStartBusStopNumber(Integer startBusStopNumber) {
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

    public Integer getStartBusStopStreetNumber() {
        return mStartBusStopStreetNumber;
    }

    public void setStartBusStopStreetNumber(Integer startBusStopStreetNumber) {
        this.mStartBusStopStreetNumber = startBusStopStreetNumber;
    }

    public Double getStartBusStopDistanceToOrigin() {
        return mStartBusStopDistanceToOrigin;
    }

    public void setStartBusStopDistanceToOrigin(Double startBusStopDistanceToOrigin) {
        this.mStartBusStopDistanceToOrigin = startBusStopDistanceToOrigin;
    }

    public Integer getDestinationBusStopNumber() {
        return mDestinationBusStopNumber;
    }

    public void setDestinationBusStopNumber(Integer destinationBusStopNumber) {
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

    public Integer getDestinationBusStopStreetNumber() {
        return mDestinationBusStopStreetNumber;
    }

    public void setDestinationBusStopStreetNumber(Integer destinationBusStopStreetNumber) {
        this.mDestinationBusStopStreetNumber = destinationBusStopStreetNumber;
    }

    public Double getDestinationBusStopDistanceToDestination() {
        return mDestinationBusStopDistanceToDestination;
    }

    public void setDestinationBusStopDistanceToDestination(Double destinationBusStopDistanceToDestination) {
        this.mDestinationBusStopDistanceToDestination = destinationBusStopDistanceToDestination;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("BusLineName: ")
                .append(getBusLineName())
                .append(", StartBusStopStreetName: ")
                .append(getStartBusStopStreetName())
                .append(", DestinationBusStopStreetName: ")
                .append(getDestinationBusStopStreetName());
        return result.toString();
    }
}
