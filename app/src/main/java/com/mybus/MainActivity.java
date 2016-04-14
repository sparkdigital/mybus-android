package com.mybus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mybus.listener.AppBarStateChangeListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationUpdater locationUpdater;
    private String CURRENT_LOCATION_MARKER;
    private Float DEFAULT_MAP_ZOOM;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.floating_action_button)
    FloatingActionButton mFAB;
    @Bind(R.id.from_field)
    AppCompatAutoCompleteTextView mFromImput;
    @Bind(R.id.to_field)
    AppCompatAutoCompleteTextView mToImput;

    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            if (state.equals(State.COLLAPSED)) {
                mFAB.show();
            }
        }
    };

    @OnClick(R.id.floating_action_button)
    public void mFABClickListener(View view) {
        mAppBarLayout.setExpanded(true, true);
        mFAB.hide();
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
        locationUpdater = new LocationUpdater(this);
        CURRENT_LOCATION_MARKER = getString(R.string.current_location_marker);
        DEFAULT_MAP_ZOOM = new Float(getResources().getInteger(R.integer.default_map_zoom));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startLocationListening();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_MAP_ZOOM));
    }

    public void setCurrentLocation(double lat, double lon) {
        mMap.clear();
        float currentZoom = mMap.getCameraPosition().zoom;
        LatLng latLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(latLng).title(CURRENT_LOCATION_MARKER));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, currentZoom));
    }

    public void startLocationListening() {
        // Define a listener that responds to location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationUpdater);
    }
}
