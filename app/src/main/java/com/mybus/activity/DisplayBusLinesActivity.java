package com.mybus.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mybus.R;
import com.mybus.adapter.BusLineViewAdapter;
import com.mybus.asynctask.BusLinesRequestCallback;
import com.mybus.model.BusLine;
import com.mybus.service.ServiceFacade;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayBusLinesActivity extends BaseDisplayActivity implements BusLinesRequestCallback{

    @Bind(R.id.bus_lines_recycler_view)
    RecyclerView mBusLinesRecyclerView;
    private BusLineViewAdapter mBusLineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mBusLinesRecyclerView.setHasFixedSize(true);
        mBusLineAdapter = new BusLineViewAdapter();
        mBusLinesRecyclerView.setAdapter(mBusLineAdapter);
        mBusLinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //start the bus lines request, view will be populated after the callback
        ServiceFacade.getInstance().getBusLines(this);
    }

    @Override
    public void onBusLinesFound(List<BusLine> busLines) {
        mBusLineAdapter.setDataSet(busLines);
        mBusLineAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_display_bus_lines;
    }

    @Override
    public int getToolbarId() {
        return R.id.displayBusLinesToolbar;
    }

    @Override
    protected int getToolbarTittle() {
        return R.string.displayBusLinesToolbarTittle;
    }
}
