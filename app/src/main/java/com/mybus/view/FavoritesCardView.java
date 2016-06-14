package com.mybus.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.adapter.FavoriteItemAdapter;
import com.mybus.listener.FavoriteItemSelectedListener;
import com.mybus.model.FavoriteLocation;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class FavoritesCardView extends CardView implements FavoriteItemSelectedListener {

    private FavoriteItemSelectedListener mFavoriteItemSelectedListener;
    private RecyclerView mRecyclerView;
    private FavoriteItemAdapter mListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mNoFavoritesTextView;
    private List<FavoriteLocation> mFavoriteLocationList;

    public void setFavoriteItemSelectedListener(FavoriteItemSelectedListener mHistoryItemSelectedListener) {
        this.mFavoriteItemSelectedListener = mHistoryItemSelectedListener;
    }

    public FavoritesCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.favorites_card_view, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.favorites_list);
        mNoFavoritesTextView = (TextView) findViewById(R.id.noFavorites);
    }

    public void setItemList(List<FavoriteLocation> list) {
        mFavoriteLocationList = list;
        if (mFavoriteLocationList != null && mFavoriteLocationList.size() > 0) {
            mNoFavoritesTextView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mListAdapter = new FavoriteItemAdapter(mFavoriteLocationList);
            mListAdapter.setItemSelectedListener(this);
            mRecyclerView.setAdapter(mListAdapter);
        } else {
            mNoFavoritesTextView.setVisibility(VISIBLE);
            mRecyclerView.setVisibility(GONE);
        }
    }

    @Override
    public void onFavoriteItemSelected(int position) {
        if (mFavoriteItemSelectedListener != null) {
            mFavoriteItemSelectedListener.onFavoriteItemSelected(position);
        }
    }
}
