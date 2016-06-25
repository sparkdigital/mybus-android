package com.mybus.marker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class MyBusInfoWindowsAdapter implements GoogleMap.InfoWindowAdapter {
    private final Context mContext;
    private final View mContentsView;

    public MyBusInfoWindowsAdapter(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
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
        MyBusMarker myBusMarker = MarkerStorage.getInstance().isMarkerPresent(marker);
        if (myBusMarker == null) {
            ivFavIcon.setVisibility(View.GONE);
        } else {
            ivFavIcon.setVisibility(View.VISIBLE);
            if (myBusMarker.isFavorite()) {
                ivFavIcon.setImageResource(R.drawable.favorite_remove_icon);
            } else {
                ivFavIcon.setImageResource(R.drawable.favorite_add_icon);
            }
        }
        return mContentsView;
    }
}
