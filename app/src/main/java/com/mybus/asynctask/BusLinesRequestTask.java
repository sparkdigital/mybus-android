package com.mybus.asynctask;

import android.os.AsyncTask;

import com.mybus.model.BusLine;
import com.mybus.service.BusLineServiceImpl;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusLinesRequestTask extends AsyncTask<Void, Void, List<BusLine>> {

    private BusLinesRequestCallback mBusLinesRequestCallback;

    public BusLinesRequestTask(BusLinesRequestCallback callback) {
        this.mBusLinesRequestCallback = callback;
    }

    @Override
    protected List<BusLine> doInBackground(Void... voids) {
        return new BusLineServiceImpl().getBusLines();
    }

    @Override
    protected void onPostExecute(List<BusLine> busLines) {
        mBusLinesRequestCallback.onBusLinesFound(busLines);
    }
}
