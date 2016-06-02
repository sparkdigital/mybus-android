package com.mybus.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mybus.R;
import com.mybus.adapter.FavoriteItemAdapter;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class FavoritesCardView extends CardView {

    private FavoriteItemSelectedListener mFavoriteItemSelectedListener;
    private RecyclerView mFavoriteItemsList;
    private RecyclerView.Adapter mListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public void setFavoriteItemSelectedListener(FavoriteItemSelectedListener mHistoryItemSelectedListener) {
        this.mFavoriteItemSelectedListener = mHistoryItemSelectedListener;
    }

    public FavoritesCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.favorites_card_view, this);

        mFavoriteItemsList = (RecyclerView) findViewById(R.id.favorites_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mFavoriteItemsList.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mFavoriteItemsList.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        String[] myDataSet = {"Casa", "Trabajo", "Gym", "Casa Novia", "Casa", "Trabajo", "Gym", "Casa Novia"};
        mListAdapter = new FavoriteItemAdapter(myDataSet);
        mFavoriteItemsList.setAdapter(mListAdapter);
    }

    public interface FavoriteItemSelectedListener {

        void onFavoriteItemSelected(String result);
    }
}
