package com.mybus.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.adapter.FavoriteViewAdapter;
import com.mybus.dao.FavoriteLocationDao;
import com.mybus.listener.FavoriteListItemListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.model.GeoLocation;
import com.mybus.model.SearchType;
import com.mybus.view.FavoriteNameAlertDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFavoritesActivity extends BaseDisplayActivity implements FavoriteListItemListener, FavoriteNameAlertDialog.FavoriteAddOrEditNameListener {

    @Bind(R.id.favorites_recycler_view)
    RecyclerView mFavoritesRecyclerView;
    @Bind(R.id.noFavorites)
    TextView mNoFavoritesTextView;

    private static final int EDIT_SEARCH_RESULT_ID = 1;
    private static final int ADD_SEARCH_RESULT_ID = 2;

    private List<FavoriteLocation> mFavorites;
    private int mFavoritePositionToEdit;
    private FavoriteViewAdapter mFavoriteViewAdapter;
    private FavoriteLocation oldFavorite;
    private GeoLocation mGeoLocation;

    @OnClick(R.id.add_favorite_action_button)
    void onAddFavoriteButtonClicked(View view) {
        DisplayFavoritesActivity.this.startSearchActivityForFavorite(ADD_SEARCH_RESULT_ID, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        //get all favorites from realm
        mFavorites = FavoriteLocationDao.getInstance(this).getAll();

        mFavoritesRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mFavoritesRecyclerView.setLayoutManager(mLayoutManager);

        mFavoriteViewAdapter = new FavoriteViewAdapter(mFavorites, this);
        mFavoritesRecyclerView.setAdapter(mFavoriteViewAdapter);

        if (!mFavorites.isEmpty()) {
            mNoFavoritesTextView.setVisibility(View.GONE);
        }
    }

    /**
     * @param requestCode
     * @param favoriteAddress
     */
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
                mGeoLocation = data.getParcelableExtra(SearchActivity.RESULT_GEOLOCATION_EXTRA);
                FavoriteNameAlertDialog favoriteNameAlertDialog = null;
                switch (requestCode) {
                    case ADD_SEARCH_RESULT_ID:
                        favoriteNameAlertDialog = FavoriteNameAlertDialog.
                                newInstance(FavoriteNameAlertDialog.TYPE_ADD, null);
                        break;
                    case EDIT_SEARCH_RESULT_ID:
                        oldFavorite = mFavorites.get(mFavoritePositionToEdit);
                        favoriteNameAlertDialog = FavoriteNameAlertDialog.
                                newInstance(FavoriteNameAlertDialog.TYPE_EDIT, oldFavorite.getName());
                        break;
                    default:
                        break;
                }
                if (favoriteNameAlertDialog != null) {
                    favoriteNameAlertDialog.show(getFragmentManager(), "Favorite Name Dialog");
                }
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
        builder.setMessage(this.getResources().getString(R.string.favorite_confirm_delete_message))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Long favId = mFavorites.get(position).getId();
                        //check if the favorite was successfully removed form the database
                        if (FavoriteLocationDao.getInstance(DisplayFavoritesActivity.this).remove(favId)) {
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
    public void onNewFavoriteName(String favoriteName) {
        FavoriteLocation newFavorite = new FavoriteLocation();
        saveOrUpdateFavorite(newFavorite, favoriteName, ADD_SEARCH_RESULT_ID);
    }

    @Override
    public void onEditFavoriteName(String favoriteName) {
        saveOrUpdateFavorite(oldFavorite, favoriteName, EDIT_SEARCH_RESULT_ID);
    }

    /**
     * @param favorite
     * @param favoriteName
     */
    private void saveOrUpdateFavorite(FavoriteLocation favorite, String favoriteName, int type) {
        favorite.setName(favoriteName);
        favorite.setAddress(mGeoLocation.getAddress());
        favorite.setLatitude(mGeoLocation.getLatLng().latitude);
        favorite.setLongitude(mGeoLocation.getLatLng().longitude);
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
