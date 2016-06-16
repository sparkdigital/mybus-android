package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.listener.FavoriteListItemListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.view.FavoriteItemViewHolder;

import java.util.List;

public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteItemViewHolder> implements FavoriteListItemListener {

    private List<FavoriteLocation> mDataset;
    private FavoriteListItemListener mFavoriteListItemListener;

    @Override
    public FavoriteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item_list, parent, false);
        return new FavoriteItemViewHolder(v, this);
    }

    public FavoriteViewAdapter(List<FavoriteLocation> dataSet,FavoriteListItemListener mFavoriteListItemListener) {
        mDataset = dataSet;
        this.mFavoriteListItemListener = mFavoriteListItemListener;
    }

    @Override
    public void onBindViewHolder(FavoriteItemViewHolder holder, int pos) {
        holder.favName.setText(mDataset.get(pos).getName());
        holder.favAddress.setText(mDataset.get(pos).getAddress());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onFavoriteItemDelete(int position) {
        mFavoriteListItemListener.onFavoriteItemDelete(position);
    }

    @Override
    public void onFavoriteItemEdit(int position) {
        mFavoriteListItemListener.onFavoriteItemEdit(position);
    }


}
