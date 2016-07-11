package com.mybus.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldimitroff on 11/07/16.
 */
public class TouristicPlace implements Parcelable {

    private static final String TAG = TouristicPlace.class.getSimpleName();
    private String mName;
    private String mDescription;
    private String mPhotoUrl;
    private Double mLatitude;
    private Double mLongitude;

    protected TouristicPlace(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mPhotoUrl = in.readString();
        mLatitude = in.readByte() == 0x00 ? null : in.readDouble();
        mLongitude = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mPhotoUrl);
        if (mLatitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(mLatitude);
        }
        if (mLongitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(mLongitude);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TouristicPlace> CREATOR = new Parcelable.Creator<TouristicPlace>() {
        @Override
        public TouristicPlace createFromParcel(Parcel in) {
            return new TouristicPlace(in);
        }

        @Override
        public TouristicPlace[] newArray(int size) {
            return new TouristicPlace[size];
        }
    };

    public TouristicPlace() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public static List<TouristicPlace> parseJSONArray(JSONArray results) {
        if (results == null) {
            return null;
        }
        List<TouristicPlace> list = new ArrayList<>();
        JSONObject jsonObject = null;
        for (int i = 0; i < results.length(); i++) {
            try {
                jsonObject = results.getJSONObject(i);
            } catch (JSONException e) {
                Log.e("Fare", e.getMessage());
            }
            TouristicPlace touristicPlace = parseSinglePlace(jsonObject);
            if (touristicPlace != null) {
                list.add(touristicPlace);
            }
        }
        return list;
    }

    private static TouristicPlace parseSinglePlace(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        TouristicPlace place = new TouristicPlace();
        try {
            place.setName(jsonObject.getString("name"));
            place.setDescription(jsonObject.optString("description"));
            place.setLatitude(jsonObject.getDouble("lat"));
            place.setLongitude(jsonObject.getDouble("lon"));
            place.setPhotoUrl(jsonObject.getString("photoUrl"));
        } catch (JSONException e) {
            place = null;
            Log.e(TAG, e.toString());
        }
        return place;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public LatLng getLatLng() {
        return new LatLng(mLatitude, mLongitude);
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }
}
