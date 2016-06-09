package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mybus.R;
import com.mybus.listener.FavoriteItemDeleteListener;
import com.mybus.model.Favorite;
import com.mybus.view.FavoriteItemViewHolder;

import java.util.List;

public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteItemViewHolder> implements FavoriteItemDeleteListener {

    private List<Favorite> mDataset;

    @Override
    public FavoriteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        FavoriteItemViewHolder vh = new FavoriteItemViewHolder(v, this);
        return vh;
    }

    public FavoriteViewAdapter(List<Favorite> dataSet) {
        mDataset = dataSet;
    }

    @Override
    public void onBindViewHolder(FavoriteItemViewHolder holder, int pos) {
        holder.favName.setText(mDataset.get(pos).name);
        holder.favAddress.setText(mDataset.get(pos).address);
        holder.setFav(mDataset.get(pos));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onFavoriteItemDelete(Favorite fav) {
        int pos = mDataset.indexOf(fav);
        mDataset.remove(pos);
        notifyItemRemoved(pos);
    }
}
