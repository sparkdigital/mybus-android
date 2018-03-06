package com.mybus.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas De Lio on 6/23/2016.
 */
public class Fare {

    private String mTitle;
    private String mCost;

    public Fare(String title, String cost) {
        this.mTitle = title;
        this.mCost = cost;
    }

    public static List<Fare> parseResults(JSONArray results) {
        if (results == null) {
            return null;
        }
        List<Fare> list = new ArrayList<>();
        Fare fare = null;
        for (int i = 0; i < results.length(); i++) {
            try {
                JSONArray jsonArray = results.getJSONArray(i);
                fare = new Fare(jsonArray.getString(0), jsonArray.getString(1));
            } catch (JSONException e) {
                Log.e("Fare", e.getMessage());
            }
            if (fare != null) {
                list.add(fare);
            }
        }
        return list;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        this.mCost = cost;
    }

    @Override
    public String toString() {
        return "Title: " + mTitle + ", Cost: " + mCost;
    }
}
