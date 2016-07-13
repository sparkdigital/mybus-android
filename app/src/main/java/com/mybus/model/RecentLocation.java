package com.mybus.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * RealmObject to persist recent locations
 */
public class RecentLocation extends RealmObject implements UsageTrackable, Comparable<RecentLocation> {
    public static final int HASH_MULTIPLIER = 31;
    @PrimaryKey
    private Long id = System.nanoTime();
    @Required
    private Integer type;
    @Required
    private String address;
    @Required
    private Double latitude;
    @Required
    private Double longitude;
    @Required
    private Long lastUsage;

    // Default constructor
    public RecentLocation() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    // Constructor used for testing
    public RecentLocation(int type, String address, Double latitude, Double longitude) {
        this.type = type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUsage = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
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

    public void setUsageTime(Long usageTime) {
        this.lastUsage = usageTime;
    }

    public Long getUsageTime() {
        return lastUsage;
    }

    public LatLng getLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }

    @Override
    public void updateUsage() {
        this.lastUsage = System.currentTimeMillis();
    }

    // Used for testing.
    @Override
    public String toString() {
        return "Type: " + (type == 0 ? "ORIGIN" : "DESTINATION")
                + " ; Address: " + address
                + " ; LatLong: (" + latitude + ", " + longitude + ")"
                + " ; LastUsage: " + new Date(lastUsage).toString();
    }

    @Override
    public int compareTo(RecentLocation another) {
        return this.getUsageTime() < another.getUsageTime() ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecentLocation that = (RecentLocation) o;

        return id.equals(that.id) && type.equals(that.type) && address.equals(that.address)
                && latitude.equals(that.latitude) && longitude.equals(that.longitude)
                && lastUsage.equals(that.lastUsage);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = HASH_MULTIPLIER * result + type.hashCode();
        result = HASH_MULTIPLIER * result + address.hashCode();
        result = HASH_MULTIPLIER * result + latitude.hashCode();
        result = HASH_MULTIPLIER * result + longitude.hashCode();
        result = HASH_MULTIPLIER * result + lastUsage.hashCode();
        return result;
    }
}
