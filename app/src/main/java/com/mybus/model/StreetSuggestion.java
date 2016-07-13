package com.mybus.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by ldimitroff on 01/06/16.
 */
public class StreetSuggestion implements SearchSuggestion, Comparable<StreetSuggestion> {

    private Long mFavID;
    private boolean mIsFavorite;
    private String mStreetName;

    public StreetSuggestion(Parcel in) {
        this.mStreetName = in.readString();
    }

    public StreetSuggestion(String street) {
        this.mStreetName = street;
        this.mIsFavorite = false;
    }

    public StreetSuggestion(FavoriteLocation favoriteLocation) {
        this.mStreetName = favoriteLocation.getName();
        this.mIsFavorite = true;
        this.mFavID = favoriteLocation.getId();
    }

    public Long getFavID() {
        return this.mFavID;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    @Override
    public String getBody() {
        return mStreetName;
    }

    public static final Creator<StreetSuggestion> CREATOR = new Creator<StreetSuggestion>() {
        @Override
        public StreetSuggestion createFromParcel(Parcel in) {
            return new StreetSuggestion(in);
        }

        @Override
        public StreetSuggestion[] newArray(int size) {
            return new StreetSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStreetName);
    }

    @Override
    public int compareTo(StreetSuggestion another) {
        return this.getBody().compareTo(another.getBody());
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

        if (mIsFavorite != that.mIsFavorite) {
            return false;
        }
        if (mFavID != null ? !mFavID.equals(that.mFavID) : that.mFavID != null) {
            return false;
        }
        return mStreetName.equals(that.mStreetName);

    }

    @Override
    public int hashCode() {
        int result = mFavID != null ? mFavID.hashCode() : 0;
        result = 31 * result + (mIsFavorite ? 1 : 0);
        result = 31 * result + mStreetName.hashCode();
        return result;
    }
}
