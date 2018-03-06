package com.mybus.marker;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * This class is used to manage Google Maps markers and its relationship with a favorite
 */
public class MyBusMarker implements Parcelable {
    public static final int ORIGIN = 1;
    public static final int DESTINATION = 2;
    public static final int USER_LOCATION = 3;
    public static final int FAVORITE = 4;
    public static final int CHARGING_POINT = 5;
    public static final Parcelable.Creator<MyBusMarker> CREATOR = new Parcelable.Creator<MyBusMarker>() {
        @Override
        public MyBusMarker createFromParcel(Parcel in) {
            return new MyBusMarker(in);
        }

        @Override
        public MyBusMarker[] newArray(int size) {
            return new MyBusMarker[size];
        }
    };
    private Marker mMapMarker;
    private MarkerOptions mMarkerOptions;
    private boolean mIsFavorite = false;
    private String mFavName;
    //MyBusMarker type
    private Integer mType;

    public MyBusMarker(MarkerOptions mOptions, boolean isFav, String favName, int type) {
        this.mMarkerOptions = mOptions;
        this.mIsFavorite = isFav;
        this.mFavName = favName;
        this.mType = type;
    }

    protected MyBusMarker(Parcel in) {
        mMarkerOptions = (MarkerOptions) in.readValue(MarkerOptions.class.getClassLoader());
        mIsFavorite = (boolean) in.readValue(Boolean.class.getClassLoader());
        mFavName = in.readString();
        mType = in.readInt();
    }

    public Marker getMapMarker() {
        return this.mMapMarker;
    }

    public void setMapMarker(Marker marker) {
        this.mMapMarker = marker;
    }

    public MarkerOptions getMarkerOptions() {
        return this.mMarkerOptions;
    }

    public void setAsFavorite(boolean isFav) {
        this.mIsFavorite = isFav;
        if (!isFav) {
            this.mFavName = null;
        }
    }

    public boolean isFavorite() {
        return this.mIsFavorite;
    }

    public String getFavoriteName() {
        return this.mFavName;
    }

    public void setFavoriteName(String favName) {
        this.mFavName = favName;
    }

    public Integer getType() {
        return this.mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(mMarkerOptions);
        parcel.writeValue(mIsFavorite);
        parcel.writeString(mFavName);
        parcel.writeInt(mType);
    }
}
