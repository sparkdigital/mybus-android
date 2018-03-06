package com.mybus.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.mybus.service.MyBusServiceImpl;

/**
 * Created by Lucas De Lio on 6/23/2016.
 */
public class FaresRequestTask extends AsyncTask<Void, Void, String> {

    private FaresRequestCallback mFaresRequestCallback;
    //private Context mContext;

    public FaresRequestTask(Context contet, FaresRequestCallback callback) {
        //this.mContext = contet;
        this.mFaresRequestCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return new MyBusServiceImpl().getBusFares();
    }

    @Override
    protected void onPostExecute(String fares) {
        mFaresRequestCallback.onFaresFound(fares);
    }
/*
    public String getFaresFromAssets() {
        JSONObject jsonMockedResponse = FileLoaderHelper.loadJSONObjectFromAssets(mContext, "fares_mock.json");
        JSONArray faresFromJson = null;
        try {
            faresFromJson = jsonMockedResponse.getJSONArray("Results");
        } catch (JSONException e) {
            Log.e("FaresRequestTask", e.getMessage());
        }
        //return Fare.parseResults(faresFromJson);
        return faresFromJson.toString();
    }*/
}
