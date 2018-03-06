package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.FavoriteListItemListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.favorite_name)
    public TextView favName;
    @BindView(R.id.favorite_address)
    public TextView favAddress;
    private FavoriteListItemListener mFavoriteListener;

    public FavoriteItemViewHolder(View itemView, FavoriteListItemListener favoriteEditListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mFavoriteListener = favoriteEditListener;
    }

    @OnClick({R.id.favorite_star_icon, R.id.favorite_name, R.id.favorite_address})
    public void showFavoriteOnMap() {
        mFavoriteListener.onFavoriteClicked(getAdapterPosition());
    }

    @OnClick(R.id.favorite_delete)
    public void deleteFavorite() {
        mFavoriteListener.onFavoriteItemDelete(getAdapterPosition());
    }

    @OnClick(R.id.favorite_edit)
    public void editFavorite() {
        mFavoriteListener.onFavoriteItemEdit(getAdapterPosition());
    }
}
