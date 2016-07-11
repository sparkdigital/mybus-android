package com.mybus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.google.android.gms.maps.model.LatLng;
import com.mybus.R;
import com.mybus.dao.FavoriteLocationDao;
import com.mybus.dao.RecentLocationDao;
import com.mybus.helper.StreetSuggestionFilter;
import com.mybus.listener.FavoriteItemSelectedListener;
import com.mybus.listener.HistoryItemSelectedListener;
import com.mybus.listener.OnFindResultsListener;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.model.FavoriteLocation;
import com.mybus.model.GeoLocation;
import com.mybus.model.RecentLocation;
import com.mybus.model.SearchType;
import com.mybus.model.StreetSuggestion;
import com.mybus.requirements.AddressValidator;
import com.mybus.service.ServiceFacade;
import com.mybus.view.FavoritesCardView;
import com.mybus.view.HistoryCardView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class SearchActivity extends AppCompatActivity implements OnAddressGeocodingCompleteCallback,
        HistoryItemSelectedListener, FavoriteItemSelectedListener, OnLocationGeocodingCompleteCallback {

    public static final String SEARCH_TYPE_EXTRA = "SEARCH_TYPE_EXTRA";
    public static final String SEARCH_ADDRESS_EXTRA = "SEARCH_TITLE_EXTRA";
    public static final String RESULT_GEOLOCATION_EXTRA = "RESULT_GEOLOCATION_EXTRA";
    public static final String RESULT_ISFAVORITE_EXTRA = "RESULT_ISFAVORITE_EXTRA";
    public static final String RESULT_FAVORITE_NAME_EXTRA = "RESULT_FAVORITE_NAME_EXTRA";
    private static final String TAG = SearchActivity.class.getSimpleName();

    @Bind(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @Bind(R.id.searchContent)
    LinearLayout mSearchContent;
    @Bind(R.id.card_view_recent)
    HistoryCardView mHistoryCardView;
    @Bind(R.id.card_view_favorites)
    FavoritesCardView mFavoriteCardView;
    private ProgressDialog mDialog;
    private int mSearchType;
    private List<RecentLocation> mRecentLocations;
    private List<FavoriteLocation> mFavoriteLocations;
    private StreetSuggestionFilter mStreetSuggestionFilter;

    @OnClick(R.id.currentLocationCard)
    public void onCurrentLocationCardClick() {
        LocationUpdater locationUpdater = new LocationUpdater(null, this);
        LatLng knownLocation = locationUpdater.getLastKnownLocation();
        if (knownLocation != null) {
            showProgressDialog(getString(R.string.toast_searching_address));
            ServiceFacade.getInstance().performGeocodeByLocation(knownLocation, this, this);
        } else {
            Toast.makeText(this, R.string.cant_find_current_location, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initSearchView();
        Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        mSearchContent.startAnimation(bottomUp);

        mSearchType = getIntent().getIntExtra(SEARCH_TYPE_EXTRA, -1);
        switch (mSearchType) {
            case SearchType.ORIGIN:
                mSearchView.setSearchHint(getString(R.string.floating_search_origin));
                initFavoriteCardView();
                break;
            case SearchType.DESTINATION:
                mSearchView.setSearchHint(getString(R.string.floating_search_destination));
                initFavoriteCardView();
                break;
            case SearchType.FAVORITE:
                if (getIntent().getStringExtra(SEARCH_ADDRESS_EXTRA) != null) {
                    mSearchView.setSearchText(getIntent().getStringExtra(SEARCH_ADDRESS_EXTRA));
                }
                mSearchView.setSearchHint(getString(R.string.floating_search_favorite));
                mFavoriteCardView.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        initHistoryCardView();

        mStreetSuggestionFilter = new StreetSuggestionFilter(SearchActivity.this, new OnFindResultsListener() {

            @Override
            public void onResults(List<StreetSuggestion> results) {

                //this will swap the data and
                //render the collapse/expand animations as necessary
                if (results != null) {
                    mSearchView.swapSuggestions(results);
                }

                //let the users know that the background
                //process has completed
                mSearchView.hideProgress();
            }
        });
    }

    private void initHistoryCardView() {
        mRecentLocations = RecentLocationDao.getInstance(this).getAll();
        if (mRecentLocations != null) {
            //Sorting recent locations. (The list could be empty but never null)
            Collections.sort(mRecentLocations, Collections.<RecentLocation>reverseOrder());
            mHistoryCardView.setList(mRecentLocations);
        }
        mHistoryCardView.setHistoryItemSelectedListener(this);
    }

    private void initFavoriteCardView() {
        mFavoriteLocations = FavoriteLocationDao.getInstance(this).getAll();
        //Sorting favorite locations by usage. (The list could be empty but never null)
        Collections.sort(mFavoriteLocations, Collections.<FavoriteLocation>reverseOrder());
        mFavoriteCardView.setItemList(mFavoriteLocations);
        mFavoriteCardView.setFavoriteItemSelectedListener(this);
    }

    private void initSearchView() {

        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                StreetSuggestion suggestion = (StreetSuggestion) item;
                switch (suggestion.getType()) {
                    case StreetSuggestion.TYPE_FAVORITE:
                        leftIcon.setImageResource(android.R.drawable.ic_menu_myplaces);
                        break;
                    case StreetSuggestion.TYPE_TOURISTIC_PLACE:
                        leftIcon.setImageResource(android.R.drawable.ic_dialog_map);
                        break;
                    default:
                        leftIcon.setImageDrawable(null);
                        break;
                }
                textView.setText(item.getBody());
            }
        });

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (oldQuery.isEmpty() && newQuery.isEmpty()) {
                    mSearchView.clearSuggestions();
                } else {
                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    mStreetSuggestionFilter.filter(newQuery);
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Log.d(TAG, "onSuggestionClicked()");
                StreetSuggestion suggestion = (StreetSuggestion) searchSuggestion;
                switch (suggestion.getType()) {
                    case StreetSuggestion.TYPE_FAVORITE:
                        FavoriteLocation fav = FavoriteLocationDao.getInstance(SearchActivity.this).getById(suggestion.getFavID());
                        setActivityResult(fav.getAddress(), fav.getLatLng(), true, fav.getName());
                        break;
                    case StreetSuggestion.TYPE_TOURISTIC_PLACE:
                        //TODO: Go to the activity without a favorite, but a touristic place instead
                        setActivityResult(suggestion.getTouristicPlace().getDescription(), suggestion.getTouristicPlace().getLatLng(), false, null);
                        break;
                    default:
                        mSearchView.setSearchTextFocused(searchSuggestion.getBody());
                        break;
                }
            }

            @Override
            public void onSearchAction(String currentQuery) {
                Log.d(TAG, "onSearchAction()");
                if (!AddressValidator.isValidAddress(currentQuery)) {
                    Toast.makeText(SearchActivity.this, R.string.invalidAddress, Toast.LENGTH_SHORT).show();
                    return;
                }
                Util.closeSoftKeyboard(SearchActivity.this);
                showProgressDialog(getString(R.string.toast_searching_address));
                ServiceFacade.getInstance().performGeocodeByAddress(currentQuery, SearchActivity.this, SearchActivity.this);
            }
        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                Log.d(TAG, "onHomeClicked()");
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onLocationGeocodingComplete(GeoLocation geoLocation) {
        geocodingComplete(geoLocation);
    }

    @Override
    public void onAddressGeocodingComplete(GeoLocation geoLocation) {
        geocodingComplete(geoLocation);
    }

    private void geocodingComplete(GeoLocation geoLocation) {
        cancelProgressDialog();
        if (geoLocation != null) {
            if (mSearchType >= 0) {
                findOrCreateNewRecent(geoLocation.getAddress(), geoLocation.getLatLng());
            }
            setActivityResult(geoLocation, false, null);
        } else {
            Toast.makeText(this, R.string.toast_no_result_found, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Finds a recent for History Searches and increases it's usage, or creates a new one if no one found
     *
     * @param query
     * @param latLng
     */
    private void findOrCreateNewRecent(String query, LatLng latLng) {
        RecentLocation location = RecentLocationDao.getInstance(SearchActivity.this).getItemByLatLng(latLng);
        if (location != null) {
            RecentLocationDao.getInstance(SearchActivity.this).updateUsage(location.getId());
        } else {
            RecentLocation recentLocation = new RecentLocation(mSearchType, query, latLng.latitude, latLng.longitude);
            RecentLocationDao.getInstance(SearchActivity.this).saveOrUpdate(recentLocation);
        }
    }

    /**
     * Finishes this current activity and sends the result to the one witch has started it
     */
    private void setActivityResult(GeoLocation geoLocation, boolean isFavorite, String favName) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_GEOLOCATION_EXTRA, geoLocation);
        intent.putExtra(RESULT_ISFAVORITE_EXTRA, isFavorite);
        intent.putExtra(RESULT_FAVORITE_NAME_EXTRA, favName);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    /**
     * Finishes this current activity and sends the result to the one witch has started it
     */
    private void setActivityResult(String address, LatLng location, boolean isFavorite, String favName) {
        GeoLocation geoLocation = new GeoLocation(address, location);
        setActivityResult(geoLocation, isFavorite, favName);
    }

    /**
     * Shows a progress dialog with specified text
     *
     * @param text
     */
    private void showProgressDialog(String text) {
        cancelProgressDialog();
        mDialog = new ProgressDialog(SearchActivity.this);
        mDialog.setTitle("");
        mDialog.setMessage(text);
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    /**
     * Cancels the current progress dialog if any
     */
    private void cancelProgressDialog() {
        if (mDialog != null) {
            mDialog.cancel();
            mDialog = null;
        }
    }

    @Override
    public void onHistoryItemSelected(int position) {
        if (position >= 0 && position < mRecentLocations.size()) {
            RecentLocationDao.getInstance(SearchActivity.this).updateUsage(mRecentLocations.get(position).getId());
            setActivityResult(mRecentLocations.get(position).getAddress(), mRecentLocations.get(position).getLatLng(), false, null);
        } else {
            Toast.makeText(this, R.string.search_activity_error_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFavoriteItemSelected(int position) {
        if (position >= 0 && position < mFavoriteLocations.size()) {
            FavoriteLocationDao.getInstance(SearchActivity.this).updateUsage(mFavoriteLocations.get(position).getId());
            setActivityResult(mFavoriteLocations.get(position).getAddress(), mFavoriteLocations.get(position).getLatLng(), true, mFavoriteLocations.get(position).getName());
        } else {
            Toast.makeText(this, R.string.search_activity_error_toast, Toast.LENGTH_SHORT).show();
        }
    }


}
