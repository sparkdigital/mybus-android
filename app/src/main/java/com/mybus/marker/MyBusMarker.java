package com.mybus.marker;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class MyBusMarker {
    private Marker mMarker;
    private MarkerOptions mMarkerOptions;
    private boolean mIsFavorite = false;
    private String mFavName;
    private Integer mType;
    public static final int ORIGIN = 1;
    public static final int DESTINATION = 2;
    public static final int USER_LOCATION = 2;

    public MyBusMarker(MarkerOptions mOptions, boolean isFav, String favName, int type) {
        this.mMarkerOptions = mOptions;
        this.mIsFavorite = isFav;
        this.mFavName = favName;
        this.mType = type;
    }

    public void setMarker(Marker marker) {
        this.mMarker = marker;
    }

    public Marker getMarker() {
        return this.mMarker;
    }

    public MarkerOptions getMarkerOptions() {
        return this.mMarkerOptions;
    }

    public void setAsFavorite(boolean isFav) {
        this.mIsFavorite = isFav;
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
