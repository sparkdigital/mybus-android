package com.mybus.helper;

import android.content.Context;

import com.mybus.model.FavoriteLocation;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 *
 * Helper class to save and list favorites
 */
public class RealmHelper {
    private static RealmHelper instance = null;
    private Realm mRealm;

    private RealmHelper(Context context) {
        RealmConfiguration realConfig = new RealmConfiguration.Builder(context).build();
        this.mRealm = Realm.getInstance(realConfig);
    }

    public static RealmHelper getInstance(Context context){
        if (instance == null) {
            instance = new RealmHelper(context);
        }
        return instance;
    }

    public void saveFavorite(FavoriteLocation favLocation) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(favLocation);
        mRealm.commitTransaction();
    }

    public RealmResults<FavoriteLocation> getAllFavorites () {
        return mRealm.where(FavoriteLocation.class).findAll();
    }
}
