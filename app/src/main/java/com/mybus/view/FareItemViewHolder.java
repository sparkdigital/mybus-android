package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;

public class FareItemViewHolder extends RecyclerView.ViewHolder {

    public TextView mFareTitle;
    public TextView mFareCost;

    public FareItemViewHolder(View itemView) {
        super(itemView);
        mFareTitle = (TextView) itemView.findViewById(R.id.fare_title);
        mFareCost = (TextView) itemView.findViewById(R.id.fare_cost);
    }
}
