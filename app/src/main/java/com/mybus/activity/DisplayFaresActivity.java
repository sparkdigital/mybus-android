package com.mybus.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mybus.R;
import com.mybus.adapter.FareViewAdapter;
import com.mybus.asynctask.FaresRequestCallback;
import com.mybus.model.Fare;
import com.mybus.service.ServiceFacade;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFaresActivity extends BaseDisplayActivity implements FaresRequestCallback {

    @Bind(R.id.fares_recycler_view)
    RecyclerView mFaresRecyclerView;
    private FareViewAdapter mFareViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mFaresRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mFaresRecyclerView.setLayoutManager(mLayoutManager);
        //start the fares request, view will be populated after the callback
        ServiceFacade.getInstance().getFares(this, this);
    }

    @Override
    public void onFaresFound(List<Fare> fares) {
        mFareViewAdapter = new FareViewAdapter(fares, this);
        mFaresRecyclerView.setAdapter(mFareViewAdapter);
    }

    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_display_fares;
    }

    @Override
    public int getToolbarId() {
        return R.id.displayFaresToolbar;
    }

    @Override
    protected int getToolbarTittle() {
        return R.string.displayFaresToolbarTittle;
    }
}
