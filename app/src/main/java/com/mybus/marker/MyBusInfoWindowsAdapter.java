package com.mybus.marker;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mybus.R;
import com.mybus.activity.MainActivity;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * Custom infoWindows for markers
 */
public class MyBusInfoWindowsAdapter implements GoogleMap.InfoWindowAdapter {
    private final MainActivity mActivity;
    private final View mContentsView;

    public MyBusInfoWindowsAdapter(MainActivity activity) {
        mActivity = activity;
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        mContentsView = inflater.inflate(R.layout.marker_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView tvTitle = ((TextView) mContentsView.findViewById(R.id.marker_title));
        tvTitle.setText(marker.getTitle());
        TextView tvAddress = ((TextView) mContentsView.findViewById(R.id.marker_address));
        tvAddress.setText(marker.getSnippet());
        ImageView ivFavIcon = ((ImageView) mContentsView.findViewById(R.id.marker_fav_icon));
        //Checks if the marker is a StartLocation, EndLocation or other
        MyBusMarker myBusMarker = mActivity.isMarkerPresent(marker);
        if (myBusMarker == null) {
            //User location or Bus stop markers
            ivFavIcon.setVisibility(View.GONE);
        } else {
            ivFavIcon.setVisibility(View.VISIBLE);
            if (myBusMarker.isFavorite()) {
                //Change title with favorite name
                tvTitle.setText(myBusMarker.getFavoriteName());
                //Put remove favorite icon
                ivFavIcon.setImageResource(R.drawable.favorite_remove_icon);
            } else {
                ivFavIcon.setImageResource(R.drawable.favorite_add_icon);
            }
        }
        return mContentsView;
    }
}
