package com.mybus.dao;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.FavoriteLocation;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p>
 * DAO for favorites objects saved on realm
 */
public final class FavoriteLocationDao extends RealmDao<FavoriteLocation> {

    private static FavoriteLocationDao instance = null;

    private FavoriteLocationDao(Context context) {
        super(context, FavoriteLocation.class);
    }

    /**
     * Get DAO instance. Needs the context to access to Realm DB.
     *
     * @param context
     * @return
     */
    public static FavoriteLocationDao getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteLocationDao(context);
        }
        return instance;
    }

    /**
     * Gets the first item by a LatLng location
     *
     * @param latLng
     */
    public FavoriteLocation getItemByLatLng(LatLng latLng) {
        return copyFromRealm(
                mRealm.where(FavoriteLocation.class).equalTo("latitude", latLng.latitude)
                        .equalTo("longitude", latLng.longitude)
                        .findFirst()
        );
    }
}
