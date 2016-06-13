package com.mybus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.google.android.gms.maps.model.LatLng;
import com.mybus.R;
import com.mybus.dao.RecentLocationDao;
import com.mybus.helper.SearchSuggestionsHelper;
import com.mybus.listener.FavoriteItemSelectedListener;
import com.mybus.listener.HistoryItemSelectedListener;
import com.mybus.listener.OnFindResultsListener;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.model.RecentLocation;
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
public class SearchActivity extends AppCompatActivity implements OnAddressGeocodingCompleteCallback, HistoryItemSelectedListener, FavoriteItemSelectedListener, OnLocationGeocodingCompleteCallback {

    public static final String SEARCH_TITLE_EXTRA = "SEARCH_TITLE_EXTRA";
    public static final String SEARCH_TYPE_EXTRA = "SEARCH_TYPE_EXTRA";

    public static final String RESULT_STREET_EXTRA = "RESULT_STREET_EXTRA";
    public static final String RESULT_LATLNG_EXTRA = "RESULT_LATLNG_EXTRA";


    private static final String TAG = SearchActivity.class.getSimpleName();

    @Bind(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @Bind(R.id.searchContent)
    LinearLayout mSearchContent;
    @Bind(R.id.card_view_recent)
    HistoryCardView mHistoryCardView;
    @Bind(R.id.card_view_favorites)
    FavoritesCardView mFavoriteCardView;
    private String mCurrentQuery;
    private ProgressDialog mDialog;
    private LatLng mLastLocation;
    private int mSearchType;
    private List<RecentLocation> mRecentLocations;

    @OnClick(R.id.currentLocationCard)
    public void onCurrentLocationCardClick() {
        LocationUpdater locationUpdater = new LocationUpdater(null, this);
        mLastLocation = locationUpdater.getLastKnownLocation();
        if (mLastLocation != null) {
            showProgressDialog(getString(R.string.toast_searching_address));
            ServiceFacade.getInstance().performGeocodeByLocation(mLastLocation, this, this);
        } else {
            Toast.makeText(this, R.string.cant_find_current_location, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mCurrentQuery = "";
        mLastLocation = null;

        mSearchType = getIntent().getIntExtra(SEARCH_TYPE_EXTRA, -1);

        if (getIntent().getStringExtra(SEARCH_TITLE_EXTRA) != null) {
            mSearchView.setSearchHint(getIntent().getStringExtra(SEARCH_TITLE_EXTRA));
        }

        initSearchView();
        Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        mSearchContent.startAnimation(bottomUp);

        initHistoryCardView();
        initFavoriteCardView();
    }

    private void initHistoryCardView() {
        mRecentLocations = RecentLocationDao.getInstance(this).getAllByField("type", mSearchType);
        //Sorting recent locations
        Collections.sort(mRecentLocations, Collections.<RecentLocation>reverseOrder());
        mHistoryCardView.setList(mRecentLocations);
        mHistoryCardView.setHistoryItemSelectedListener(this);
    }

    private void initFavoriteCardView() {
        mFavoriteCardView.setFavoriteItemSelectedListener(this);
    }

    private void initSearchView() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    SearchSuggestionsHelper.findStreets(newQuery, new OnFindResultsListener() {

                        @Override
                        public void onResults(List<StreetSuggestion> results) {

                            //this will swap the data and
                            //render the collapse/expand animations as necessary
                            mSearchView.swapSuggestions(results);

                            //let the users know that the background
                            //process has completed
                            mSearchView.hideProgress();
                        }
                    });
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Log.d(TAG, "onSuggestionClicked()");
                mSearchView.setSearchTextFocused(searchSuggestion.getBody());
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
                mCurrentQuery = currentQuery;
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
    public void onLocationGeocodingComplete(String address) {
        geocodingComplete(address, mLastLocation);
    }

    @Override
    public void onAddressGeocodingComplete(LatLng location) {
        geocodingComplete(mCurrentQuery, location);
    }

    private void geocodingComplete(String query, LatLng location) {
        cancelProgressDialog();
        if (query != null && location != null) {
            if (mSearchType >= 0) {
                RecentLocation recentLocation = new RecentLocation(mSearchType, query, location.latitude, location.longitude);
                RecentLocationDao.getInstance(SearchActivity.this).saveOrUpdate(recentLocation);
            }
            setActivityResult(query, location);
        } else {
            Toast.makeText(this, R.string.toast_no_result_found, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Finishes this current activity and sends the result to the one witch has started it
     */
    private void setActivityResult(String query, LatLng location) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_STREET_EXTRA, query);
        intent.putExtra(RESULT_LATLNG_EXTRA, location);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
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
            RecentLocationDao.getInstance(SearchActivity.this).updateItemUsageCount(mRecentLocations.get(position).getId());
            setActivityResult(mRecentLocations.get(position).getAddress(), mRecentLocations.get(position).getLatLng());
        } else {
            Toast.makeText(this, R.string.search_activity_error_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFavoriteItemSelected(String result) {
        //TODO: Result should contain a valid LatLng and return it on the intent
        showProgressDialog(getString(R.string.toast_searching_address));
        mCurrentQuery = result;
        ServiceFacade.getInstance().performGeocodeByAddress(result, SearchActivity.this, SearchActivity.this);
    }


}
