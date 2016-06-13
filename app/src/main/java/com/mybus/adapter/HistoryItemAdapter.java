package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.listener.HistoryItemSelectedListener;
import com.mybus.model.RecentLocation;
import com.mybus.view.HistoryViewHolder;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryViewHolder> implements HistoryItemSelectedListener {
    private List<RecentLocation> mDataset;
    private HistoryItemSelectedListener mItemSelectedListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HistoryItemAdapter(List<RecentLocation> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        HistoryViewHolder vh = new HistoryViewHolder(v, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.setRecentLocation(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setItemSelectedListener(HistoryItemSelectedListener listener) {
        this.mItemSelectedListener = listener;
    }

    @Override
    public void onHistoryItemSelected(RecentLocation result) {
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onHistoryItemSelected(result);
        }
    }
}
