package com.mybus.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.R;
import com.mybus.adapter.FavoriteViewAdapter;
import com.mybus.dao.FavoriteLocationDao;
import com.mybus.listener.FavoriteAddOrEditListener;
import com.mybus.listener.FavoriteListItemListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.model.SearchType;
import com.mybus.view.FavoriteNameAlertDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFavoritesActivity extends BaseDisplayActivity implements FavoriteListItemListener, FavoriteAddOrEditListener {

    @Bind(R.id.favorites_recycler_view)
    RecyclerView mFavoritesRecyclerView;
    @Bind(R.id.add_favorite_action_button)
    FloatingActionButton mAddFavoriteActionButton;
    @Bind(R.id.noFavorites)
    TextView mNoFavoritesTextView;

    private static final int EDIT_SEARCH_RESULT_ID = 1;
    private static final int ADD_SEARCH_RESULT_ID = 2;

    private List<FavoriteLocation> mFavorites;
    private int mFavoritePositionToEdit;
    private RecyclerView.LayoutManager mLayoutManager;
    private FavoriteViewAdapter mFavoriteViewAdapter;
    private String newAddress;
    private LatLng newLocation;
    private FavoriteLocation oldFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        //get all the from real
        mFavorites = FavoriteLocationDao.getInstance(this).getAll();

        mFavoritesRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mFavoritesRecyclerView.setLayoutManager(mLayoutManager);

        mFavoriteViewAdapter = new FavoriteViewAdapter(mFavorites, this);
        mFavoritesRecyclerView.setAdapter(mFavoriteViewAdapter);

        mAddFavoriteActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayFavoritesActivity.this.startSearchActivityForFavorite(ADD_SEARCH_RESULT_ID, null);
            }
        });
        if (!mFavorites.isEmpty()) {
            mNoFavoritesTextView.setVisibility(View.GONE);
        }
    }

    private void startSearchActivityForFavorite(int requestCode, String favoriteAddress) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchIntent.putExtra(SearchActivity.SEARCH_TYPE_EXTRA, SearchType.FAVORITE);
        if (favoriteAddress != null) {
            searchIntent.putExtra(SearchActivity.SEARCH_ADDRESS_EXTRA, favoriteAddress);
        }
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
                newLocation = data.getParcelableExtra(SearchActivity.RESULT_LATLNG_EXTRA);
                FavoriteNameAlertDialog favoriteNameAlertDialog = new FavoriteNameAlertDialog();
                favoriteNameAlertDialog.setListener(this);
                switch (requestCode) {
                    case ADD_SEARCH_RESULT_ID:
                        favoriteNameAlertDialog.setDialogType(FavoriteNameAlertDialog.TYPE_ADD);
                        break;
                    case EDIT_SEARCH_RESULT_ID:
                        favoriteNameAlertDialog.setDialogType(FavoriteNameAlertDialog.TYPE_EDIT);
                        oldFavorite = mFavorites.get(mFavoritePositionToEdit);
                        favoriteNameAlertDialog.setPreviousName(oldFavorite.getName());
                        break;
                    default:
                        break;
                }

                favoriteNameAlertDialog.show(getFragmentManager(), "Favorite Name");
        }
    }

    @Override
    public void onFavoriteItemEdit(int position) {
        this.mFavoritePositionToEdit = position;
        startSearchActivityForFavorite(EDIT_SEARCH_RESULT_ID, mFavorites.get(position).getAddress());
    }

    @Override
    public void onFavoriteItemDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.favorite_confirm_delete))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Long favid = mFavorites.get(position).getId();
                        //check if the favorite was successfully removed form the database
                        if (FavoriteLocationDao.getInstance(DisplayFavoritesActivity.this).remove(favid)) {
                            //remove it locally
                            mFavorites.remove(position);
                            //if there are no favorites, show the no favorites message
                            if (mFavorites.isEmpty()) {
                                mNoFavoritesTextView.setVisibility(View.VISIBLE);
                            }
                        }
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
        FavoriteLocation newFavorite = new FavoriteLocation();
        saveOrUpdateFavorite(newFavorite, favoriteName, ADD_SEARCH_RESULT_ID);
    }

    @Override
    public void onChangeFavoriteName(String favoriteName) {
        saveOrUpdateFavorite(oldFavorite, favoriteName, EDIT_SEARCH_RESULT_ID);
    }

    /**
     * @param favorite
     * @param favoriteName
     */
    private void saveOrUpdateFavorite(FavoriteLocation favorite, String favoriteName, int type) {
        favorite.setName(favoriteName);
        favorite.setAddress(newAddress);
        favorite.setLatitude(newLocation.latitude);
        favorite.setLongitude(newLocation.longitude);
        if (FavoriteLocationDao.getInstance(this).saveOrUpdate(favorite)) {
            if (type == ADD_SEARCH_RESULT_ID) {
                mFavorites.add(favorite);
            }
            mFavoriteViewAdapter.notifyDataSetChanged();
            //hide the no favorites message
            mNoFavoritesTextView.setVisibility(View.GONE);
        }
        mFavoriteViewAdapter.notifyDataSetChanged();
    }
}
