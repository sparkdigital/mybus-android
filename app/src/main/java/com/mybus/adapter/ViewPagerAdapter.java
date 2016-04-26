package com.mybus.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.model.BusRouteResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final LayoutInflater mInflater;

    public ViewPagerAdapter(FragmentManager manager, LayoutInflater layoutInflater) {
        super(manager);
        this.mInflater = layoutInflater;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    /**
     * @param tabLayout
     * @param busRouteResult
     * @return
     */
    public View getTabView(TabLayout tabLayout, BusRouteResult busRouteResult) {
        View view = mInflater.inflate(R.layout.tab_layout, tabLayout, false);
        TextView busLineTitle = (TextView) view.findViewById(R.id.bus_line_text);
        ImageView busLineImage = (ImageView) view.findViewById(R.id.bus_line_image);

        if (busRouteResult.getType() == 0) {
            busLineTitle.setText(busRouteResult.getBusRoutes().get(0).getBusLineName());
            busLineImage.setImageResource(R.drawable.mini_bondi);
        } else {
            String text = busRouteResult.getBusRoutes().get(0).getBusLineName() + " / " + busRouteResult.getBusRoutes().get(1).getBusLineName();
            busLineTitle.setText(text);
            busLineImage.setImageResource(R.drawable.mini_combinado);
        }
        return view;
    }
}
