package com.mybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.mybus.R;
import com.mybus.helper.SearchSuggestionsHelper;
import com.mybus.listener.OnFindResultsListener;
import com.mybus.model.StreetSuggestion;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    public static final String SEARCH_TITLE = "SEARCH_TITLE";

    @Bind(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @Bind(R.id.searchContent)
    LinearLayout mSearchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra(SEARCH_TITLE) != null) {
            mSearchView.setSearchHint(getIntent().getStringExtra(SEARCH_TITLE));
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
                Intent intent = new Intent();
                intent.putExtra("TEXT", searchSuggestion.getBody());
                setResult(RESULT_OK, intent);
                overridePendingTransition(0, 0);
                finish();
            }

            @Override
            public void onSearchAction() {
                Log.d(TAG, "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                //show suggestions when search bar gains focus (typically history suggestions)
                mSearchView.swapSuggestions(SearchSuggestionsHelper.getHistory(3));
                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {
                Log.d(TAG, "onFocusCleared()");
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
}
