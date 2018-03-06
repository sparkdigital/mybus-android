package com.mybus.adapter;

import android.content.Context;
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
    //private Context mContext;

    /**
     * @param context
     */
    public FareViewAdapter(Context context) {
        //this.mContext = context;
    }

    @Override
    public FareItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fare_item, parent, false);
        return new FareItemViewHolder(v);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(FareItemViewHolder holder, int position) {
        if (mDataset != null && !mDataset.isEmpty()) {
            holder.mFareTitle.setText(mDataset.get(position).getTitle());
            //holder.mFareCost.setText(mContext.getString(R.string.bus_cost, mDataset.get(position).getCost()));
            holder.mFareCost.setText(mDataset.get(position).getCost());
        }
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.size();
    }

    /**
     * @param dataSet
     */
    public void setDataSet(List<Fare> dataSet) {
        this.mDataset = dataSet;
    }
}
