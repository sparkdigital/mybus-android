package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.HistoryItemSelectedListener;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView historyAddress;
    private HistoryItemSelectedListener mItemSelectedListener;

    public HistoryViewHolder(View itemView, HistoryItemSelectedListener itemSelectedListener) {
        super(itemView);
        mItemSelectedListener = itemSelectedListener;
        itemView.setOnClickListener(this);
        historyAddress = (TextView) itemView.findViewById(R.id.history_address);
    }

    @Override
    public void onClick(View view) {
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onHistoryItemSelected(getAdapterPosition());
        }
    }
}
