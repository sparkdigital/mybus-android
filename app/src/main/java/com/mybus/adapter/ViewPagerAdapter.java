package com.mybus.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.fragment.BusRouteFragment;
import com.mybus.model.BusRouteResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<BusRouteFragment> mFragmentList = new ArrayList<>();
    private final LayoutInflater mInflater;
    private final FragmentManager mFragmentManager;

    private TextView mBusLineTitle;
    private TextView mBusLineDestination;
    private TextView mBusLineOrigin;

    public ViewPagerAdapter(FragmentManager manager, LayoutInflater layoutInflater) {
        super(manager);
        this.mInflater = layoutInflater;
        this.mFragmentManager = manager;
    }

    @Override
    public BusRouteFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(BusRouteFragment fragment) {
        mFragmentList.add(fragment);
    }

    /**
     * @param tabLayout
     * @param busRouteResult
     * @return
     */
    public View getTabView(TabLayout tabLayout, BusRouteResult busRouteResult) {
        View view;
        if (!busRouteResult.isCombined()) {
            view = mInflater.inflate(R.layout.simple_bus_tab_layout, tabLayout, false);
            mBusLineTitle = (TextView) view.findViewById(R.id.bus_line_text);
            mBusLineTitle.setText(busRouteResult.getBusRoutes().get(0).getBusLineName());
        } else {
            view = mInflater.inflate(R.layout.combined_bus_tab_layout, tabLayout, false);
            String firstBus = busRouteResult.getBusRoutes().get(0).getBusLineName();
            String secondBus = busRouteResult.getBusRoutes().get(1).getBusLineName();
            mBusLineOrigin = (TextView) view.findViewById(R.id.bus_line_origin);
            mBusLineDestination = (TextView) view.findViewById(R.id.bus_line_destination);
            mBusLineOrigin.setText(firstBus);
            mBusLineDestination.setText(secondBus);
        }


        return view;
    }

    public void clearBusRoutes() {
        if (mFragmentList != null) {
            for (BusRouteFragment busRoute : mFragmentList) {
                busRoute.clearBusRoadFromMap();
            }
        }
    }

    public void cleanAll() {
        for (BusRouteFragment busRoute : mFragmentList) {
            mFragmentManager.beginTransaction().remove(busRoute).commit();
        }
        mFragmentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
