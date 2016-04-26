package com.mybus.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.helper.WalkDistanceHelper;
import com.mybus.model.BusRouteResult;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class BusRouteFragment extends Fragment {

    private BusRouteResult mBusRouteResult;

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
        View v = inflater.inflate(R.layout.bus_route_fragment, container, false);
        TextView stopOrigin = (TextView) v.findViewById(R.id.stop_origin_name);
        TextView walkOrigin = (TextView) v.findViewById(R.id.route_walk_origin);
        TextView stopDestination = (TextView) v.findViewById(R.id.stop_destination_name);
        TextView walkDestination = (TextView) v.findViewById(R.id.route_walk_destination);

        stopOrigin.setText(mBusRouteResult.getBusRoutes().get(0).getStartBusStopStreetName() + " " + mBusRouteResult.getBusRoutes().get(0).getStartBusStopStreetNumber());
        walkOrigin.setText("Distancia desde el origen: " + WalkDistanceHelper.getDistanceInBlocks(mBusRouteResult.getBusRoutes().get(0).getStartBusStopDistanceToOrigin()));

        if (mBusRouteResult.getType() == 0) {
            stopDestination.setText(mBusRouteResult.getBusRoutes().get(0).getDestinationBusStopStreetName() + " " + mBusRouteResult.getBusRoutes().get(0).getDestinationBusStopNumber());
            walkDestination.setText("Distancia hasta el destino: " + WalkDistanceHelper.getDistanceInBlocks(mBusRouteResult.getBusRoutes().get(0).getDestinationBusStopDistanceToDestination()));
        } else {
            stopDestination.setText(mBusRouteResult.getBusRoutes().get(1).getDestinationBusStopStreetName() + " " + mBusRouteResult.getBusRoutes().get(1).getDestinationBusStopNumber());
            walkDestination.setText("Distancia hasta el destino: " + WalkDistanceHelper.getDistanceInBlocks(mBusRouteResult.getBusRoutes().get(1).getDestinationBusStopDistanceToDestination()));
        }
        return v;
    }

    public void setBusRouteResult(BusRouteResult routeResult) {
        this.mBusRouteResult = routeResult;
    }
}
