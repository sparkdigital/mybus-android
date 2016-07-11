package com.mybus.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mybus.helper.FileLoaderHelper;
import com.mybus.model.TouristicPlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ldimitroff on 11/07/16.
 */
public class TouristicPlacesRequestTask extends AsyncTask<Void, Void, List<TouristicPlace>> {

    private static final String TAG = TouristicPlacesRequestTask.class.getSimpleName();
    private TouristicPlacesRequestCallback mCallback;
    private Context mContext;

    public TouristicPlacesRequestTask(Context contet, TouristicPlacesRequestCallback callback) {
        this.mContext = contet;
        this.mCallback = callback;
    }

    @Override
    protected List<TouristicPlace> doInBackground(Void... voids) {
        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromAssets(mContext, "tour_places_mock.json");
        JSONArray jsonArray = null;
        try {
            if (jsonMockedResponse != null) {
                jsonArray = jsonMockedResponse.getJSONArray("Places");
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            jsonArray = null;
        }
        return TouristicPlace.parseJSONArray(jsonArray);
    }

    @Override
    protected void onPostExecute(List<TouristicPlace> touristicPlaces) {
        mCallback.onPlacesFound(touristicPlaces);
    }
}
