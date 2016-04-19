package com.mybus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mybus.R;
import com.mybus.helper.SearchFormStatus;

/**
 * Custom InfoWindow Adapter for Popups over Markers
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater layoutInflater;
    private Context mContext;

    public CustomInfoWindowAdapter(LayoutInflater inflater, Context c) {
        this.layoutInflater = inflater;
        this.mContext = c;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        if (!SearchFormStatus.getInstance().isStartFilled()) {
            //Show Start popup
            return inflateView(R.drawable.marker_origen, mContext.getString(R.string.popup_question_origin));
        } else if (!SearchFormStatus.getInstance().isDestinationFilled()
                && !marker.getId().equals(SearchFormStatus.getInstance().getStartMarkerId())) {
            //Show End Popup
            return inflateView(R.drawable.marker_destino, mContext.getString(R.string.popup_question_destination));
        }
        return null;
    }

    /**
     * Inflate the popup view
     *
     * @param resId
     * @param text
     * @return
     */
    private View inflateView(int resId, String text) {
        // Getting view from the layout file map_popup
        View v = layoutInflater.inflate(R.layout.map_popup, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.popup_imageView);
        TextView textView = (TextView) v.findViewById(R.id.popup_textview);
        imageView.setImageResource(resId);
        textView.setText(text);
        return v;
    }
}