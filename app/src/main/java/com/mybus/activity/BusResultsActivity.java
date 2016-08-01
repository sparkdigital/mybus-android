package com.mybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.adapter.BusResultViewAdapter;
import com.mybus.listener.BusLineListItemListener;
import com.mybus.model.BusRouteResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lucas De Lio on 7/19/2016.
 */
public class BusResultsActivity extends AppCompatActivity implements BusLineListItemListener {

    public static final String RESULTS_EXTRA = "RESULTS_EXTRA";
    public static final String SELECTED_BUS_LINE_EXTRA = "SELECTED_BUS_LINE_EXTRA";
    public static final String ORIGIN_ADDRESS_EXTRA = "ORIGIN_ADDRESS_EXTRA";
    public static final String DESTINATION_ADDRESS_EXTRA = "DESTINATION_ADDRESS_EXTRA";

    @Bind(R.id.bus_results_recycler_view)
    RecyclerView mBusResultsRecyclerView;
    @Bind(R.id.searchOriginTextView)
    TextView mSearchOriginTextView;
    @Bind(R.id.searchDestinationTextView)
    TextView mSearchDestinationTextView;
    @Bind(R.id.backArrowImageView)
    ImageView mBackArrowImageView;

    private List<BusRouteResult> mResults;
    private BusResultViewAdapter mBusResultViewAdapter;

    @OnClick(R.id.flipSearch)
    public void onFlipSearch(View view) {
        String originAddress = mSearchOriginTextView.getText().toString();
        String destinationAddress = mSearchDestinationTextView.getText().toString();
        mSearchDestinationTextView.setText(originAddress);
        mSearchOriginTextView.setText(destinationAddress);
        //TODO perform a new search
    }

    @OnClick(R.id.backArrowImageView)
    public void onBackPressed(View view) {
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_results);
        ButterKnife.bind(this);
        mSearchOriginTextView.setText(getIntent().getStringExtra(ORIGIN_ADDRESS_EXTRA));
        mSearchDestinationTextView.setText(getIntent().getStringExtra(DESTINATION_ADDRESS_EXTRA));
        mResults = getIntent().getParcelableArrayListExtra(RESULTS_EXTRA);
        //initialize the RecyclerView
        mBusResultsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mBusResultsRecyclerView.setLayoutManager(mLayoutManager);
        mBusResultViewAdapter = new BusResultViewAdapter(mResults, this, this);
        mBusResultsRecyclerView.setAdapter(mBusResultViewAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_BUS_LINE_EXTRA, position);
        intent.putExtra(RESULTS_EXTRA, (ArrayList<BusRouteResult>) mResults);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    // interface to handle the route selection
    public interface OnBusResultSelectListener {
        void onBusResultSelected();
    }
}
