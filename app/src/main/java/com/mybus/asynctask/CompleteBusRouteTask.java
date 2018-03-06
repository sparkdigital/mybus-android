package com.mybus.asynctask;

import android.os.AsyncTask;

import com.mybus.model.CompleteBusRoute;
import com.mybus.service.CompleteBusRouteServiceImpl;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class CompleteBusRouteTask extends AsyncTask<Void, Void, CompleteBusRoute> {

    private final String mBusLineName;
    private CompleteBusRouteCallback mCompleteBusRouteCallback;
    private Integer mBusLineId;

    public CompleteBusRouteTask(int busLineId, String busLineName, CompleteBusRouteCallback callback) {
        this.mCompleteBusRouteCallback = callback;
        this.mBusLineId = busLineId;
        this.mBusLineName = busLineName;
    }

    @Override
    protected CompleteBusRoute doInBackground(Void... voids) {
        return new CompleteBusRouteServiceImpl().getCompleteRoute(mBusLineId, mBusLineName);
    }

    @Override
    protected void onPostExecute(CompleteBusRoute completeBusRoute) {
        mCompleteBusRouteCallback.onCompleteRouteFound(mBusLineId, completeBusRoute);
    }
}
