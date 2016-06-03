package com.mybus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView favName;
    public TextView favAddress;

    public FavoriteViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        favName = (TextView) itemView.findViewById(R.id.favorite_name);
        favAddress = (TextView) itemView.findViewById(R.id.favorite_address);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Favorite Item = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
