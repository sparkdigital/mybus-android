package com.mybus.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.adapter.HistoryItemAdapter;
import com.mybus.dao.RecentLocationDao;
import com.mybus.listener.HistoryItemSelectedListener;
import com.mybus.model.RecentLocation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class HistoryCardView extends CardView implements HistoryItemSelectedListener {

    private HistoryItemSelectedListener mHistoryItemSelectedListener;
    private RecyclerView mHistoryItemsList;
    private HistoryItemAdapter mListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mNoRecentTextView;

    public void setHistoryItemSelectedListener(HistoryItemSelectedListener mHistoryItemSelectedListener) {
        this.mHistoryItemSelectedListener = mHistoryItemSelectedListener;
    }

    public HistoryCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.history_card_view, this);

        mHistoryItemsList = (RecyclerView) findViewById(R.id.recent_list);
        mNoRecentTextView = (TextView) findViewById(R.id.noRecentTv);
    }

    @Override
    public void onHistoryItemSelected(RecentLocation recentLocation) {
        if (mHistoryItemSelectedListener != null) {
            mHistoryItemSelectedListener.onHistoryItemSelected(recentLocation);
        }
    }

    /**
     * Sets the type of the search so we can show the proper recent searches
     *
     * @param type
     */
    public void setType(int type) {
        if (type < 0) {
            //do nothing
            return;
        }
        List<RecentLocation> recentLocations = RecentLocationDao.getInstance(getContext()).getAllByField("type", type);
        if (recentLocations != null && recentLocations.size() > 0) {
            //Sorting recent locations
            Collections.sort(recentLocations, Collections.<RecentLocation>reverseOrder());

            mNoRecentTextView.setVisibility(GONE);
            mHistoryItemsList.setVisibility(VISIBLE);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mHistoryItemsList.setHasFixedSize(true);
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mHistoryItemsList.setLayoutManager(mLayoutManager);

            mListAdapter = new HistoryItemAdapter(recentLocations);
            mListAdapter.setItemSelectedListener(this);
            mHistoryItemsList.setAdapter(mListAdapter);
        } else {
            mHistoryItemsList.setVisibility(GONE);
            mNoRecentTextView.setVisibility(VISIBLE);
        }
    }
}
