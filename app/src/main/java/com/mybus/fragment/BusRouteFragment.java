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
import com.mybus.model.BusRoute;
import com.mybus.model.BusRouteResult;
import com.mybus.model.road.MapBusRoad;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class BusRouteFragment extends Fragment {

    @Bind(R.id.origin_bus_stop_address)
    public TextView mOriginBusStopAddress;
    @Bind(R.id.destination_bus_stop_address)
    public TextView mDestinationBusStopAddress;
    @Bind(R.id.start_distance)
    public TextView mStartDistance;
    @Bind(R.id.stop_distance)
    public TextView mStopDistance;

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
        View view = inflater.inflate(R.layout.bus_line_result_bottomsheet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BusRoute firstBus = mBusRouteResult.getBusRoutes().get(0);
        mOriginBusStopAddress.setText(String.format("%s %s", firstBus.getStartBusStopStreetName(), firstBus.getStartBusStopStreetNumber()));
        mStartDistance.setText(getContext().getString(R.string.bus_route_distance_to_origin, WalkDistanceHelper.getDistanceInBlocks(firstBus.getStartBusStopDistanceToOrigin())));
        if (!mBusRouteResult.isCombined()) {
            //if single route
            mDestinationBusStopAddress.setText(String.format("%s %s", firstBus.getDestinationBusStopStreetName(), firstBus.getDestinationBusStopStreetNumber()));
            mStopDistance.setText(getContext().getString(R.string.bus_route_distance_to_destination, WalkDistanceHelper.getDistanceInBlocks(firstBus.getDestinationBusStopDistanceToDestination())));
        } else {
            //if combined
            BusRoute lastBus = mBusRouteResult.getBusRoutes().get(1);
            mDestinationBusStopAddress.setText(String.format("%s %s", lastBus.getDestinationBusStopStreetName(), lastBus.getDestinationBusStopStreetNumber()));
            mStopDistance.setText(getContext().getString(R.string.bus_route_distance_to_destination, WalkDistanceHelper.getDistanceInBlocks(lastBus.getDestinationBusStopDistanceToDestination())));
        }
    }

    public void setBusRouteResult(BusRouteResult routeResult) {
        this.mBusRouteResult = routeResult;
    }

    public BusRouteResult getBusRouteResult() {
        return this.mBusRouteResult;
    }

    public void setMapBusRoad(MapBusRoad mapBusRoad) {
        this.mMapBusRoad = mapBusRoad;
    }

    public MapBusRoad getMapBusRoad() {
        return this.mMapBusRoad;
    }

    public void showMapBusRoad(boolean show) {
        if (mMapBusRoad != null) {
            mMapBusRoad.showBusRoadFromMap(show);
        }
    }

    public void clearBusRoadFromMap() {
        if (mMapBusRoad != null) {
            mMapBusRoad.clearBusRoadFromMap();
        }
    }
}
