package com.mybus;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mybus.adapter.StreetAutoCompleteAdapter;
import com.mybus.listener.AppBarStateChangeListener;
import com.mybus.location.LocationGeocoding;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnAddressGeocodingCompleteCallback;
import com.mybus.location.OnLocationChangedCallback;
import com.mybus.location.OnLocationGeocodingCompleteCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, OnLocationChangedCallback
        , OnLocationGeocodingCompleteCallback, OnAddressGeocodingCompleteCallback {

    private GoogleMap mMap;
    private LocationUpdater mLocationUpdater;
    private Float DEFAULT_MAP_ZOOM;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.floating_action_button)
    FloatingActionButton mFAB;
    @Bind(R.id.center_location_action_button)
    FloatingActionButton mCLAB;
    @Bind(R.id.from_field)
    AppCompatAutoCompleteTextView mFromImput;
    @Bind(R.id.to_field)
    AppCompatAutoCompleteTextView mToImput;
    MarkerOptions mUserLocationMarkerOptions;
    //Marker used to update the location on the map
    Marker mUserLocationMarker;
    private SearchAddressEditor mOnEditorAndroidListener;

    /**
     * Checks the state of the AppBarLayout
     */
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            if (state.equals(State.COLLAPSED)) {
                mFAB.show();
                showSoftKeyBoard(false);
            }
        }
    };



    @OnClick(R.id.floating_action_button)
    public void onFABClick(View view) {
        mAppBarLayout.setExpanded(true, true);
        mFAB.hide();
        mFromImput.requestFocus();
        showSoftKeyBoard(true);
    }

    @OnClick(R.id.center_location_action_button)
    public void mCLABClickListener(View view) {
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

        mFromImput.setAdapter(autoCompleteAdapter);
        mToImput.setAdapter(autoCompleteAdapter);

        mOnEditorAndroidListener = new SearchAddressEditor(this,this);
        mToImput.setOnEditorActionListener(mOnEditorAndroidListener);
        mLocationUpdater = new LocationUpdater(this, this);
        DEFAULT_MAP_ZOOM = new Float(getResources().getInteger(R.integer.default_map_zoom));
        mUserLocationMarkerOptions = new MarkerOptions().title(getString(R.string.current_location_marker));

        LocationGeocoding.setContext(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocationUpdater.startListening();
        centerToLastKnownLocation();
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
            else{
                mUserLocationMarker.setPosition(lastLocation);
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, DEFAULT_MAP_ZOOM));
            LocationGeocoding.getInstance().performGeocodeByLocation(lastLocation, this);
        }
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        if (mUserLocationMarker != null) {
            mUserLocationMarker.setPosition(latLng);
        }
    }

    private void showSoftKeyBoard(boolean show) {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            if (show) {
                imm.showSoftInput(mFromImput, InputMethodManager.SHOW_IMPLICIT);
            } else {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    @Override
    public void onAddressGeocodingComplete(LatLng location) {
        if(location!=null){
            mUserLocationMarkerOptions.position(location);
            //if the marker is not on the map, add it
            if (mUserLocationMarker == null) {
                mUserLocationMarker = mMap.addMarker(mUserLocationMarkerOptions);
            }
            else{
                mUserLocationMarker.setPosition(location);
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_MAP_ZOOM));
        }
    }

    @Override
    public void onLocationGeocodingComplete(String address) {
        Toast.makeText(this,address,Toast.LENGTH_LONG).show();
    }
}
