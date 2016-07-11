package com.mybus.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusLine {
    private int mId;
    private String mName;
    private String mColor;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = "#" + color;
    }

    public static List<BusLine> parseResults(JSONArray results) {
        if (results == null) {
            return null;
        }
        List<BusLine> list = new ArrayList<>();
        JSONObject busLineJsonObject = null;
        for (int i = 0; i < results.length(); i++) {
            try {
                busLineJsonObject = results.getJSONObject(i);
            } catch (JSONException e) {
                Log.e("Error parsing a BusLine", e.getMessage());
            }
            BusLine busLine = parseSingleBusLine(busLineJsonObject);
            if (busLine != null) {
                list.add(busLine);
            }
        }
        return list;
    }

    private static BusLine parseSingleBusLine(JSONObject busLineJsonObject) {
        if (busLineJsonObject == null) {
            return null;
        }
        BusLine busLine = new BusLine();
        busLine.setId(busLineJsonObject.optInt("IdBusLine"));
        busLine.setName(busLineJsonObject.optString("BusLineName"));
        busLine.setColor(busLineJsonObject.optString("Color"));
        return busLine;
    }

    @Override
    public String toString() {
        return "Id: " + mId + ", Name: " + mName + ", Color: " + mColor;
    }
}
