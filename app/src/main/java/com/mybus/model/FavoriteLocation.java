package com.mybus.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 *
 * RealmObject to persist favorite locations
 */
public class FavoriteLocation extends RealmObject {
    @Required
    private String name;
    @Required
    private String streetName;
    @Required
    private Integer streetNumber;
    @Required
    private Double latitude;
    @Required
    private Double longitude;

    //Default constructor
    public FavoriteLocation(){
    }

    public FavoriteLocation(String name, String stName, Integer stNumber, Double latitude, Double longitude){
        this.name = name;
        this.streetName = stName;
        this.streetNumber = stNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
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

    // Used for testing.
    @Override
    public String toString() {
        return "Name: " + name + " ; Address: " + streetName + " " + streetNumber + " ; LatLong: (" + latitude + ", " + longitude + ")";
    }
}
