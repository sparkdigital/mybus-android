package com.mybus.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static List<Fare> parseResults(JSONArray results) {
        if (results == null) {
            return null;
        }
        List<Fare> list = new ArrayList<>();
        JSONObject fareJsonObject = null;
        for (int i = 0; i < results.length(); i++) {
            try {
                fareJsonObject = results.getJSONObject(i);
            } catch (JSONException e) {
                Log.e("Fare", e.getMessage());
            }
            Fare fare = parseSingleFare(fareJsonObject);
            if (fare != null) {
                list.add(fare);
            }
        }
        return list;
    }

    private static Fare parseSingleFare(JSONObject fareJsonObject) {
        if (fareJsonObject == null) {
            return null;
        }
        String title = fareJsonObject.optString("Title");
        String cost = fareJsonObject.optString("Cost");
        return new Fare(title, cost);
    }

    @Override
    public String toString() {
        return "Title: " + mTitle + ", Cost: " + mCost;
    }
}
