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

    public BusRoute(){};

    public BusRoute(Integer mIdBusLine, String mBusLineName, Integer mBusLineDirection, String mBusLineColor, Integer mStartBusStopNumber, String mStartBusStopLat, String mStartBusStopLng, String mStartBusStopStreetName, Integer mStartBusStopStreetNumber, Double mStartBusStopDistanceToOrigin, Integer mDestinationBusStopNumber, String mDestinationBusStopLat, String mDestinationBusStopLng, String mDestinationBusStopStreetName, Integer mDestinationBusStopStreetNumber, Double mDestinationBusStopDistanceToDestination) {
        this.mIdBusLine = mIdBusLine;
        this.mBusLineName = mBusLineName;
        this.mBusLineDirection = mBusLineDirection;
        this.mBusLineColor = mBusLineColor;
        this.mStartBusStopNumber = mStartBusStopNumber;
        this.mStartBusStopLat = mStartBusStopLat;
        this.mStartBusStopLng = mStartBusStopLng;
        this.mStartBusStopStreetName = mStartBusStopStreetName;
        this.mStartBusStopStreetNumber = mStartBusStopStreetNumber;
        this.mStartBusStopDistanceToOrigin = mStartBusStopDistanceToOrigin;
        this.mDestinationBusStopNumber = mDestinationBusStopNumber;
        this.mDestinationBusStopLat = mDestinationBusStopLat;
        this.mDestinationBusStopLng = mDestinationBusStopLng;
        this.mDestinationBusStopStreetName = mDestinationBusStopStreetName;
        this.mDestinationBusStopStreetNumber = mDestinationBusStopStreetNumber;
        this.mDestinationBusStopDistanceToDestination = mDestinationBusStopDistanceToDestination;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusRoute busRoute = (BusRoute) o;

        if (!mIdBusLine.equals(busRoute.mIdBusLine)) return false;
        if (!mBusLineName.equals(busRoute.mBusLineName)) return false;
        if (!mBusLineDirection.equals(busRoute.mBusLineDirection)) return false;
        if (!mBusLineColor.equals(busRoute.mBusLineColor)) return false;
        if (!mStartBusStopNumber.equals(busRoute.mStartBusStopNumber)) return false;
        if (!mStartBusStopLat.equals(busRoute.mStartBusStopLat)) return false;
        if (!mStartBusStopLng.equals(busRoute.mStartBusStopLng)) return false;
        if (!mStartBusStopStreetName.equals(busRoute.mStartBusStopStreetName)) return false;
        if (!mStartBusStopStreetNumber.equals(busRoute.mStartBusStopStreetNumber)) return false;
        if (!mStartBusStopDistanceToOrigin.equals(busRoute.mStartBusStopDistanceToOrigin))
            return false;
        if (!mDestinationBusStopNumber.equals(busRoute.mDestinationBusStopNumber)) return false;
        if (!mDestinationBusStopLat.equals(busRoute.mDestinationBusStopLat)) return false;
        if (!mDestinationBusStopLng.equals(busRoute.mDestinationBusStopLng)) return false;
        if (!mDestinationBusStopStreetName.equals(busRoute.mDestinationBusStopStreetName))
            return false;
        if (!mDestinationBusStopStreetNumber.equals(busRoute.mDestinationBusStopStreetNumber))
            return false;
        return mDestinationBusStopDistanceToDestination.equals(busRoute.mDestinationBusStopDistanceToDestination);

    }

    @Override
    public int hashCode() {
        int result = mIdBusLine.hashCode();
        result = 31 * result + mBusLineName.hashCode();
        result = 31 * result + mBusLineDirection.hashCode();
        result = 31 * result + mBusLineColor.hashCode();
        result = 31 * result + mStartBusStopNumber.hashCode();
        result = 31 * result + mStartBusStopLat.hashCode();
        result = 31 * result + mStartBusStopLng.hashCode();
        result = 31 * result + mStartBusStopStreetName.hashCode();
        result = 31 * result + mStartBusStopStreetNumber.hashCode();
        result = 31 * result + mStartBusStopDistanceToOrigin.hashCode();
        result = 31 * result + mDestinationBusStopNumber.hashCode();
        result = 31 * result + mDestinationBusStopLat.hashCode();
        result = 31 * result + mDestinationBusStopLng.hashCode();
        result = 31 * result + mDestinationBusStopStreetName.hashCode();
        result = 31 * result + mDestinationBusStopStreetNumber.hashCode();
        result = 31 * result + mDestinationBusStopDistanceToDestination.hashCode();
        return result;
    }
}
