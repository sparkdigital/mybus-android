package com.mybus.marker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
        String stopBusStr = mContext.getResources().getString(R.string.bus_stop_origin).split(" ")[0];
        if (marker.getTitle().contains(stopBusStr)
                || marker.getTitle().equals(mContext.getString(R.string.current_location_marker))) {
            return null;
        }
        TextView tvTitle = ((TextView) mContentsView.findViewById(R.id.marker_title));
        tvTitle.setText(marker.getTitle());
        TextView tvAddress = ((TextView) mContentsView.findViewById(R.id.marker_address));
        tvAddress.setText(marker.getSnippet());

        return mContentsView;
    }
}
