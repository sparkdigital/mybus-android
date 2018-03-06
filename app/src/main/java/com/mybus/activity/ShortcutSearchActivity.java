package com.mybus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.R;
import com.mybus.dao.RecentLocationDao;
import com.mybus.location.LocationUpdater;
import com.mybus.location.OnLocationGeocodingCompleteCallback;
import com.mybus.model.GeoLocation;
import com.mybus.model.RecentLocation;
import com.mybus.model.SearchType;
import com.mybus.service.ServiceFacade;

/**
 * Created by ldimitroff on 05/03/18.
 */

public class ShortcutSearchActivity extends AppCompatActivity implements OnLocationGeocodingCompleteCallback {

    public static final String TO_GEOLOCATION_ID = "TO_GEOLOCATION_ID";
    public static final String RESULT_GEOLOCATION_ORIGIN = "RESULT_GEOLOCATION_ORIGIN";
    public static final String RESULT_GEOLOCATION_DESTINATION = "RESULT_GEOLOCATION_DESTINATION";
    private ProgressDialog mDialog;
    private GeoLocation mOrigin;
    private GeoLocation mDestination;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            setResultActivityCancel();
            return;
        }

        long recentLocationId = extras.getLong(TO_GEOLOCATION_ID);

        RecentLocation location = RecentLocationDao.getInstance(ShortcutSearchActivity.this).getById(recentLocationId);
        mDestination = new GeoLocation(location.getAddress(), location.getLatLng());
        RecentLocationDao.findOrCreateNewRecent(mDestination.getAddress(), mDestination.getLatLng(), SearchType.DESTINATION, ShortcutSearchActivity.this);


        LocationUpdater locationUpdater = new LocationUpdater(null, ShortcutSearchActivity.this);
        LatLng knownLocation = locationUpdater.getLastKnownLocation();
        if (knownLocation != null) {
            showProgressDialog(getString(R.string.toast_searching_address));
            ServiceFacade.getInstance().performGeocodeByLocation(knownLocation, ShortcutSearchActivity.this, ShortcutSearchActivity.this);
        } else {
            Toast.makeText(this, R.string.cant_find_current_location, Toast.LENGTH_SHORT).show();
            setResultActivityCancel();
        }
    }

    private void showProgressDialog(String text) {
        cancelProgressDialog();
        mDialog = new ProgressDialog(ShortcutSearchActivity.this);
        mDialog.setTitle("");
        mDialog.setMessage(text);
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void cancelProgressDialog() {
        if (mDialog != null) {
            mDialog.cancel();
            mDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void setResultActivityCancel() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        overridePendingTransition(0, 0);
        finish();

    }

    @Override
    public void onLocationGeocodingComplete(GeoLocation origin) {
        cancelProgressDialog();
        if (origin != null) {
            mOrigin = origin;

            setResultActivityOK();

        } else {
            setResultActivityCancel();
        }
    }

    private void setResultActivityOK() {
        Intent intent = new Intent();
        intent.putExtra(RESULT_GEOLOCATION_ORIGIN, mOrigin);
        intent.putExtra(RESULT_GEOLOCATION_DESTINATION, mDestination);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
