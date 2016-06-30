package com.mybus.helper;


import android.widget.Filter;

import com.mybus.MyBus;
import com.mybus.R;
import com.mybus.listener.OnFindResultsListener;
import com.mybus.model.StreetSuggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter for searching streets given a constraint
 * <p/>
 * Created by ldimitroff on 01/06/16.
 */
public class StreetSuggestionFilter extends Filter {

    private static StreetSuggestionFilter instance;
    private final String[] mStreetList;
    private OnFindResultsListener mListener;

    /**
     * @param listener
     * @return
     */
    public static StreetSuggestionFilter instance(OnFindResultsListener listener){
        if (instance == null){
            instance = new StreetSuggestionFilter(listener);
        }
        return instance;

    }

    private StreetSuggestionFilter(OnFindResultsListener listener) {
        this.mListener = listener;
        this.mStreetList = MyBus.getContext().getResources().getStringArray(R.array.streetList);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        List<StreetSuggestion> suggestionList = new ArrayList<>();
        if (constraint != null && constraint.length() >= 2) {
            for (String street : mStreetList) {
                if (street.toLowerCase().contains(constraint.toString().toLowerCase())) {
                    suggestionList.add(new StreetSuggestion(street));
                }
            }
        }
        // Assign the data to the FilterResults
        FilterResults filterResults = new FilterResults();
        filterResults.values = suggestionList;
        filterResults.count = suggestionList.size();
        return filterResults;
    }

    @Override
    public void publishResults(CharSequence constraint, FilterResults results) {
        if (mListener != null) {
            mListener.onResults((List<StreetSuggestion>) results.values);
        }
    }
}
