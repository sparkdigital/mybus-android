package com.mybus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ldimitroff on 13/06/16.
 */
public class GeoLocation implements Parcelable {

    public static final Parcelable.Creator<GeoLocation> CREATOR = new Parcelable.Creator<GeoLocation>() {
        @Override
        public GeoLocation createFromParcel(Parcel in) {
            return new GeoLocation(in);
        }

        @Override
        public GeoLocation[] newArray(int size) {
            return new GeoLocation[size];
        }
    };
    private LatLng mLatLng;
    private String mAddress;

    public GeoLocation(String address, LatLng latLng) {
        this.mLatLng = latLng;
        this.mAddress = address;
    }

    protected GeoLocation(Parcel in) {
        mLatLng = (LatLng) in.readValue(LatLng.class.getClassLoader());
        mAddress = in.readString();
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        this.mLatLng = latLng;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mLatLng);
        dest.writeString(mAddress);
    }
}
