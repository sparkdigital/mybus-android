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

    public MyBusMarker(MarkerOptions mOptions, boolean isFav, String favName) {
        this.mMarkerOptions = mOptions;
        this.mIsFavorite = isFav;
        this.mFavName = favName;
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

    public void setFavoriteName(String favName){
        this.mFavName = favName;
    }

    public String getFavoriteName() {
        return this.mFavName;
    }
}
