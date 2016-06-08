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
public abstract class RealmDao<T extends RealmObject> {
    private Realm mRealm;
    private Class<T> mType;

    protected RealmDao(Context c, Class<T> clazz) {
        RealmConfiguration realConfig = new RealmConfiguration.Builder(c)
                .deleteRealmIfMigrationNeeded()
                .build();
        this.mRealm = Realm.getInstance(realConfig);
        this.mType = clazz;
    }

    /**
     * Save or update the given item depends if it already exists or not.
     * @param item
     * @return The item updated or null if an error occurred
     */
    public T saveOrUpdate(T item) {
        if (item == null) {
            return null;
        }
        try {
            mRealm.beginTransaction();
            T updated = mRealm.copyToRealmOrUpdate(item);
            mRealm.commitTransaction();
            return updated;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Remove an item from realm
     * @param id To find the item
     * @return
     */
    public boolean remove(Long id) {
        T item = mRealm.where(mType).equalTo("id", id).findFirst();
        if (item != null) {
            mRealm.beginTransaction();
            item.deleteFromRealm();
            mRealm.commitTransaction();
            return true;
        }
        return false;
    }

    /**
     * @return all items on realm
     */
    public RealmResults<T> getAll() {
        return mRealm.where(mType).findAll();
    }

    /**
     * Increment the usage count for a given item.
     * @param id
     */
    public void updateItemUsageCount(Long id) {
        if (!(UsageTrackable.class).isAssignableFrom(mType)) {
            return;
        }
        UsageTrackable item = (UsageTrackable) mRealm.where(mType).equalTo("id", id).findFirst();
        if (item != null) {
            mRealm.beginTransaction();
            item.incrementUsageCount();
            mRealm.commitTransaction();
        }
    }

    /**
     * @param id
     * @return a copy of a realObject item finding by id
     */
    public T getById(Long id) {
        return getByField("id", id);
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Boolean value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Byte value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Double value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Float value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Integer value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Long value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Short value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, String value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Date value) {
        return mRealm.copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }
}