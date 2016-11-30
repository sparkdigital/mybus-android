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
public class FavoriteLocation extends RealmObject implements UsageTrackable, Comparable<FavoriteLocation> {
    public static final int HASH_MULTIPLIER = 31;
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
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    // Constructor used for testing
    public FavoriteLocation(String name, String address, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

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
    public void updateUsage() {
        this.usageCount++;
    }

    // Used for testing.
    @Override
    public String toString() {
        return "Name: " + name + " ; Address: " + address + " ; LatLong: (" + latitude + ", " + longitude + ")";
    }

    @Override
    public int compareTo(FavoriteLocation another) {
        return this.getUsageCount() < another.getUsageCount() ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FavoriteLocation that = (FavoriteLocation) o;

        if (!id.equals(that.id)) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        if (!address.equals(that.address)) {
            return false;
        }
        if (!latitude.equals(that.latitude)) {
            return false;
        }
        if (!longitude.equals(that.longitude)) {
            return false;
        }
        return usageCount.equals(that.usageCount);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = HASH_MULTIPLIER * result + (name != null ? name.hashCode() : 0);
        result = HASH_MULTIPLIER * result + (address != null ? address.hashCode() : 0);
        result = HASH_MULTIPLIER * result + latitude.hashCode();
        result = HASH_MULTIPLIER * result + longitude.hashCode();
        result = HASH_MULTIPLIER * result + usageCount.hashCode();
        return result;
    }
}
