package com.mybus;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.mybus.location.LocationGeocoding;
import com.mybus.location.OnAddressGeocodingCompleteCallback;

public class SearchAddressEditor implements TextView.OnEditorActionListener {

    private Context context;
    private OnAddressGeocodingCompleteCallback onAddressGeocodingCompleteCallback;

    public SearchAddressEditor(Context context, OnAddressGeocodingCompleteCallback callback) {
        this.context = context;
        this.onAddressGeocodingCompleteCallback = callback;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String address = v.getText().toString();
            LocationGeocoding.getInstance().performGeocodeByAddress(address, onAddressGeocodingCompleteCallback);
            return true;
        }
        return false;
    }
}