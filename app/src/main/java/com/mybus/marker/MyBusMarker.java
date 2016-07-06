package com.mybus.marker;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * This class is used to manage Google Maps markers and its relationship with a favorite
 */
public class MyBusMarker {
    private Marker mMapMarker;
    private MarkerOptions mMarkerOptions;
    private boolean mIsFavorite = false;
    private String mFavName;
    //MyBusMarker type
    private Integer mType;
    public static final int ORIGIN = 1;
    public static final int DESTINATION = 2;
    public static final int USER_LOCATION = 3;
    public static final int FAVORITE = 4;
    public static final int CHARGING_POINT = 5;

    public MyBusMarker(MarkerOptions mOptions, boolean isFav, String favName, int type) {
        this.mMarkerOptions = mOptions;
        this.mIsFavorite = isFav;
        this.mFavName = favName;
        this.mType = type;
    }

    public void setMapMarker(Marker marker) {
        this.mMapMarker = marker;
    }

    public Marker getMapMarker() {
        return this.mMapMarker;
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

    public void setFavoriteName(String favName) {
        this.mFavName = favName;
    }

    public String getFavoriteName() {
        return this.mFavName;
    }

    public Integer getType() {
        return this.mType;
    }
}
