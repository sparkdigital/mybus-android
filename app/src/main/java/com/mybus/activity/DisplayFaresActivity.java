package com.mybus.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mybus.R;
import com.mybus.adapter.FareViewAdapter;
import com.mybus.asynctask.FaresRequestCallback;
import com.mybus.builder.MyBusServiceUrlBuilder;
import com.mybus.model.Fare;
import com.mybus.service.ServiceFacade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFaresActivity extends BaseMyBusActivity implements FaresRequestCallback {

    @Bind(R.id.fares_recycler_view)
    RecyclerView mFaresRecyclerView;
    @Bind(R.id.no_internet_layout)
    LinearLayout mNoInternetLayout;
    private FareViewAdapter mFaresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        SharedPreferences settings = getSharedPreferences(MainActivity.FARES, 0);
        SharedPreferences.Editor editor = settings.edit();
        String fares = settings.getString(MainActivity.FARES, "");

        if (fares.length() == 0) {
            ServiceFacade.getInstance().getFares(this, this);

        } else {
            populateFaresValues(fares);
        }
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

    @Override
    public void onFaresFound(String fares) {
        if (fares != null && fares.length() != 0) {
            SharedPreferences settings = getSharedPreferences(MainActivity.FARES, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(MainActivity.FARES, fares);
            editor.commit();
            populateFaresValues(fares);
        } else {
            //if there is no cached fares due to no internet
            mFaresRecyclerView.setVisibility(View.GONE);
            mNoInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    private void populateFaresValues(String fares) {
        mFaresRecyclerView.setVisibility(View.VISIBLE);
        mNoInternetLayout.setVisibility(View.GONE);
        mFaresRecyclerView.setHasFixedSize(true);
        mFaresAdapter = new FareViewAdapter(this);
        mFaresRecyclerView.setAdapter(mFaresAdapter);
        mFaresRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(fares);
            JSONArray jsonArray = jsonObject.getJSONArray("Results");
            List<Fare> faresList = Fare.parseResults(jsonArray);
            mFaresAdapter.setDataSet(faresList);
            mFaresAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
