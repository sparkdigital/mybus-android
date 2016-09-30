package com.mybus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusRoute implements Parcelable {
    public static final int HASH_MULTIPLIER = 31;
    private Integer mIdBusLine;
    private String mBusLineName;
    private Integer mBusLineDirection;
    private String mBusLineColor;
    private Integer mStartBusStopNumber;
    private String mStartBusStopLat;
    private String mStartBusStopLng;
    private String mStartBusStopStreetName;
    private Integer mStartBusStopStreetNumber;
    private Double mStartBusStopDistanceToOrigin;
    private Integer mDestinationBusStopNumber;
    private String mDestinationBusStopLat;
    private String mDestinationBusStopLng;
    private String mDestinationBusStopStreetName;
    private Integer mDestinationBusStopStreetNumber;
    private Double mDestinationBusStopDistanceToDestination;

    public BusRoute() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public BusRoute(Integer mIdBusLine, String mBusLineName, Integer mBusLineDirection, String mBusLineColor,
                    Integer mStartBusStopNumber, String mStartBusStopLat, String mStartBusStopLng,
                    String mStartBusStopStreetName, Integer mStartBusStopStreetNumber,
                    Double mStartBusStopDistanceToOrigin, Integer mDestinationBusStopNumber,
                    String mDestinationBusStopLat, String mDestinationBusStopLng,
                    String mDestinationBusStopStreetName, Integer mDestinationBusStopStreetNumber,
                    Double mDestinationBusStopDistanceToDestination) {
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

    protected BusRoute(Parcel in) {
        mBusLineName = in.readString();
        mBusLineColor = in.readString();
        mStartBusStopLat = in.readString();
        mStartBusStopLng = in.readString();
        mStartBusStopStreetName = in.readString();
        mDestinationBusStopLat = in.readString();
        mDestinationBusStopLng = in.readString();
        mDestinationBusStopStreetName = in.readString();
        mDestinationBusStopStreetNumber = in.readInt();
        mStartBusStopStreetNumber = in.readInt();
        mIdBusLine = in.readInt();
        mBusLineDirection = in.readInt();
        mStartBusStopNumber = in.readInt();
        mDestinationBusStopNumber = in.readInt();
        mDestinationBusStopDistanceToDestination = (Double) in.readValue(Double.class.getClassLoader());
        mStartBusStopDistanceToOrigin = (Double) in.readValue(Double.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBusLineName);
        dest.writeString(mBusLineColor);
        dest.writeString(mStartBusStopLat);
        dest.writeString(mStartBusStopLng);
        dest.writeString(mStartBusStopStreetName);
        dest.writeString(mDestinationBusStopLat);
        dest.writeString(mDestinationBusStopLng);
        dest.writeString(mDestinationBusStopStreetName);
        dest.writeInt(mDestinationBusStopStreetNumber);
        dest.writeInt(mStartBusStopStreetNumber);
        dest.writeInt(mIdBusLine);
        dest.writeInt(mBusLineDirection);
        dest.writeInt(mStartBusStopNumber);
        dest.writeInt(mDestinationBusStopNumber);
        dest.writeValue(mDestinationBusStopDistanceToDestination);
        dest.writeValue(mStartBusStopDistanceToOrigin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusRoute> CREATOR = new Creator<BusRoute>() {
        @Override
        public BusRoute createFromParcel(Parcel in) {
            return new BusRoute(in);
        }

        @Override
        public BusRoute[] newArray(int size) {
            return new BusRoute[size];
        }
    };

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
        StringBuilder result = new StringBuilder(150);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusRoute busRoute = (BusRoute) o;

        if (!mIdBusLine.equals(busRoute.mIdBusLine)) {
            return false;
        }
        if (!mBusLineName.equals(busRoute.mBusLineName)) {
            return false;
        }
        if (!mBusLineDirection.equals(busRoute.mBusLineDirection)) {
            return false;
        }
        if (!mBusLineColor.equals(busRoute.mBusLineColor)) {
            return false;
        }
        if (!mStartBusStopNumber.equals(busRoute.mStartBusStopNumber)) {
            return false;
        }
        if (!mStartBusStopLat.equals(busRoute.mStartBusStopLat)) {
            return false;
        }
        if (!mStartBusStopLng.equals(busRoute.mStartBusStopLng)) {
            return false;
        }
        if (!mStartBusStopStreetName.equals(busRoute.mStartBusStopStreetName)) {
            return false;
        }
        if (!mStartBusStopStreetNumber.equals(busRoute.mStartBusStopStreetNumber)) {
            return false;
        }
        if (mStartBusStopDistanceToOrigin != null
                ? !mStartBusStopDistanceToOrigin.equals(busRoute.mStartBusStopDistanceToOrigin)
                : busRoute.mStartBusStopDistanceToOrigin != null) {
            return false;
        }
        if (!mDestinationBusStopNumber.equals(busRoute.mDestinationBusStopNumber)) {
            return false;
        }
        if (!mDestinationBusStopLat.equals(busRoute.mDestinationBusStopLat)) {
            return false;
        }
        if (!mDestinationBusStopLng.equals(busRoute.mDestinationBusStopLng)) {
            return false;
        }
        if (!mDestinationBusStopStreetName.equals(busRoute.mDestinationBusStopStreetName)) {
            return false;
        }
        if (!mDestinationBusStopStreetNumber.equals(busRoute.mDestinationBusStopStreetNumber)) {
            return false;
        }
        return mDestinationBusStopDistanceToDestination != null
                ? mDestinationBusStopDistanceToDestination.equals(busRoute.mDestinationBusStopDistanceToDestination)
                : busRoute.mDestinationBusStopDistanceToDestination == null;

    }

    @Override
    public int hashCode() {
        int result = mIdBusLine.hashCode();
        result = HASH_MULTIPLIER * result + mBusLineName.hashCode();
        result = HASH_MULTIPLIER * result + mBusLineDirection.hashCode();
        result = HASH_MULTIPLIER * result + mBusLineColor.hashCode();
        result = HASH_MULTIPLIER * result + mStartBusStopNumber.hashCode();
        result = HASH_MULTIPLIER * result + mStartBusStopLat.hashCode();
        result = HASH_MULTIPLIER * result + mStartBusStopLng.hashCode();
        result = HASH_MULTIPLIER * result + mStartBusStopStreetName.hashCode();
        result = HASH_MULTIPLIER * result + mStartBusStopStreetNumber.hashCode();
        result = HASH_MULTIPLIER * result + (mStartBusStopDistanceToOrigin != null
                ? mStartBusStopDistanceToOrigin.hashCode() : 0);
        result = HASH_MULTIPLIER * result + mDestinationBusStopNumber.hashCode();
        result = HASH_MULTIPLIER * result + mDestinationBusStopLat.hashCode();
        result = HASH_MULTIPLIER * result + mDestinationBusStopLng.hashCode();
        result = HASH_MULTIPLIER * result + mDestinationBusStopStreetName.hashCode();
        result = HASH_MULTIPLIER * result + mDestinationBusStopStreetNumber.hashCode();
        result = HASH_MULTIPLIER * result + (mDestinationBusStopDistanceToDestination != null
                ? mDestinationBusStopDistanceToDestination.hashCode() : 0);
        return result;
    }

    public LatLng getStartBusStopLatLng() {
        return new LatLng(Double.valueOf(mStartBusStopLat), Double.valueOf(mStartBusStopLng));
    }

    public LatLng getEndBusStopLatLng() {
        return new LatLng(Double.valueOf(mDestinationBusStopLat), Double.valueOf(mDestinationBusStopLng));
    }

    public String getFullOriginStopBusAddress() {
        return mStartBusStopStreetName + " " + mStartBusStopStreetNumber;
    }

    public String getFullDestinationStopBusAddress() {
        return mDestinationBusStopStreetName + " " + mDestinationBusStopStreetNumber;
    }
}
