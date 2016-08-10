package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.FavoriteListItemListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.favorite_name)
    public TextView favName;
    @Bind(R.id.favorite_address)
    public TextView favAddress;
    @Bind(R.id.favorite_edit)
    public ImageView favoriteEditIcon;
    @Bind(R.id.favorite_delete)
    public ImageView favoriteDeleteIcon;
    private FavoriteListItemListener mFavoriteListener;

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

    public FavoriteItemViewHolder(View itemView, FavoriteListItemListener favoriteEditListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mFavoriteListener = favoriteEditListener;
    }
}
