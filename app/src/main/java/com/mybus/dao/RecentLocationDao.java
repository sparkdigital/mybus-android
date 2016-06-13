package com.mybus.dao;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.model.RecentLocation;
import com.mybus.model.UsageTrackable;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * DAO for recent objects saved on realm
 */
public class RecentLocationDao extends RealmDao<RecentLocation> {
    private static RecentLocationDao instance = null;

    private RecentLocationDao(Context context) {
        super(context, RecentLocation.class);
    }

    /**
     * Get DAO instance. Needs the context to access to Realm DB.
     *
     * @param context
     * @return
     */
    public static RecentLocationDao getInstance(Context context) {
        if (instance == null) {
            instance = new RecentLocationDao(context);
        }
        return instance;
    }

    /**
     * Gets the first item by a LatLng location
     *
     * @param type
     * @param latLng
     */
    public RecentLocation getItemByLatLng(int type, LatLng latLng) {
        return copyFromRealm(
                mRealm.where(RecentLocation.class).equalTo("type", type)
                        .equalTo("latitude", latLng.latitude)
                        .equalTo("longitude", latLng.longitude)
                        .findFirst()
        );
    }
}
