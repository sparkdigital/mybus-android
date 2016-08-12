package com.mybus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.helper.WalkDistanceHelper;
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_line_result, parent, false);
        return new BusResultViewHolder(v, this);
    }

    /**
     * @param dataSet
     * @param busLineListItemListener
     */
    public BusResultViewAdapter(List<BusRouteResult> dataSet, BusLineListItemListener busLineListItemListener, Context context) {
        mDataset = dataSet;
        mBusLineListItemListener = busLineListItemListener;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(BusResultViewHolder holder, int pos) {
        BusRouteResult routeResult = mDataset.get(pos);
        List<BusRoute> routes = routeResult.getBusRoutes();
        if (routes != null && !routes.isEmpty()) {
            if (!routeResult.isCombined()) {
                //if single route
                BusRoute busRoute = routes.get(0);
                holder.mLineNumber.setText(busRoute.getBusLineName());
                holder.mStartAddress.setText(busRoute.getStartBusStopStreetName() + " " + busRoute.getStartBusStopStreetNumber());
                holder.mStopAddress.setText(busRoute.getDestinationBusStopStreetName() + " " + busRoute.getDestinationBusStopStreetNumber());
                holder.mStartDistance.setText(mContext.getString(R.string.bus_route_distance_to_origin, WalkDistanceHelper.getDistanceInBlocks(busRoute.getStartBusStopDistanceToOrigin())));
                holder.mStopDistance.setText(mContext.getString(R.string.bus_route_distance_to_destination, WalkDistanceHelper.getDistanceInBlocks(busRoute.getDestinationBusStopDistanceToDestination())));
            } else {
                //if combined
                BusRoute firstBus = routes.get(0);
                BusRoute lastBus = routes.get(1);
                holder.mLineNumber.setText(firstBus.getBusLineName() + " -> " + lastBus.getBusLineName());
                holder.mStartAddress.setText(firstBus.getStartBusStopStreetName() + " " + firstBus.getStartBusStopStreetNumber());
                holder.mStopAddress.setText(lastBus.getDestinationBusStopStreetName() + " " + lastBus.getDestinationBusStopStreetNumber());
                holder.mStartDistance.setText(mContext.getString(R.string.bus_route_distance_to_origin, WalkDistanceHelper.getDistanceInBlocks(firstBus.getStartBusStopDistanceToOrigin())));
                holder.mStopDistance.setText(mContext.getString(R.string.bus_route_distance_to_destination, WalkDistanceHelper.getDistanceInBlocks(lastBus.getDestinationBusStopDistanceToDestination())));
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

    public void setDataset(List<BusRouteResult> dataset){
        mDataset = dataset;
    }
}
