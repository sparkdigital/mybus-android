package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mybus.R;
import com.mybus.listener.FavoriteItemSelectedListener;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView favName;
    public TextView favAddress;
    private FavoriteItemSelectedListener mItemSelectedListener;

    public FavoriteViewHolder(View itemView, FavoriteItemSelectedListener favoriteItemSelectedListener) {
        super(itemView);
        mItemSelectedListener = favoriteItemSelectedListener;
        itemView.setOnClickListener(this);
        favName = (TextView) itemView.findViewById(R.id.favorite_name);
        favAddress = (TextView) itemView.findViewById(R.id.favorite_address);
    }

    @Override
    public void onClick(View view) {
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onFavoriteItemSelected(favAddress.getText().toString());
        }
    }
}
