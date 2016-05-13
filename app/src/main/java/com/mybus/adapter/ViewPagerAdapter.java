package com.mybus.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.fragment.BusRouteFragment;
import com.mybus.model.BusRouteResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<BusRouteFragment> mFragmentList = new ArrayList<>();
    private final LayoutInflater mInflater;

    @Bind(R.id.bus_line_text)
    TextView mBusLineTitle;
    @Bind(R.id.bus_line_image)
    ImageView mBusLineImage;

    public ViewPagerAdapter(FragmentManager manager, LayoutInflater layoutInflater) {
        super(manager);
        this.mInflater = layoutInflater;
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
        View view = mInflater.inflate(R.layout.tab_layout, tabLayout, false);
        ButterKnife.bind(this, view);


        if (busRouteResult.getType() == 0) {
            mBusLineTitle.setText(busRouteResult.getBusRoutes().get(0).getBusLineName());
            mBusLineImage.setImageResource(R.drawable.mini_bondi);
        } else {
            String text = busRouteResult.getBusRoutes().get(0).getBusLineName() + " / " + busRouteResult.getBusRoutes().get(1).getBusLineName();
            mBusLineTitle.setText(text);
            mBusLineImage.setImageResource(R.drawable.mini_combinado);
        }
        return view;
    }
}
