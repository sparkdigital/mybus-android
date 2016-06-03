package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.view.FavoriteViewHolder;
import com.mybus.view.HistoryViewHolder;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HistoryItemAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        HistoryViewHolder vh = new HistoryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.historyAddress.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
