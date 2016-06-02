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
import com.google.android.gms.maps.model.LatLng;
import com.mybus.R;
import com.mybus.helper.SearchSuggestionsHelper;
import com.mybus.listener.OnFindResultsListener;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.model.StreetSuggestion;
import com.mybus.service.ServiceFacade;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class SearchActivity extends AppCompatActivity implements OnAddressGeocodingCompleteCallback {

    public static final String SEARCH_TITLE_EXTRA = "SEARCH_TITLE_EXTRA";
    public static final String RESULT_STREET_EXTRA = "RESULT_STREET_EXTRA";
    public static final String RESULT_LATLNG_EXTRA = "RESULT_LATLNG_EXTRA";

    private static final String TAG = SearchActivity.class.getSimpleName();

    @Bind(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @Bind(R.id.searchContent)
    LinearLayout mSearchContent;
    private String mCurrentQuery;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mCurrentQuery = "";

        if (getIntent().getStringExtra(SEARCH_TITLE_EXTRA) != null) {
            mSearchView.setSearchHint(getIntent().getStringExtra(SEARCH_TITLE_EXTRA));
        }

        initSearchView();
        Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        mSearchContent.startAnimation(bottomUp);
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
    public void onAddressGeocodingComplete(LatLng location) {
        cancelProgressDialog();
        if (mCurrentQuery == null || location == null) {
            Toast.makeText(this, R.string.toast_no_result_found, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(RESULT_STREET_EXTRA, mCurrentQuery);
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
        mDialog = ProgressDialog.show(SearchActivity.this, "", text, true, false);
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
}
