package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.model.Fare;
import com.mybus.view.FareItemViewHolder;

import java.util.List;

/**
 * Created by Lucas De Lio on 6/28/2016.
 */
public class FareViewAdapter extends RecyclerView.Adapter<FareItemViewHolder> {

    private List<Fare> mDataset;

    @Override
    public FareItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fare_item, parent, false);
        return new FareItemViewHolder(v);
    }

    /**
     * @param dataSet
     */
    public FareViewAdapter(List<Fare> dataSet) {
        mDataset = dataSet;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(FareItemViewHolder holder, int position) {
        holder.mFareTitle.setText(mDataset.get(position).getTitle());
        holder.mFareCost.setText("$" + mDataset.get(position).getCost());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
