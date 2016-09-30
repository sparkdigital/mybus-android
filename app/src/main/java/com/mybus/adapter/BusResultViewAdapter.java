package com.mybus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.listener.BusLineListItemListener;
import com.mybus.model.BusRoute;
import com.mybus.model.BusRouteResult;
import com.mybus.view.BusResultViewHolder;

import java.util.List;

/**
 * Created by Lucas De Lio on 19/07/2016.
 */
public class BusResultViewAdapter extends RecyclerView.Adapter<BusResultViewHolder> implements BusLineListItemListener {

    private List<BusRouteResult> mDataset;
    private BusLineListItemListener mBusLineListItemListener;
    private Context mContext;

    @Override
    public BusResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_line_result, parent, false);
        return new BusResultViewHolder(v, this);
    }

    /**
     * @param dataSet
     * @param busLineListItemListener
     */
    public BusResultViewAdapter(List<BusRouteResult> dataSet, BusLineListItemListener busLineListItemListener, Context context) {
        this.mDataset = dataSet;
        this.mBusLineListItemListener = busLineListItemListener;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(BusResultViewHolder holder, int pos) {
        BusRouteResult routeResult = mDataset.get(pos);
        List<BusRoute> routes = routeResult.getBusRoutes();
        if (routes != null && !routes.isEmpty()) {
            BusRoute firstBusRoute = routes.get(0);
            holder.mStartAddress.setText(firstBusRoute.getFullOriginStopBusAddress());
            holder.mStopAddress.setText(firstBusRoute.getFullDestinationStopBusAddress());
            if (!routeResult.isCombined()) { //Single route
                // Single Bus Line Name title:
                holder.mLineNumber.setText(firstBusRoute.getBusLineName());
                // Make sure that the second line is hidden
                holder.mSecondLineView.setVisibility(View.GONE);
            } else { // Combined Route
                // Get the second route
                BusRoute secondBusRoute = routes.get(1);
                //Combined bus line name title:
                holder.mLineNumber.setText(String.format("%s -> %s", firstBusRoute.getBusLineName(), secondBusRoute.getBusLineName()));
                // Populate second line information:
                holder.mFirstDestinationTitle.setText(mContext.getString(R.string.down_on));
                holder.mSecondStartAddress.setText(secondBusRoute.getFullOriginStopBusAddress());
                holder.mSecondStopAddress.setText(secondBusRoute.getFullDestinationStopBusAddress());
                // Show second line information
                holder.mSecondLineView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onItemClicked(int position) {
        mBusLineListItemListener.onItemClicked(position);
    }

    public void setDataset(List<BusRouteResult> dataset) {
        mDataset = dataset;
    }
}
