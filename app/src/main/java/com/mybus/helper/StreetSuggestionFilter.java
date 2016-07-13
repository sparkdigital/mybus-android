package com.mybus.helper;


import android.content.Context;
import android.widget.Filter;

import com.mybus.MyBus;
import com.mybus.R;
import com.mybus.dao.FavoriteLocationDao;
import com.mybus.listener.OnFindResultsListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.model.StreetSuggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

/**
 * Filter for searching streets given a constraint
 * <p/>
 * Created by ldimitroff on 01/06/16.
 */
public final class StreetSuggestionFilter extends Filter {

    private final String[] mStreetList;
    private final List<FavoriteLocation> mFavoriteList;
    private OnFindResultsListener mListener;

    public StreetSuggestionFilter(Context c, OnFindResultsListener listener) {
        this.mListener = listener;
        this.mStreetList = MyBus.getContext().getResources().getStringArray(R.array.streetList);
        this.mFavoriteList = FavoriteLocationDao.getInstance(c).getAll();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        TreeSet<StreetSuggestion> set = new TreeSet<>();

        if (constraint != null && constraint.length() >= 2) {
            for (FavoriteLocation favoriteLocation : mFavoriteList) {
                if (favoriteLocation.getName().toLowerCase(Locale.getDefault())
                        .contains(constraint.toString().toLowerCase(Locale.getDefault()))) {
                    set.add(new StreetSuggestion(favoriteLocation));
                }
            }
            for (String street : mStreetList) {
                if (street.toLowerCase(Locale.getDefault())
                        .contains(constraint.toString().toLowerCase(Locale.getDefault()))) {
                    set.add(new StreetSuggestion(street));
                }
            }
        }

        List<StreetSuggestion> suggestions = new ArrayList<>(set);

        // Assign the data to the FilterResults
        FilterResults filterResults = new FilterResults();
        filterResults.values = suggestions;
        filterResults.count = suggestions.size();
        return filterResults;
    }

    @Override
    public void publishResults(CharSequence constraint, FilterResults results) {
        if (mListener != null) {
            mListener.onResults((List<StreetSuggestion>) results.values);
        }
    }
}
