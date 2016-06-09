package com.mybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mybus.R;
import com.mybus.adapter.FavoriteItemAdapter;
import com.mybus.adapter.FavoriteViewAdapter;
import com.mybus.adapter.HistoryItemAdapter;
import com.mybus.model.Favorite;
import com.mybus.view.HistoryCardView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFavoritesActivity extends BaseDisplayActivity {

    @Bind(R.id.favorites_recycler_view)
    RecyclerView mFavoritesRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    private FavoriteViewAdapter mFavoriteViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ArrayList<Favorite> favorites = new ArrayList<Favorite>();
        favorites.add(new Favorite("casa", "dorrego 900") );
        favorites.add(new Favorite("escuela", "libertad 2000") );

        mFavoritesRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mFavoritesRecyclerView.setLayoutManager(mLayoutManager);

        mFavoriteViewAdapter = new FavoriteViewAdapter(favorites);
        mFavoritesRecyclerView.setAdapter(mFavoriteViewAdapter);


        mFavoriteViewAdapter = new FavoriteViewAdapter(favorites);
    }

    private void startSearchActivityToAddFavorite(int searchHint, int requestCode) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchIntent.putExtra(SearchActivity.ADD_FAVORITE, "YES");
        startActivityForResult(searchIntent, requestCode);
        overridePendingTransition(0, 0);
    }

    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_display_favorites;
    }

    @Override
    public int getToolbarId() {
        return R.id.displayFavoritesToolbar;
    }

    @Override
    protected int getToolbarTittle() {
        return R.string.displayFavoritesToolbarTittle;
    }
}
