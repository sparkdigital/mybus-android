package com.mybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mybus.R;
import com.mybus.adapter.BusLineViewAdapter;
import com.mybus.asynctask.BusLinesRequestCallback;
import com.mybus.listener.BusLineListItemListener;
import com.mybus.model.BusLine;
import com.mybus.service.ServiceFacade;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayBusLinesActivity extends BaseDisplayActivity implements BusLinesRequestCallback, BusLineListItemListener{

    @Bind(R.id.bus_lines_recycler_view)
    RecyclerView mBusLinesRecyclerView;
    private BusLineViewAdapter mBusLineAdapter;
    private List<BusLine> mBusLines;
    public static final String RESULT_BUS_LINE_ID = "BUS_LINE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mBusLinesRecyclerView.setHasFixedSize(true);
        mBusLineAdapter = new BusLineViewAdapter(this);
        mBusLinesRecyclerView.setAdapter(mBusLineAdapter);
        mBusLinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //start the bus lines request, view will be populated after the callback
        ServiceFacade.getInstance().getBusLines(this);
    }

    @Override
    public void onBusLinesFound(List<BusLine> busLines) {
        mBusLines = busLines;
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

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_BUS_LINE_ID, mBusLines.get(position).getId());
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
