package com.mybus.dao;

import android.content.Context;

import com.mybus.model.RecentLocation;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * DAO for recent objects saved on realm
 */
public class RecentLocationDao extends RealmDao<RecentLocation> {
    private static RealmDao<RecentLocation> instance = null;

    private RecentLocationDao(Context context) {
        super(context, RecentLocation.class);
    }

    /**
     * Get DAO instance. Needs the context to access to Realm DB.
     *
     * @param context
     * @return
     */
    public static RealmDao<RecentLocation> getInstance(Context context) {
        if (instance == null) {
            instance = new RecentLocationDao(context);
        }
        return instance;
    }
}
