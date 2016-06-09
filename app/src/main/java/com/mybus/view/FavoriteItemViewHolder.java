package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.FavoriteItemDeleteListener;
import com.mybus.model.Favorite;

public class FavoriteItemViewHolder extends RecyclerView.ViewHolder  {

    public TextView favName;
    public TextView favAddress;
    public ImageView favoriteEdit;
    public ImageView favoriteDelete;


    private FavoriteItemDeleteListener favoriteDeleteListener;
    private FavoriteItemViewHolder mfavoriteItemViewHolder;

    private Favorite fav;

    public FavoriteItemViewHolder(View itemView, final FavoriteItemDeleteListener favoriteDeleteListener) {
        super(itemView);
        this.favoriteDeleteListener = favoriteDeleteListener;
        favName = (TextView) itemView.findViewById(R.id.favorite_name);
        favAddress = (TextView) itemView.findViewById(R.id.favorite_address);
        favoriteEdit = (ImageView) itemView.findViewById(R.id.favorite_edit);
        favoriteDelete = (ImageView) itemView.findViewById(R.id.favorite_delete);
        favoriteDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteDeleteListener.onFavoriteItemDelete( fav );
            }
        });
    }

    public Favorite getFav() {
        return fav;
    }

    public void setFav(Favorite fav) {
        this.fav = fav;
    }

}
