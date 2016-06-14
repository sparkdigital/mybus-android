package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.listener.FavoriteDeleteListener;
import com.mybus.listener.FavoriteEditListener;
import com.mybus.model.FavoriteLocation;
import com.mybus.view.FavoriteItemViewHolder;

import java.util.List;

public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteItemViewHolder> implements FavoriteDeleteListener, FavoriteEditListener {

    private List<FavoriteLocation> mDataset;
    private FavoriteEditListener mFavoriteEditListener;
    private FavoriteDeleteListener mFavoriteDeleteListener;
    FavoriteItemViewHolder vh;

    @Override
    public FavoriteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        FavoriteItemViewHolder vh = new FavoriteItemViewHolder(v, this, this);
        return vh;
    }

    public FavoriteViewAdapter(List<FavoriteLocation> dataSet, FavoriteEditListener favoriteEditListener, FavoriteDeleteListener favoriteDeleteListener) {
        mDataset = dataSet;
        this.mFavoriteEditListener = favoriteEditListener;
        this.mFavoriteDeleteListener = favoriteDeleteListener;
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
        mFavoriteDeleteListener.onFavoriteItemDelete(position);
    }

    @Override
    public void onFavoriteItemEdit(int position) {
        mFavoriteEditListener.onFavoriteItemEdit(position);
    }


}
