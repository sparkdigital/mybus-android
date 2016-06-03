package com.mybus.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView historyAddress;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        historyAddress = (TextView) itemView.findViewById(R.id.history_address);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked History Item = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
