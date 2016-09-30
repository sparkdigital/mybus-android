package com.mybus.helper;


import android.content.Context;
import android.widget.Filter;

import com.mybus.MyBus;
import com.mybus.R;
import com.mybus.asynctask.TouristicPlacesRequestCallback;
import com.mybus.dao.FavoriteLocationDao;
import com.mybus.listener.OnFindResultsListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.model.StreetSuggestion;
import com.mybus.model.TouristicPlace;
import com.mybus.service.ServiceFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

/**
 * Filter for searching streets given a constraint
 * <p/>
 * Created by ldimitroff on 01/06/16.
 */
public final class StreetSuggestionFilter extends Filter implements TouristicPlacesRequestCallback {

    private final String[] mStreetList;
    private final List<FavoriteLocation> mFavoriteList;
    private OnFindResultsListener mListener;
    private List<TouristicPlace> mTouristicPlaces;

    public StreetSuggestionFilter(Context c, OnFindResultsListener listener) {
        this.mListener = listener;
        this.mStreetList = MyBus.getContext().getResources().getStringArray(R.array.streetList);
        this.mFavoriteList = FavoriteLocationDao.getInstance(c).getAll();
        ServiceFacade.getInstance().getTouristicPlaces(c, this);
    }

    @Override
    public void onPlacesFound(List<TouristicPlace> places) {
        this.mTouristicPlaces = places;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        TreeSet<StreetSuggestion> set = new TreeSet<>();

        if (constraint != null && constraint.length() >= 2) {
            String query = constraint.toString();

            set.addAll(getTouristicPlacesSuggestion(query));

            set.addAll(getFavoritesSuggestion(query));

            set.addAll(getStreetSuggestion(query));

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

    private List<StreetSuggestion> getTouristicPlacesSuggestion(String constraint) {
        List<StreetSuggestion> list = new ArrayList<>();
        if (mTouristicPlaces != null) {
            for (TouristicPlace place : mTouristicPlaces) {
                if (place.getName().toLowerCase(Locale.getDefault())
                        .contains(constraint.toLowerCase(Locale.getDefault()))) {
                    list.add(new StreetSuggestion(place));
                }
            }
        }
        return list;
    }

    private List<StreetSuggestion> getFavoritesSuggestion(String constraint) {
        List<StreetSuggestion> list = new ArrayList<>();
        if (mFavoriteList != null) {
            for (FavoriteLocation favoriteLocation : mFavoriteList) {
                if (favoriteLocation.getName().toLowerCase(Locale.getDefault())
                        .contains(constraint.toLowerCase(Locale.getDefault()))) {
                    list.add(new StreetSuggestion(favoriteLocation));
                }
            }
        }
        return list;
    }

    private List<StreetSuggestion> getStreetSuggestion(String constraint) {
        List<StreetSuggestion> list = new ArrayList<>();
        if (mStreetList != null) {
            for (String street : mStreetList) {
                if (street.toLowerCase(Locale.getDefault())
                        .contains(constraint.toLowerCase(Locale.getDefault()))) {
                    list.add(new StreetSuggestion(street));
                }
            }
        }
        return list;
    }
}
