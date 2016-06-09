package com.mybus.model;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * Interface used to extract common behavior for some RealmObjects (Favorites & History)
 */
public interface UsageTrackable {
    void incrementUsageCount();
}
