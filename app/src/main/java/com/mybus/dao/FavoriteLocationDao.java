package com.mybus.dao;

import android.content.Context;

import com.mybus.model.FavoriteLocation;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * DAO for favorites objects
 */
public class FavoriteLocationDao implements RealmDao<FavoriteLocation> {
    private static RealmDao<FavoriteLocation> instance = null;
    private Realm mRealm;

    private FavoriteLocationDao(Context context) {
        RealmConfiguration realConfig = new RealmConfiguration.Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        this.mRealm = Realm.getInstance(realConfig);
    }

    /**
     * Get DAO instance. Needs the context to access to Realm DB.
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
     * @param favLocation
     */
    @Override
    public void saveOrUpdate(FavoriteLocation favLocation) {
        FavoriteLocation current = getById(favLocation.getName());
        mRealm.beginTransaction();
        if (current == null) {
            favLocation.setUsageCount(0);
            mRealm.copyToRealm(favLocation);
        } else {
            current.setName(favLocation.getName());
            current.setLatitude(favLocation.getLatitude());
            current.setLongitude(favLocation.getLongitude());
            current.setStreetName(favLocation.getStreetName());
            current.setStreetNumber(favLocation.getStreetNumber());
            mRealm.copyToRealm(current);
        }
        mRealm.commitTransaction();
    }

    /**
     * Increment the usage count for a given item.
     * @param name
     */
    @Override
    public void updateItemUsageCount(String name) {
        // This query is fast because "name" is an indexed field
        FavoriteLocation favLocation = getById(name);
        mRealm.beginTransaction();
        if (favLocation != null) {
            favLocation.setUsageCount(favLocation.getUsageCount() + 1);
        }
        mRealm.commitTransaction();
    }

    /**
     * @return all the favorites
     */
    @Override
    public RealmResults<FavoriteLocation> getAll() {
        return mRealm.where(FavoriteLocation.class).findAll();
    }

    /**
     * @param name String
     * @return a FavoriteLocation or null if it not exits
     */
    @Override
    public FavoriteLocation getById(String name) {
        return mRealm.where(FavoriteLocation.class).equalTo("name", name).findFirst();
    }
}
