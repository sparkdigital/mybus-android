package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.listener.FavoriteItemSelectedListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.view.FavoriteViewHolder;

import java.util.List;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class FavoriteItemAdapter extends RecyclerView.Adapter<FavoriteViewHolder> implements FavoriteItemSelectedListener {
    private List<FavoriteLocation> mDataset;
    private FavoriteItemSelectedListener mItemSelectedListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoriteItemAdapter(List<FavoriteLocation> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        FavoriteViewHolder vh = new FavoriteViewHolder(v, mItemSelectedListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.favName.setText(mDataset.get(position).getName());
        holder.favAddress.setText(mDataset.get(position).getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setItemSelectedListener(FavoriteItemSelectedListener listener) {
        this.mItemSelectedListener = listener;
    }

    @Override
    public void onFavoriteItemSelected(int position) {
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onFavoriteItemSelected(position);
        }
    }
}
