package com.mybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mybus.R;
import com.mybus.adapter.BusResultViewAdapter;
import com.mybus.asynctask.RouteSearchCallback;
import com.mybus.listener.BusLineListItemListener;
import com.mybus.model.BusRouteResult;
import com.mybus.model.GeoLocation;
import com.mybus.model.SearchType;
import com.mybus.requirements.DeviceRequirementsChecker;
import com.mybus.service.ServiceFacade;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lucas De Lio on 7/19/2016.
 */
public class BusResultsActivity extends BaseMyBusActivity implements BusLineListItemListener, RouteSearchCallback {

    public static final String RESULTS_EXTRA = "RESULTS_EXTRA";
    public static final String SELECTED_BUS_LINE_EXTRA = "SELECTED_BUS_LINE_EXTRA";
    public static final String START_GEOLOCATION_EXTRA = "START_GEOLOCATION_EXTRA";
    public static final String END_GEOLOCATION_EXTRA = "END_GEOLOCATION_EXTRA";
    public static final int FROM_SEARCH_ORIGIN_RESULT_ID = 1;
    public static final int FROM_SEARCH_DESTINATION_RESULT_ID = 2;

    @BindView(R.id.bus_results_recycler_view)
    RecyclerView mBusResultsRecyclerView;
    @BindView(R.id.searchOriginTextView)
    TextView mSearchOriginTextView;
    @BindView(R.id.searchDestinationTextView)
    TextView mSearchDestinationTextView;
    @BindView(R.id.backArrowImageView)
    ImageView mBackArrowImageView;

    private List<BusRouteResult> mResults;
    private BusResultViewAdapter mBusResultViewAdapter;
    private GeoLocation mStartGeoLocation;
    private GeoLocation mEndGeoLocation;

    @OnClick(R.id.flipSearch)
    public void onFlipSearch(View view) {
        if (DeviceRequirementsChecker.isNetworkAvailable(this)) {
            //switch origin and destination variables
            switchOriginAndDestination();
            //perform a new search
            showProgressDialog(getString(R.string.toast_searching));
            ServiceFacade.getInstance().searchRoutes(mStartGeoLocation.getLatLng(), mEndGeoLocation.getLatLng(), this);
        } else {
            Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void switchOriginAndDestination() {
        GeoLocation oldStartGeoLocation = mStartGeoLocation;
        mStartGeoLocation = mEndGeoLocation;
        mEndGeoLocation = oldStartGeoLocation;
        mSearchOriginTextView.setText(mStartGeoLocation.getAddress());
        mSearchDestinationTextView.setText(mEndGeoLocation.getAddress());
    }

    @OnClick(R.id.searchOriginTextView)
    public void onSearchOrigin(View view) {
        startSearchActivity(FROM_SEARCH_ORIGIN_RESULT_ID, SearchType.ORIGIN);
    }

    @OnClick(R.id.searchDestinationTextView)
    public void onSearchDestination(View view) {
        startSearchActivity(FROM_SEARCH_DESTINATION_RESULT_ID, SearchType.DESTINATION);
    }

    @OnClick(R.id.backArrowImageView)
    public void onBackArrowPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mBusResultsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mBusResultsRecyclerView.setLayoutManager(mLayoutManager);
        Intent data = getIntent();
        mStartGeoLocation = data.getParcelableExtra(START_GEOLOCATION_EXTRA);
        mEndGeoLocation = data.getParcelableExtra(END_GEOLOCATION_EXTRA);
        mResults = getIntent().getParcelableArrayListExtra(RESULTS_EXTRA);
        //set the TextView's address
        mSearchOriginTextView.setText(mStartGeoLocation.getAddress());
        mSearchDestinationTextView.setText(mEndGeoLocation.getAddress());
        //initialize the RecyclerView
        mBusResultViewAdapter = new BusResultViewAdapter(mResults, this, this);
        mBusResultsRecyclerView.setAdapter(mBusResultViewAdapter);
    }

    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_bus_results;
    }

    @Override
    public int getToolbarId() {
        // This Activity has no toolbar
        return 0;
    }

    @Override
    protected int getToolbarTittle() {
        // This Activity has no title
        return 0;
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_BUS_LINE_EXTRA, position);
        intent.putExtra(RESULTS_EXTRA, (ArrayList<BusRouteResult>) mResults);
        intent.putExtra(BusResultsActivity.START_GEOLOCATION_EXTRA, mStartGeoLocation);
        intent.putExtra(BusResultsActivity.END_GEOLOCATION_EXTRA, mEndGeoLocation);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onRouteFound(List<BusRouteResult> results) {
        cancelProgressDialog();
        mBusResultViewAdapter.setDataset(results);
        mBusResultViewAdapter.notifyDataSetChanged();
        //update mResults, then used when return to the MainActivity
        mResults = results;
    }

    private void startSearchActivity(int requestCode, int type) {
        Intent searchIntent = new Intent(BusResultsActivity.this, SearchActivity.class);
        searchIntent.putExtra(SearchActivity.SEARCH_TYPE_EXTRA, type);
        startActivityForResult(searchIntent, requestCode);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GeoLocation geoLocation = data.getParcelableExtra(SearchActivity.RESULT_GEOLOCATION_EXTRA);
            updateOriginAndDestination(requestCode, geoLocation);
            //perform a new search
            showProgressDialog(getString(R.string.toast_searching));
            ServiceFacade.getInstance().searchRoutes(mStartGeoLocation.getLatLng(), mEndGeoLocation.getLatLng(), this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateOriginAndDestination(int requestCode, GeoLocation geoLocation) {
        switch (requestCode) {
            case FROM_SEARCH_ORIGIN_RESULT_ID:
                mStartGeoLocation = geoLocation;
                mSearchOriginTextView.setText(mStartGeoLocation.getAddress());
                break;
            case FROM_SEARCH_DESTINATION_RESULT_ID:
                mEndGeoLocation = geoLocation;
                mSearchDestinationTextView.setText(mEndGeoLocation.getAddress());
                break;
            default:
                break;
        }
    }

    // interface to handle the route selection
    public interface OnBusResultSelectListener {
        void onBusResultSelected();
    }


}
