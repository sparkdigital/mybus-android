package com.mybus;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mybus.adapter.StreetAutoCompleteAdapter;
import com.mybus.helper.SearchFormStatus;
import com.mybus.listener.AppBarStateChangeListener;
import com.mybus.listener.CustomAutoCompleteClickListener;
import com.mybus.location.LocationGeocoding;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationChangedCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, OnLocationChangedCallback,
        OnAddressGeocodingCompleteCallback, OnLocationGeocodingCompleteCallback {

    private GoogleMap mMap;
    private LocationUpdater mLocationUpdater;
    private Float DEFAULT_MAP_ZOOM;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.floating_action_button)
    FloatingActionButton mFloatingSearchButton;
    @Bind(R.id.center_location_action_button)
    FloatingActionButton mCenterLocationButton;
    @Bind(R.id.from_field)
    AppCompatAutoCompleteTextView mFromInput;
    @Bind(R.id.to_field)
    AppCompatAutoCompleteTextView mToInput;
    MarkerOptions mUserLocationMarkerOptions;
    //Marker used to update the location on the map
    Marker mUserLocationMarker;
    //Marker used to show the Start Location
    Marker mStartLocationMarker;
    //Marker used to show the End Location
    Marker mEndLocationMarker;
    //Temporary Marker
    private Marker mTempMarker;
    MarkerOptions mStartLocationMarkerOptions;
    MarkerOptions lastAddressGeocodingType;
    MarkerOptions lastLocationGeocodingType;

    MarkerOptions mEndLocationMarkerOptions;
    //Keeps the state of the app bar
    private AppBarStateChangeListener.State mAppBarState;
    OnAddressGeocodingCompleteCallback mOnAddressGeocodingCompleteCallback;
    OnLocationGeocodingCompleteCallback mOnLocationGeocodingCompleteCallback;

    LocationGeocoding locationGeocoding;

    /**
     * Checks the state of the AppBarLayout
     */
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            mAppBarState = state;
            if (state.equals(State.COLLAPSED)) {
                showSoftKeyBoard(false);
            }
        }
    };

    /**
     * Listener for To_TextView, makes the search when the user hits the magnifying glass
     */
    private TextView.OnEditorActionListener mOnEditorAndroidListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
            String address = tv.getText().toString();
            if ((tv.getId() == mFromInput.getId()) && actionId == EditorInfo.IME_ACTION_NEXT) {
                //TODO: Put Marker FROM
                setMarkerTitle(mStartLocationMarker, mStartLocationMarkerOptions, address);
                lastAddressGeocodingType = mStartLocationMarkerOptions;
                locationGeocoding.performGeocodeByAddress(address, mOnAddressGeocodingCompleteCallback);
                mToInput.requestFocus();
                return true;
            }
            if ((tv.getId() == mToInput.getId()) && actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO: Put Marker TO
                setMarkerTitle(mEndLocationMarker, mEndLocationMarkerOptions, address);
                lastAddressGeocodingType = mEndLocationMarkerOptions;
                locationGeocoding.performGeocodeByAddress(address, mOnAddressGeocodingCompleteCallback);
                //TODO: Perform Search
                showSoftKeyBoard(false);
                return true;
            }
            return false;
        }
    };

    /**
     * Listener for Map Long Click Listener for setting start or end locations.
     */
    private GoogleMap.OnMapLongClickListener mMapOnLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            //TODO: Add marker
            if (mTempMarker != null) {
                clearTempMarker();
            }
            if (!SearchFormStatus.getInstance().isStartFilled()) {
                lastLocationGeocodingType = mStartLocationMarkerOptions;
                mStartLocationMarker = positionMarker(mStartLocationMarker, mStartLocationMarkerOptions, latLng, true);
            } else if (!SearchFormStatus.getInstance().isDestinationFilled()) {
                lastLocationGeocodingType = mEndLocationMarkerOptions;
                mEndLocationMarker = positionMarker(mEndLocationMarker, mEndLocationMarkerOptions, latLng, true);
            }
        }
    };


    public Marker positionMarker(Marker marker, MarkerOptions markerOptions, LatLng latLng, boolean performGeocoding) {
        if (marker == null) {
            markerOptions.position(latLng);
            marker = mMap.addMarker(markerOptions);
        } else {
            marker.setPosition(latLng);
        }
        if (performGeocoding) {
            locationGeocoding.performGeocodeByLocation(latLng, mOnLocationGeocodingCompleteCallback);
        }
        return marker;
    }

    /**
     * Listener for Map Clicks and removes the Temporary Marker
     */
    private GoogleMap.OnMapClickListener mOnMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            clearTempMarker();
        }
    };

    /**
     * Listener for Marker Clicks and removes the Temporary Marker
     */
    private GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            clearTempMarker();
            return false;
        }
    };

    /**
     * Listener for the marker drag
     */
    private GoogleMap.OnMarkerDragListener mOnMarkerDragListener = new GoogleMap.OnMarkerDragListener() {

        @Override
        public void onMarkerDragStart(Marker marker) {
            marker.hideInfoWindow();
        }

        @Override
        public void onMarkerDrag(Marker marker) {
            marker.hideInfoWindow();
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            marker.hideInfoWindow();
            if (marker.getId().equals(mStartLocationMarker.getId())) {
                lastLocationGeocodingType = mStartLocationMarkerOptions;
            } else if (marker.getId().equals(mEndLocationMarker.getId())) {
                lastLocationGeocodingType = mEndLocationMarkerOptions;
            }
            locationGeocoding.performGeocodeByLocation(marker.getPosition(), mOnLocationGeocodingCompleteCallback);
        }
    };

    @OnClick(R.id.floating_action_button)
    public void onFloatingSearchButton(View view) {
        if (SearchFormStatus.getInstance().canMakeSearch()) {
            //TODO: Perform Search
            return;
        }
        mAppBarLayout.setExpanded(true, true);
        mFromInput.requestFocus();
        showSoftKeyBoard(true);
    }

    @OnClick(R.id.center_location_action_button)
    public void onCenterLocationButtonClick(View view) {
        centerToLastKnownLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mAppBarLayout.addOnOffsetChangedListener(mOnOffsetChangedListener);

        StreetAutoCompleteAdapter autoCompleteAdapter = new StreetAutoCompleteAdapter(MainActivity.this);

        mFromInput.setAdapter(autoCompleteAdapter);
        mFromInput.setOnEditorActionListener(mOnEditorAndroidListener);
        mFromInput.setOnItemClickListener(new CustomAutoCompleteClickListener(mFromInput));

        mToInput.setAdapter(autoCompleteAdapter);
        mToInput.setOnItemClickListener(new CustomAutoCompleteClickListener(mToInput));
        mToInput.setOnEditorActionListener(mOnEditorAndroidListener);

        mLocationUpdater = new LocationUpdater(this, this);
        DEFAULT_MAP_ZOOM = new Float(getResources().getInteger(R.integer.default_map_zoom));
        mUserLocationMarkerOptions = new MarkerOptions()
                .title(getString(R.string.current_location_marker))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot));
        mOnAddressGeocodingCompleteCallback = this;
        mOnLocationGeocodingCompleteCallback = this;
        locationGeocoding = new LocationGeocoding(this);


        resetLocalVariables();
    }

    /**
     * This method restart the local variables to avoid old apps's states
     */
    private void resetLocalVariables() {
        SearchFormStatus.getInstance().clearFormStatus();
        mUserLocationMarkerOptions = null;
        mUserLocationMarker = null;
        mStartLocationMarker = null;
        mEndLocationMarker = null;
        mTempMarker = null;
        lastAddressGeocodingType = null;
        lastLocationGeocodingType = null;
        mStartLocationMarkerOptions = new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_origen))
                .title("origen");
        mEndLocationMarkerOptions = new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_destino))
                .title("destino");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mUserLocationMarkerOptions = new MarkerOptions()
                .title(getString(R.string.current_location_marker))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot));
        mMap = googleMap;
        mLocationUpdater.startListening();
        centerToLastKnownLocation();

        mMap.setOnMapLongClickListener(mMapOnLongClickListener);
        //mMap.setOnInfoWindowClickListener(mOnInfoWindowClickListener);
        mMap.setOnMapClickListener(mOnMapClickListener);
        mMap.setOnMarkerClickListener(mOnMarkerClickListener);
        mMap.setOnMarkerDragListener(mOnMarkerDragListener);
    }

    public void centerToLastKnownLocation() {
        //get the last gps location
        LatLng lastLocation = mLocationUpdater.getLastKnownLocation();
        if (lastLocation != null) {
            mUserLocationMarkerOptions.position(lastLocation);
            //if the marker is not on the map, add it
            if (mUserLocationMarker == null) {
                mUserLocationMarker = mMap.addMarker(mUserLocationMarkerOptions);
            }
            zoomTo(mLocationUpdater.getLastKnownLocation());
        }
    }

    private void zoomTo(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM));
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        if (mUserLocationMarker != null) {
            mUserLocationMarker.setPosition(latLng);
        }
    }

    /**
     * Show or hide the soft keyboard
     *
     * @param show
     */
    private void showSoftKeyBoard(boolean show) {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            if (show) {
                imm.showSoftInput(mFromInput, InputMethodManager.SHOW_IMPLICIT);
            } else {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * Clears the Temporary Marker from the map
     */
    private void clearTempMarker() {
        if (mTempMarker != null) {
            mTempMarker.remove();
            mTempMarker = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (mAppBarState != null && mAppBarState.equals(AppBarStateChangeListener.State.EXPANDED)) {
            mAppBarLayout.setExpanded(false, true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onAddressGeocodingComplete(LatLng location) {
        if (location != null) {
            if (lastAddressGeocodingType == mStartLocationMarkerOptions) {
                mStartLocationMarker = positionMarker(mStartLocationMarker, mStartLocationMarkerOptions, location, false);
            }
            if (lastAddressGeocodingType == mEndLocationMarkerOptions) {
                mEndLocationMarker = positionMarker(mEndLocationMarker, mEndLocationMarkerOptions, location, false);
            }
            zoomTo(location);
        }
    }

    private void setMarkerTitle(Marker marker, MarkerOptions markerOptions, String title) {
        if (marker != null) {
            marker.setTitle(title);
            marker.showInfoWindow();
        }
        markerOptions.title(title);
    }

    @Override
    public void onLocationGeocodingComplete(String address) {
        if (address != null) {
            if (lastLocationGeocodingType == mStartLocationMarkerOptions) {
                setMarkerTitle(mStartLocationMarker, mStartLocationMarkerOptions, address);
                mFromInput.setText(address);
                SearchFormStatus.getInstance().setStartFilled(true);
                SearchFormStatus.getInstance().setStartMarkerId(mStartLocationMarker.getId());
            }
            if (lastLocationGeocodingType == mEndLocationMarkerOptions) {
                setMarkerTitle(mEndLocationMarker, mEndLocationMarkerOptions, address);
                mToInput.setText(address);
                SearchFormStatus.getInstance().setDestinationFilled(true);
            }
        }
    }
}