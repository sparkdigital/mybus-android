package com.mybus.helper;

import com.mybus.listener.OnFindResultsListener;
import com.mybus.model.StreetSuggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper for SearchSuggestions in autocomplete
 */
public class SearchSuggestionsHelper {

    /**
     * @param count
     * @return
     */
    public static List<StreetSuggestion> getHistory(int count) {
        //TODO: Use history from DB
        List<StreetSuggestion> suggestionList = new ArrayList<>();
        suggestionList.add(new StreetSuggestion("Luro"));
        suggestionList.add(new StreetSuggestion("Independencia"));
        suggestionList.add(new StreetSuggestion("Colon"));
        suggestionList.add(new StreetSuggestion("Libertad"));
        suggestionList.add(new StreetSuggestion("Champagnat"));
        suggestionList.add(new StreetSuggestion("JB Justo"));
        suggestionList.add(new StreetSuggestion("Peralta Ramos"));

        if (suggestionList.size() > count) {
            return suggestionList.subList(0, count);
        } else {
            return suggestionList;
        }
    }

    /**
     * Finds streets suggestions given the constraint
     *
     * @param constraint
     * @param listener
     */
    public static void findStreets(String constraint, final OnFindResultsListener listener) {
        new StreetSuggestionFilter(listener).filter(constraint);
    }


}