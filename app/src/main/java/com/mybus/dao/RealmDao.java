package com.mybus.dao;

import android.content.Context;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.mybus.model.FavoriteLocation;
import com.mybus.model.UsageTrackable;

import java.util.Date;
import java.util.List;

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
     *
     * @param item
     * @return The item updated or null if an error occurred
     */
    public boolean saveOrUpdate(T item) {
        if (item == null) {
            return false;
        }
        mRealm.beginTransaction();
        T updated = mRealm.copyToRealmOrUpdate(item);
        mRealm.commitTransaction();
        return updated != null;
    }

    /**
     * Remove an item from realm
     *
     * @param id To find the item
     * @return
     */
    public boolean remove(Long id) {
        T item = mRealm.where(mType).equalTo("id", id).findFirst();
        if (item != null) {
            try {
                mRealm.beginTransaction();
                item.deleteFromRealm();
                mRealm.commitTransaction();
                return true;
            } catch (IllegalStateException e) { //IllegalStateException if the corresponding Realm is closed or in an incorrect thread.
                return false;
            }
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
     *
     * @param id
     */
    public void updateItemUsageCount(Long id) {
        if (!UsageTrackable.class.isAssignableFrom(mType)) {
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
     * If given object is not null, copy it from realm.
     *
     * @param item
     * @return
     */
    private T copyFromRealm(T item) {
        if (item == null) {
            return null;
        }
        return mRealm.copyFromRealm(item);
    }

    /**
     * @param id
     * @return a copy of a realObject item finding by id
     */
    public T getById(Long id) {
        return getByField("id", id);
    }

    /********************** Get First by Field *****************************************/

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Boolean value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Byte value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Double value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Float value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Integer value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Long value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Short value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, String value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a copy of a realObject item finding by a field
     */
    public T getByField(String field, Date value) {
        return copyFromRealm(mRealm.where(mType).equalTo(field, value).findFirst());
    }

    /********************** Get All by Field *****************************************/

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Boolean value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Byte value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Double value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Float value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Integer value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Long value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Short value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, String value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }

    /**
     * @param field name of the field
     * @param value of the field
     * @return a RealmResults<T> of items finding by a field
     */
    public RealmResults<T> getAllByField(String field, Date value) {
        return mRealm.where(mType).equalTo(field, value).findAll();
    }
}