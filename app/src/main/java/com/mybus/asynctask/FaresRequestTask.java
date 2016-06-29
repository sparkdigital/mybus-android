package com.mybus.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.mybus.helper.FileLoaderHelper;
import com.mybus.model.Fare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Lucas De Lio on 6/23/2016.
 */
public class FaresRequestTask extends AsyncTask<Void, Void, List<Fare>> {

    private FaresRequestCallback mFaresRequestCallback;
    private Context mContext;

    public FaresRequestTask(Context contet, FaresRequestCallback callback) {
        this.mContext = contet;
        this.mFaresRequestCallback = callback;
    }

    @Override
    protected List<Fare> doInBackground(Void... voids) {
        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromAssets(mContext, "fares_mock.json");
        JSONArray faresFromJson = null;
        try {
            faresFromJson = jsonMockedResponse.getJSONArray("Fares");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Fare> busRoutesReceived = Fare.parseResults(faresFromJson);
        return busRoutesReceived;
    }

    @Override
    protected void onPostExecute(List<Fare> fares) {
        mFaresRequestCallback.onFaresFound(fares);
    }
}
