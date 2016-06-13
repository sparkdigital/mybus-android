package com.mybus.dao;

import android.content.Context;

import com.mybus.model.FavoriteLocation;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p>
 * DAO for favorites objects saved on realm
 */
public class FavoriteLocationDao extends RealmDao<FavoriteLocation> {
    private static RealmDao<FavoriteLocation> instance = null;

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
}
