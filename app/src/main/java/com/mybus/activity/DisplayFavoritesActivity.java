package com.mybus.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mybus.R;
import com.mybus.adapter.FavoriteViewAdapter;
import com.mybus.dao.FavoriteLocationDao;
import com.mybus.listener.FavoriteDeleteListener;
import com.mybus.listener.FavoriteEditListener;
import com.mybus.listener.OnAddNewFavoriteListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.view.FavoriteNameAlertDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFavoritesActivity extends BaseDisplayActivity implements FavoriteEditListener, FavoriteDeleteListener, OnAddNewFavoriteListener {

    @Bind(R.id.favorites_recycler_view)
    RecyclerView mFavoritesRecyclerView;
    @Bind(R.id.add_favorite_action_button)
    FloatingActionButton mAddFavoriteActionButton;

    public static final int EDIT_SEARCH_RESULT_ID = 1;
    public static final int ADD_SEARCH_RESULT_ID = 2;

    List<FavoriteLocation> mFavorites;
    private int mFavoritePositionToEdit;

    private RecyclerView.LayoutManager mLayoutManager;

    private FavoriteViewAdapter mFavoriteViewAdapter;
    private String newAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        //get all the from real
        mFavorites = FavoriteLocationDao.getInstance(this).getAll();

        mFavoritesRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mFavoritesRecyclerView.setLayoutManager(mLayoutManager);

        mFavoriteViewAdapter = new FavoriteViewAdapter(mFavorites, this, this);
        mFavoritesRecyclerView.setAdapter(mFavoriteViewAdapter);

        mAddFavoriteActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayFavoritesActivity.this.startSearchActivityToAddFavorite(ADD_SEARCH_RESULT_ID);
            }
        });
    }

    private void startSearchActivityToAddFavorite(int requestCode) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchIntent.putExtra(SearchActivity.FAVORITES_EXTRA, "YES");
        startActivityForResult(searchIntent, requestCode);
        overridePendingTransition(0, 0);
    }

    private void startSearchActivityToEditFavorite(int requestCode, String favoriteAddress) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchIntent.putExtra(SearchActivity.FAVORITES_EXTRA, "YES");
        searchIntent.putExtra(SearchActivity.SEARCH_ADDRESS_EXTRA, favoriteAddress);
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
                newAddress = data.getStringExtra(SearchActivity.RESULT_STREET_EXTRA);
                switch (requestCode) {
                    case ADD_SEARCH_RESULT_ID:
                        FavoriteNameAlertDialog favoriteNameAlertDialog = new FavoriteNameAlertDialog();
                        favoriteNameAlertDialog.setmOnAddNewFavoriteListener(this);
                        favoriteNameAlertDialog.show(getFragmentManager(), "Favorite Name");
                        break;
                    case EDIT_SEARCH_RESULT_ID:
                        FavoriteLocation oldFavorite = mFavorites.get(mFavoritePositionToEdit);
                        Toast.makeText(this, "Edited " + oldFavorite.getAddress() + "to " + newAddress, Toast.LENGTH_LONG).show();
                        oldFavorite.setAddress(newAddress);
                        mFavoriteViewAdapter.notifyDataSetChanged();
                        break;
                }
        }
    }

    @Override
    public void onFavoriteItemEdit(int position) {
        this.mFavoritePositionToEdit = position;
        startSearchActivityToEditFavorite(EDIT_SEARCH_RESULT_ID, mFavorites.get(position).getAddress());
    }

    @Override
    public void onFavoriteItemDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.favorite_confirm_delete))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Long favid = mFavorites.get(position).getId();
                        FavoriteLocationDao.getInstance(DisplayFavoritesActivity.this).remove(favid);
                        mFavorites.remove(position);
                        mFavoriteViewAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onAddNewFavorite(String favoriteName) {
        FavoriteLocation newFavorite = new FavoriteLocation(favoriteName, newAddress);
        if (FavoriteLocationDao.getInstance(this).saveOrUpdate(newFavorite)) {
            mFavorites.add(newFavorite);
            mFavoriteViewAdapter.notifyDataSetChanged();
        }
    }
}
