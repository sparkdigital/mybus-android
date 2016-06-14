package com.mybus.model;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * <p/>
 * RealmObject to persist recent locations
 */
public class RecentLocation extends RealmObject implements UsageTrackable, Comparable<RecentLocation> {
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
    private Integer usageCount = 0;

    // Default constructor
    public RecentLocation() {
    }

    // Constructor used for testing
    public RecentLocation(int type, String address, Double latitude, Double longitude) {
        this.type = type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public void setUsageCount(Integer usesCount) {
        this.usageCount = usesCount;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public LatLng getLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }

    @Override
    public void incrementUsageCount() {
        this.usageCount++;
    }

    // Used for testing.
    @Override
    public String toString() {
        return "Type: " + (type == 0 ? "ORIGIN" : "DESTINATION") + " ; Address: " + address + " ; LatLong: (" + latitude + ", " + longitude + ")" + "Usage Count: " + usageCount;
    }

    @Override
    public int compareTo(RecentLocation another) {
        return this.getUsageCount() < another.getUsageCount() ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecentLocation that = (RecentLocation) o;

        if (!id.equals(that.id)) return false;
        if (!type.equals(that.type)) return false;
        if (!address.equals(that.address)) return false;
        if (!latitude.equals(that.latitude)) return false;
        if (!longitude.equals(that.longitude)) return false;
        return usageCount.equals(that.usageCount);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + usageCount.hashCode();
        return result;
    }
}
