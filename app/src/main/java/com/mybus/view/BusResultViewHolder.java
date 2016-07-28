package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.BusLineListItemListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 19/07/2016.
 */
public class BusResultViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.bus_line_number)
    public TextView mLineNumber;
    @Bind(R.id.start_address)
    public TextView mStartAddress;
    @Bind(R.id.stop_address)
    public TextView mStopAddress;
    @Bind(R.id.start_distance)
    public TextView mStartDistance;
    @Bind(R.id.stop_distance)
    public TextView mStopDistance;

    private BusLineListItemListener mItemClickListener;

    public BusResultViewHolder(View itemView, BusLineListItemListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemClickListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClicked(getAdapterPosition());
            }
        });
    }
}
