package com.mybus.model;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public enum RecentType {
    ORIGIN, DESTINATION;
    private final int value;

    RecentType() {
        value = ordinal();
    }

    public static RecentType fromValue(int value) throws IllegalArgumentException {
        try {
            return RecentType.values()[value];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown RecentType for value : " + value);
        }
    }

    public int getValue() {
        return value;
    }
}
