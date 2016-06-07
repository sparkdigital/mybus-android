package com.mybus.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by ldimitroff on 01/06/16.
 */
public class StreetSuggestion implements SearchSuggestion {

    private String mStreetName;

    public StreetSuggestion(Parcel in) {
        this.mStreetName = in.readString();
    }

    public StreetSuggestion(String street) {
        this.mStreetName = street;
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
}
