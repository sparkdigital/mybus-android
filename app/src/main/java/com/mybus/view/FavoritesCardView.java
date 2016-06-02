package com.mybus.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class FavoritesCardView extends CardView {
    public FavoritesCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.favorites_card_view, this);
    }
}
