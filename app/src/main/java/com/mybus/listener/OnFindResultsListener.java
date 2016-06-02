package com.mybus.listener;

import com.mybus.model.StreetSuggestion;

import java.util.List;

public interface OnFindResultsListener {

    void onResults(List<StreetSuggestion> results);
}