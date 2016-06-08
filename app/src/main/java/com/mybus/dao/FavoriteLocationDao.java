package com.mybus.dao;

import android.content.Context;

import com.mybus.model.FavoriteLocation;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p>
 * DAO for favorites objects
 */
public class FavoriteLocationDao extends RealmDao<FavoriteLocation> {
    private static RealmDao<FavoriteLocation> instance = null;
    private Realm mRealm;

    private FavoriteLocationDao(Context context) {
        super(context, FavoriteLocation.class);
    }

    /**
     * Get DAO instance. Needs the context to access to Realm DB.
     *
     * @param context
     * @return
     */
    public static RealmDao<FavoriteLocation> getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteLocationDao(context);
        }
        return instance;
    }

    /**
     * Save a FavoriteLocation on Realm or update if it already exists
     *
     * @param favLocation
     */
    @Override
    public void updateItem(FavoriteLocation current, FavoriteLocation favLocation) {
        current.setName(favLocation.getName());
        current.setLatitude(favLocation.getLatitude());
        current.setLongitude(favLocation.getLongitude());
        current.setStreetName(favLocation.getStreetName());
        current.setStreetNumber(favLocation.getStreetNumber());
    }
}
