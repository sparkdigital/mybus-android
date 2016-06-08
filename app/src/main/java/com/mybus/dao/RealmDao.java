package com.mybus.dao;

import android.content.Context;

import com.mybus.model.FavoriteLocation;
import com.mybus.model.UsageTrackable;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public abstract class RealmDao< T extends RealmObject> {
    private Realm mRealm;
    private Class<T> mType;

    protected RealmDao(Context c, Class<T> clazz) {
        RealmConfiguration realConfig = new RealmConfiguration.Builder(c)
                .deleteRealmIfMigrationNeeded()
                .build();
        this.mRealm = Realm.getInstance(realConfig);
        this.mType = clazz;
    }

    public void save(T item) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(item);
        mRealm.commitTransaction();
    };

    public void update(T item, Long id) {
        T current = getById(id);
        mRealm.beginTransaction();
        updateItem(current, item);
        mRealm.commitTransaction();
    }

    protected abstract void updateItem(T current, T item);

    public void remove(Long id) {
        T item = getById(id);
        item.deleteFromRealm();
    }

    public RealmResults<T> getAll() {
        return mRealm.where(mType).findAll();
    }

    /**
     * Increment the usage count for a given item.
     * @param id
     */
    public void updateItemUsageCount(Long id) {
        if (!mType.isAssignableFrom(UsageTrackable.class)) {
            return;
        }
        // This query is fast because "name" is an indexed field
        UsageTrackable item = (UsageTrackable) getById(id);
        if (item != null) {
            mRealm.beginTransaction();
            item.incrementUsageCount();
            mRealm.commitTransaction();
        }
    }

    public T getById(Long id) {
        return getByField("id", id);
    };

    public T getByField(String field, Boolean value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, Byte value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, Double value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, Float value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, Integer value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, Long value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, Short value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, String value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }

    public T getByField(String field, Date value) {
        return mRealm.where(mType).equalTo(field, value).findFirst();
    }
}
