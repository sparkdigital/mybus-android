package com.mybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mybus.R;
import com.mybus.adapter.FavoriteViewAdapter;
import com.mybus.model.Favorite;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFavoritesActivity extends BaseDisplayActivity {

    @Bind(R.id.favorites_recycler_view)
    RecyclerView mFavoritesRecyclerView;
    @Bind(R.id.add_favorite_action_button)
    FloatingActionButton mAddFavoriteActionButton;

    private RecyclerView.LayoutManager mLayoutManager;

    private FavoriteViewAdapter mFavoriteViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ArrayList<Favorite> favorites = new ArrayList<Favorite>();
        favorites.add(new Favorite("casa", "dorrego 900"));
        favorites.add(new Favorite("escuela", "libertad 2000"));

        mFavoritesRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mFavoritesRecyclerView.setLayoutManager(mLayoutManager);

        mFavoriteViewAdapter = new FavoriteViewAdapter(favorites);
        mFavoritesRecyclerView.setAdapter(mFavoriteViewAdapter);

        mFavoriteViewAdapter = new FavoriteViewAdapter(favorites);
        mAddFavoriteActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayFavoritesActivity.this.startSearchActivityToAddFavorite(0);
            }
        });
    }

    private void startSearchActivityToAddFavorite(int requestCode) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_CANCELED:
                //The user canceled
                break;
            case RESULT_OK:
                String address = data.getStringExtra(SearchActivity.RESULT_STREET_EXTRA);
                //TODO add the favorite to the list
                Toast.makeText(this, "Added " + address + " to favotires", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
