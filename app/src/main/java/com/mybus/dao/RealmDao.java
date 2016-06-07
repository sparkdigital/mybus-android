package com.mybus.dao;

import com.mybus.model.FavoriteLocation;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public interface RealmDao< T extends RealmObject> {
    void saveOrUpdate(T item);

    void updateItemUsageCount(String id);

    RealmResults<T> getAll();

    T getById(String id);
}
