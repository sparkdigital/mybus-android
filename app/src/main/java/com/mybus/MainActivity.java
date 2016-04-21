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
import com.mybus.adapter.CustomInfoWindowAdapter;
import com.mybus.adapter.StreetAutoCompleteAdapter;
import com.mybus.helper.SearchFormStatus;
import com.mybus.listener.AppBarStateChangeListener;
import com.mybus.listener.CustomAutoCompleteClickListener;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnLocationChangedCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, OnLocationChangedCallback {

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
    Marker mStartLocationMarker = null;
    //Marker used to show the End Location
    Marker mEndLocationMarker = null;
    //Temporary Marker
    private Marker mTempMarker;
    //Keeps the state of the app bar
    private AppBarStateChangeListener.State mAppBarState;


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
            if ((tv.getId() == mFromInput.getId()) && actionId == EditorInfo.IME_ACTION_NEXT) {
                //TODO: Put Marker FROM
                mToInput.requestFocus();
                return true;
            }
            if ((tv.getId() == mToInput.getId()) && actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO: Put Marker TO
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
                mTempMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_origen)));
                mTempMarker.showInfoWindow();
            } else if (!SearchFormStatus.getInstance().isDestinationFilled()) {
                mTempMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_destino)));
                mTempMarker.showInfoWindow();
            }
        }
    };

    /**
     * Listener for PopUp Window of Markers
     */
    private GoogleMap.OnInfoWindowClickListener mOnInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            if (mUserLocationMarker != null && !marker.getId().equals(mUserLocationMarker.getId())) {
                marker.remove();
                clearTempMarker();
            }
            marker.hideInfoWindow();
            if (!SearchFormStatus.getInstance().isStartFilled()) {
                //TODO: Change to Geocoding
                mStartLocationMarker = mMap.addMarker(new MarkerOptions()
                        .position(marker.getPosition())
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_origen))
                        .title(marker.getPosition().toString()));
                mFromInput.setText(marker.getPosition().toString());
                SearchFormStatus.getInstance().setStartFilled(true);
                SearchFormStatus.getInstance().setStartMarkerId(mStartLocationMarker.getId());
            } else if (!SearchFormStatus.getInstance().isDestinationFilled()) {
                mEndLocationMarker = mMap.addMarker(new MarkerOptions()
                        .position(marker.getPosition())
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_destino))
                        .title(marker.getPosition().toString()));
                //TODO: Change to Geocoding
                mToInput.setText(marker.getPosition().toString());
                SearchFormStatus.getInstance().setDestinationFilled(true);
            }
        }
    };

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
        }

        @Override
        public void onMarkerDrag(Marker marker) {
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            if (marker.getId().equals(mStartLocationMarker.getId())) {
                mFromInput.setText(marker.getPosition().toString());
            } else if (marker.getId().equals(mEndLocationMarker.getId())) {
                mToInput.setText(marker.getPosition().toString());
            }
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

        resetLocalVariables();
    }

    /**
     * This method restart the local variables to avoid old apps's states
     */
    private void resetLocalVariables() {
        SearchFormStatus.getInstance().clearFormStatus();
        if (mStartLocationMarker != null) {
            mStartLocationMarker = null;
        }
        if (mEndLocationMarker != null) {
            mEndLocationMarker = null;
        }
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
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getLayoutInflater(), MainActivity.this));
        mMap.setOnInfoWindowClickListener(mOnInfoWindowClickListener);
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocationUpdater.getLastKnownLocation(), DEFAULT_MAP_ZOOM));
        }
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
}
