package com.mybus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.helper.WalkDistanceHelper;
import com.mybus.model.BusRoute;
import com.mybus.model.BusRouteResult;
import com.mybus.model.road.MapBusRoad;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class BusRouteFragment extends Fragment {

    @BindView(R.id.origin_bus_stop_address)
    public TextView mFirstLineOriginBusStopAddress;
    @BindView(R.id.start_distance)
    public TextView mFirstLineDistanceFromOrigin;
    @BindView(R.id.first_destination_bus_stop_title)
    public TextView mFirstDestinationTitle;
    @BindView(R.id.destination_bus_stop_address)
    public TextView mFirstLineDestinationBusStopAddress;
    @BindView(R.id.stop_distance)
    public TextView mFirstLineDistanceToDestination;
    @BindView(R.id.secondLineInfo)
    public LinearLayout mSecondLineInfo;
    @BindView(R.id.secondLineStartAddress)
    public TextView mSecondLineOriginBusStopAddress;
    @BindView(R.id.secondLineStartDistance)
    public TextView mCombinationDistance;
    @BindView(R.id.secondLineStopAddress)
    public TextView mSecondLineDestinationBusStopAddress;
    @BindView(R.id.secondLineStopDistance)
    public TextView mSecondLineDistanceToDestination;

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
        if (mBusRouteResult != null) {
            BusRoute firstBus = mBusRouteResult.getBusRoutes().get(0);
            //1° Stop Bus:
            mFirstLineOriginBusStopAddress.setText(firstBus.getFullOriginStopBusAddress());
            mFirstLineDistanceFromOrigin.setText(getContext().getString(R.string.bus_route_distance_to_origin, WalkDistanceHelper.getDistanceInBlocks(firstBus.getStartBusStopDistanceToOrigin())));

            //2° Stop Bus:
            mFirstLineDestinationBusStopAddress.setText(firstBus.getFullDestinationStopBusAddress());

            if (!mBusRouteResult.isCombined()) { //if single route
                mSecondLineInfo.setVisibility(View.GONE); // Hide second line info
                mFirstLineDistanceToDestination.setText(getContext().getString(R.string.bus_route_distance_to_destination, WalkDistanceHelper.getDistanceInBlocks(firstBus.getDestinationBusStopDistanceToDestination())));
            } else { //if combined
                mFirstDestinationTitle.setText(getContext().getString(R.string.down_on)); // Change title for first destination stop bus

                BusRoute secondBus = mBusRouteResult.getBusRoutes().get(1);
                mSecondLineOriginBusStopAddress.setText(secondBus.getFullOriginStopBusAddress());
                mCombinationDistance.setText(getContext().getString(R.string.bus_route_distance_from_combination, WalkDistanceHelper.getDistanceInBlocks(mBusRouteResult.getCombinationDistance())));

                mSecondLineDestinationBusStopAddress.setText(secondBus.getFullDestinationStopBusAddress());
                mSecondLineDistanceToDestination.setText(getContext().getString(R.string.bus_route_distance_to_destination, WalkDistanceHelper.getDistanceInBlocks(secondBus.getDestinationBusStopDistanceToDestination())));

                mSecondLineInfo.setVisibility(View.VISIBLE); // Show second line info.
            }
        }
    }

    public BusRouteResult getBusRouteResult() {
        return this.mBusRouteResult;
    }

    public void setBusRouteResult(BusRouteResult routeResult) {
        this.mBusRouteResult = routeResult;
    }

    public MapBusRoad getMapBusRoad() {
        return this.mMapBusRoad;
    }

    public void setMapBusRoad(MapBusRoad mapBusRoad) {
        this.mMapBusRoad = mapBusRoad;
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
