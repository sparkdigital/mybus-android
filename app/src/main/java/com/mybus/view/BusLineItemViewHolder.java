package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusLineItemViewHolder extends RecyclerView.ViewHolder {

    public TextView mBusLineName;
    public TextView mBusLineColor;

    public BusLineItemViewHolder(View itemView) {
        super(itemView);
        mBusLineName = (TextView) itemView.findViewById(R.id.bus_line_name);
        mBusLineColor = (TextView) itemView.findViewById(R.id.bus_line_color);
    }
}
