package com.mybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mybus.R;
import com.mybus.adapter.BusResultViewAdapter;
import com.mybus.listener.BusLineListItemListener;
import com.mybus.model.BusRouteResult;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 7/19/2016.
 */
public class BusResultsActivity extends AppCompatActivity implements BusLineListItemListener {

    public static final String RESULTS_EXTRA = "RESULTS_EXTRA";
    public static final String SELECTED_BUS_LINE_EXTRA = "SELECTED_BUS_LINE_EXTRA";

    @Bind(R.id.bus_results_recycler_view)
    RecyclerView mBusResultsRecyclerView;

    private List<BusRouteResult> mResults;
    private BusResultViewAdapter mBusResultViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_results);
        ButterKnife.bind(this);
        mResults = getIntent().getParcelableArrayListExtra(BusResultsActivity.RESULTS_EXTRA);
        //initialize the RecyclerView
        mBusResultsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mBusResultsRecyclerView.setLayoutManager(mLayoutManager);
        mBusResultViewAdapter = new BusResultViewAdapter(mResults, this);
        mBusResultsRecyclerView.setAdapter(mBusResultViewAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_BUS_LINE_EXTRA, position);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    // interface to handle the route selection
    public interface OnBusResultSelectListener {
        void onBusResultSelected();
    }
}
