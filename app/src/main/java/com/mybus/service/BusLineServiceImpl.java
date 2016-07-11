package com.mybus.service;

import android.util.Log;

import com.mybus.builder.MyBusServiceUrlBuilder;
import com.mybus.model.BusLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class BusLineServiceImpl extends GenericService implements BusLineService{

    private static final String TAG = BusLineService.class.getSimpleName();

    @Override
    public List<BusLine> getBusLines() {
        try {
            //Create request to get a list of bus lines.
            String url = MyBusServiceUrlBuilder.buildBusLinesUrl();
            JSONObject jsonObject = new JSONObject(executeUrl(url));
            JSONArray results = jsonObject.getJSONArray("Results"); //Gets result
            return BusLine.parseResults(results); //Parse results from JSON
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
