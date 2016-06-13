package com.mybus.model;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * RealmObject to persist favorite locations
 */
public class FavoriteLocation extends RealmObject implements UsageTrackable {
    @PrimaryKey
    private Long id = System.nanoTime();
    @Required
    private String name;
    @Required
    private String address;
    @Required
    private Double latitude;
    @Required
    private Double longitude;
    @Required
    private Integer usageCount = 0;

    // Default constructor
    public FavoriteLocation() {
    }

    // Constructor used for testing
    public FavoriteLocation(String name, String address, Integer stNumber, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setUsageCount(Integer usesCount) { this.usageCount = usesCount; }

    public Integer getUsageCount() { return usageCount; }

    public LatLng getLatLng () {
        return new LatLng(this.latitude, this.longitude);
    }

    @Override
    public void incrementUsageCount() { this.usageCount++; }

    // Used for testing.
    @Override
    public String toString() {
        return "Name: " + name + " ; Address: " + address + " ; LatLong: (" + latitude + ", " + longitude + ")";
    }
}
