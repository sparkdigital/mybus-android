package com.mybus.asynctask;

import android.os.AsyncTask;

import com.mybus.model.CompleteBusRoute;
import com.mybus.service.CompleteBusRouteServiceImpl;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class CompleteBusRouteTask extends AsyncTask<Void, Void, CompleteBusRoute> {

    private CompleteBusRouteCallback mCompleteBusRouteCallback;
    private Integer mBusLineId;

    public CompleteBusRouteTask(int busLineId, CompleteBusRouteCallback callback) {
        this.mCompleteBusRouteCallback = callback;
        this.mBusLineId = busLineId;
    }

    @Override
    protected CompleteBusRoute doInBackground(Void... voids) {
        return new CompleteBusRouteServiceImpl().getCompleteRoute(mBusLineId);
    }

    @Override
    protected void onPostExecute(CompleteBusRoute completeBusRoute) {
        mCompleteBusRouteCallback.onCompleteRouteFound(completeBusRoute);
    }
}
