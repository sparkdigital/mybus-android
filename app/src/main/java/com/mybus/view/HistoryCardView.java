package com.mybus.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.adapter.HistoryItemAdapter;
import com.mybus.listener.HistoryItemSelectedListener;
import com.mybus.model.RecentLocation;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class HistoryCardView extends CardView implements HistoryItemSelectedListener {

    private HistoryItemSelectedListener mHistoryItemSelectedListener;
    private RecyclerView mRecyclerView;
    private HistoryItemAdapter mListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mNoRecentTextView;
    private List<RecentLocation> mRecentLocationList;

    public void setHistoryItemSelectedListener(HistoryItemSelectedListener mHistoryItemSelectedListener) {
        this.mHistoryItemSelectedListener = mHistoryItemSelectedListener;
    }

    public HistoryCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.history_card_view, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recent_list);
        mNoRecentTextView = (TextView) findViewById(R.id.noRecentTv);
    }

    @Override
    public void onHistoryItemSelected(int position) {
        if (mHistoryItemSelectedListener != null) {
            mHistoryItemSelectedListener.onHistoryItemSelected(position);
        }
    }

    public void setList(List<RecentLocation> list) {
        mRecentLocationList = list;
        if (mRecentLocationList != null && mRecentLocationList.size() > 0) {
            mNoRecentTextView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mListAdapter = new HistoryItemAdapter(mRecentLocationList);
            mListAdapter.setItemSelectedListener(this);
            mRecyclerView.setAdapter(mListAdapter);
        } else {
            mNoRecentTextView.setVisibility(VISIBLE);
            mRecyclerView.setVisibility(GONE);
        }
    }
}
