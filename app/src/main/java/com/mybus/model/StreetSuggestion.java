package com.mybus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by ldimitroff on 01/06/16.
 */
public class StreetSuggestion implements SearchSuggestion, Comparable<StreetSuggestion> {

    public static final int TYPE_STREET = 0;
    public static final int TYPE_FAVORITE = 1;
    public static final int TYPE_TOURISTIC_PLACE = 2;
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StreetSuggestion> CREATOR = new Parcelable.Creator<StreetSuggestion>() {
        @Override
        public StreetSuggestion createFromParcel(Parcel in) {
            return new StreetSuggestion(in);
        }

        @Override
        public StreetSuggestion[] newArray(int size) {
            return new StreetSuggestion[size];
        }
    };
    private static final int HASH_MULTIPLIER = 31;
    private Long mFavID;
    private String mStreetName;
    private int mType;
    private TouristicPlace mTouristicPlace = null;

    protected StreetSuggestion(Parcel in) {
        mFavID = in.readByte() == 0x00 ? null : in.readLong();
        mStreetName = in.readString();
        mType = in.readInt();
        mTouristicPlace = (TouristicPlace) in.readValue(TouristicPlace.class.getClassLoader());
    }

    public StreetSuggestion(String street) {
        this.mStreetName = street;
        this.mType = TYPE_STREET;
    }

    public StreetSuggestion(FavoriteLocation favoriteLocation) {
        this.mStreetName = favoriteLocation.getName();
        this.mType = TYPE_FAVORITE;
        this.mFavID = favoriteLocation.getId();
    }

    public StreetSuggestion(TouristicPlace place) {
        this.mStreetName = place.getName();
        this.mTouristicPlace = place;
        this.mType = TYPE_TOURISTIC_PLACE;
    }

    public Long getFavID() {
        return this.mFavID;
    }

    public int getType() {
        return mType;
    }

    @Override
    public String getBody() {
        return mStreetName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mFavID == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mFavID);
        }
        dest.writeString(mStreetName);
        dest.writeInt(mType);
        dest.writeValue(mTouristicPlace);
    }

    @Override
    public int compareTo(StreetSuggestion another) {
        return this.getBody().compareTo(another.getBody());
    }

    public TouristicPlace getTouristicPlace() {
        return mTouristicPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StreetSuggestion that = (StreetSuggestion) o;

        if (mType != that.mType) {
            return false;
        }
        return mStreetName.equals(that.mStreetName);

    }

    @Override
    public int hashCode() {
        int result = mStreetName.hashCode();
        result = HASH_MULTIPLIER * result + mType;
        return result;
    }
}
