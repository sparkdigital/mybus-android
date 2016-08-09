package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.BusLineListItemListener;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class BusLineItemViewHolder extends RecyclerView.ViewHolder {

    public TextView mBusLineName;
    public LinearLayout mBusLineColorRectangle;
    private BusLineListItemListener mItemClickListener;

    public BusLineItemViewHolder(View itemView, BusLineListItemListener listener) {
        super(itemView);
        mItemClickListener = listener;
        mBusLineName = (TextView) itemView.findViewById(R.id.bus_line_name);
        mBusLineColorRectangle = (LinearLayout) itemView.findViewById(R.id.bus_line_color_rectangle);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClicked(getAdapterPosition());
            }
        });
    }
}
