package com.mybus.view.indowindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.mybus.R;
import com.mybus.marker.MyBusMarker;

/**
 * Created by ldimitroff on 07/07/16.
 */
public class GenericMarkerInfoWindow extends FrameLayout {

    private TextView mTvTitle;
    private TextView mTvAddress;
    private ImageView mFavIcon;

    public GenericMarkerInfoWindow(Context context) {
        super(context);
        initView();
    }

    public GenericMarkerInfoWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GenericMarkerInfoWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.marker_info_window, this);
        mTvTitle = ((TextView) findViewById(R.id.marker_title));
        mTvAddress = ((TextView) findViewById(R.id.marker_address));
        mFavIcon = ((ImageView) findViewById(R.id.marker_fav_icon));
    }

    public GenericMarkerInfoWindow setMapMarker(Marker marker) {
        mTvTitle.setText(marker.getTitle());
        if (marker.getSnippet() != null && !marker.getSnippet().isEmpty()) {
            mTvAddress.setVisibility(VISIBLE);
            mTvAddress.setText(marker.getSnippet());
        } else {
            mTvAddress.setVisibility(GONE);
        }
        return this;
    }

    public GenericMarkerInfoWindow setFavIconVisible(boolean visible) {
        mFavIcon.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    public GenericMarkerInfoWindow setMyBusMarker(MyBusMarker myBusMarker) {
        mFavIcon.setVisibility(View.VISIBLE);
        mTvAddress.setText(myBusMarker.getMapMarker().getSnippet());
        if (myBusMarker.isFavorite()) {
            mTvTitle.setText(myBusMarker.getFavoriteName()); //Change title with favorite name
            mFavIcon.setImageResource(R.drawable.favorite_remove_icon); //Put remove favorite icon
        } else {
            mTvTitle.setText(myBusMarker.getMapMarker().getTitle()); //Change title with marker name
            mFavIcon.setImageResource(R.drawable.favorite_add_icon);
        }
        return this;
    }

}
