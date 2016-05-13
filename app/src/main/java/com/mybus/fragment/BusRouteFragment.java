package com.mybus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.helper.WalkDistanceHelper;
import com.mybus.model.BusRouteResult;
import com.mybus.model.Road.MapBusRoad;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class BusRouteFragment extends Fragment {

    @Bind(R.id.stop_origin_name)
    TextView mStopOrigin;
    @Bind(R.id.route_walk_origin)
    TextView mWalkOrigin;
    @Bind(R.id.stop_destination_name)
    TextView mStopDestination;
    @Bind(R.id.route_walk_destination)
    TextView mWalkDestination;

    private BusRouteResult mBusRouteResult;
    private MapBusRoad mMapBusRoad;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bus_route_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStopOrigin.setText(mBusRouteResult.getBusRoutes().get(0).getStartBusStopStreetName() + " " + mBusRouteResult.getBusRoutes().get(0).getStartBusStopStreetNumber());
        mWalkOrigin.setText(getContext().getString(R.string.distance_to_origin) + WalkDistanceHelper.getDistanceInBlocks(mBusRouteResult.getBusRoutes().get(0).getStartBusStopDistanceToOrigin()));

        if (mBusRouteResult.getType() == 0) {
            mStopDestination.setText(mBusRouteResult.getBusRoutes().get(0).getDestinationBusStopStreetName() + " " + mBusRouteResult.getBusRoutes().get(0).getDestinationBusStopNumber());
            mWalkDestination.setText(getContext().getString(R.string.distance_to_destination) + WalkDistanceHelper.getDistanceInBlocks(mBusRouteResult.getBusRoutes().get(0).getDestinationBusStopDistanceToDestination()));
        } else {
            mStopDestination.setText(mBusRouteResult.getBusRoutes().get(1).getDestinationBusStopStreetName() + " " + mBusRouteResult.getBusRoutes().get(1).getDestinationBusStopNumber());
            mWalkDestination.setText(getContext().getString(R.string.distance_to_destination) + WalkDistanceHelper.getDistanceInBlocks(mBusRouteResult.getBusRoutes().get(1).getDestinationBusStopDistanceToDestination()));
        }
    }

    public void setBusRouteResult(BusRouteResult routeResult) {
        this.mBusRouteResult = routeResult;
    }

    public BusRouteResult getBusRouteResult(){
        return this.mBusRouteResult;
    }

    public void setMapBusRoad(MapBusRoad mapBusRoad) {
        this.mMapBusRoad = mapBusRoad;
    }

    public MapBusRoad getMapBusRoad(){
        return this.mMapBusRoad;
    }

    public void showMapBusRoad(boolean show) {
        if (mMapBusRoad != null){
            mMapBusRoad.showBusRoadFromMap(show);
        }
    }
}
