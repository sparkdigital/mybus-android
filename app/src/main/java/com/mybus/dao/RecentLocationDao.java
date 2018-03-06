package com.mybus.dao;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.helper.ShortcutHelper;
import com.mybus.model.RecentLocation;

import java.util.List;

import io.realm.Sort;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * DAO for recent objects saved on realm
 */
public final class RecentLocationDao extends RealmDao<RecentLocation> {
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
     * Finds a recent for History Searches and increases it's usage, or creates a new one if no one found
     *
     * @param query
     * @param latLng
     */
    public static void findOrCreateNewRecent(String query, LatLng latLng, int type, Context context) {
        RecentLocation location = RecentLocationDao.getInstance(context).getItemByLatLng(latLng);
        if (location != null) {
            RecentLocationDao.getInstance(context).updateUsage(location.getId());
        } else {
            RecentLocation recentLocation = new RecentLocation(type, query, latLng.latitude, latLng.longitude);
            RecentLocationDao.getInstance(context).saveOrUpdate(recentLocation);
        }

        ShortcutHelper.updateRecentLocationShortcuts(context);
    }

    /**
     * Gets the first item by a LatLng location
     *
     * @param latLng
     */
    public RecentLocation getItemByLatLng(LatLng latLng) {
        return copyFromRealm(
                mRealm.where(RecentLocation.class).equalTo("latitude", latLng.latitude)
                        .equalTo("longitude", latLng.longitude)
                        .findFirst()
        );
    }

    /**
     * Finds all the RecentLocations by Type : @{@link com.mybus.model.SearchType}
     *
     * @param type
     * @return
     */
    public List<RecentLocation> findAllByType(int type) {
        return copyFromRealm(
                mRealm.where(RecentLocation.class)
                        .equalTo("type", type)
                        .sort("lastUsage", Sort.DESCENDING)
                        .findAll());
    }
}
