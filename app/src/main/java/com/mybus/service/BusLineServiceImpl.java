package com.mybus.service;

import android.content.Context;
import android.util.Log;

import com.mybus.helper.FileLoaderHelper;
import com.mybus.model.BusLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BusLineServiceImpl extends GenericService implements BusLineService {

    private static final String TAG = "BusLineServiceImpl";

    @Override
    public List<BusLine> getBusLines(Context context) {
        JSONArray results = null;
        JSONObject linesJSONObject = FileLoaderHelper.loadJSONObjectFromAssets(context, "bus_lines.json");
        if (linesJSONObject != null) {
            try {
                results = linesJSONObject.getJSONArray("Results");
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return BusLine.parseResults(results);
    }
}
