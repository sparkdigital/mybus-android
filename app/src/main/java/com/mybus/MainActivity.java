package com.mybus;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
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
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Mar Del Plata and move the camera
        LatLng latLng = new LatLng(-37.979858, -57.589794);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Mar Del Plata"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }
}
