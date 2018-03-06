package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.BusLineListItemListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 19/07/2016.
 */
public class BusResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.bus_line_number)
    public TextView mLineNumber;
    @BindView(R.id.start_address)
    public TextView mStartAddress;
    @BindView(R.id.stop_address)
    public TextView mStopAddress;

    // Second line information:
    @BindView(R.id.secondLineView)
    public LinearLayout mSecondLineView;
    @BindView(R.id.first_line_destination_title)
    public TextView mFirstDestinationTitle;
    @BindView(R.id.second_line_start_address)
    public TextView mSecondStartAddress;
    @BindView(R.id.second_line_stop_address)
    public TextView mSecondStopAddress;

    @BindView(R.id.arrival_time_layout)
    public LinearLayout mArrivalTimeLayout;
    @BindView(R.id.arrival_time_txt)
    public TextView mArrivalTimeTxt;

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
