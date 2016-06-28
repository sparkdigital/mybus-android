package com.mybus.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
        ServiceFacade.getInstance().getFares(this, this);
        mFaresRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mFaresRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onFaresFound(List<Fare> fares) {
        mFareViewAdapter = new FareViewAdapter(fares);
        mFaresRecyclerView.setAdapter(mFareViewAdapter);
        Toast.makeText(this, fares.toString(), Toast.LENGTH_LONG).show();
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
