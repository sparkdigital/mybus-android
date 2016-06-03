package com.mybus.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mybus.R;
import com.mybus.adapter.HistoryItemAdapter;
import com.mybus.listener.HistoryItemSelectedListener;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class HistoryCardView extends CardView implements HistoryItemSelectedListener{

    private HistoryItemSelectedListener mHistoryItemSelectedListener;
    private RecyclerView mHistoryItemsList;
    private HistoryItemAdapter mListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mHistoryItemsList.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mHistoryItemsList.setLayoutManager(mLayoutManager);

        //TODO: Should get the list of history
        String[] myDataSet = {"Avenida Pedro Luro 2228", "Aristobulo del Valle 2248", "Avenida Constitucion 570"};
        mListAdapter = new HistoryItemAdapter(myDataSet);
        mListAdapter.setItemSelectedListener(this);
        mHistoryItemsList.setAdapter(mListAdapter);
    }

    @Override
    public void onHistoryItemSelected(String result) {
        if (mHistoryItemSelectedListener != null) {
            mHistoryItemSelectedListener.onHistoryItemSelected(result);
        }
    }
}
