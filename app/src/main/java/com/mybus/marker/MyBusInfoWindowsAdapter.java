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
        //Detect which type of marker is:
        MyBusMarker myBusMarker = mActivity.isMyBusMarker(marker);
        if (myBusMarker != null) {
            switch (myBusMarker.getType()) {
                case MyBusMarker.ORIGIN:
                case MyBusMarker.DESTINATION:
                    ivFavIcon.setVisibility(View.VISIBLE);
                    if (myBusMarker.isFavorite()) {
                        tvTitle.setText(myBusMarker.getFavoriteName()); //Change title with favorite name
                        ivFavIcon.setImageResource(R.drawable.favorite_remove_icon); //Put remove favorite icon
                    } else {
                        ivFavIcon.setImageResource(R.drawable.favorite_add_icon);
                    }
                    break;
                default:
                    ivFavIcon.setVisibility(View.GONE);
                    break;
            }
        } else { //Bus stop markers
            ivFavIcon.setVisibility(View.GONE);
        }
        return mContentsView;
    }
}
