package com.mybus.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.mybus.R;
import com.mybus.helper.ColorSuggestion;
import com.mybus.helper.DataHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class SearchActivity extends AppCompatActivity {

    public static final String TAG = "SearchActivity";

    @Bind(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initSearchView();
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
                    DataHelper.find(SearchActivity.this, newQuery, new DataHelper.OnFindResultsListener() {

                        @Override
                        public void onResults(List<ColorSuggestion> results) {

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
                mSearchView.swapSuggestions(DataHelper.getHistory(SearchActivity.this, 3));
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
        //Replacing the transaction between the activities
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}
