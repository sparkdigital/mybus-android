package com.mybus.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.mybus.model.BusLine;
import com.mybus.service.BusLineServiceImpl;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusLinesRequestTask extends AsyncTask<Void, Void, List<BusLine>> {

    private BusLinesRequestCallback mBusLinesRequestCallback;
    private Context mContext;

    public BusLinesRequestTask(BusLinesRequestCallback callback, Context context) {
        this.mBusLinesRequestCallback = callback;
        mContext = context;
    }

    @Override
    protected List<BusLine> doInBackground(Void... voids) {
        return new BusLineServiceImpl().getBusLines(mContext);
    }

    @Override
    protected void onPostExecute(List<BusLine> busLines) {
        mBusLinesRequestCallback.onBusLinesFound(busLines);
    }
}
