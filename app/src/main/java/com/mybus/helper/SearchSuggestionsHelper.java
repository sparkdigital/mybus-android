package com.mybus.helper;

import com.mybus.listener.OnFindResultsListener;

/**
 * Helper for SearchSuggestions in autocomplete
 */
public final class SearchSuggestionsHelper {

    private SearchSuggestionsHelper() {
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
