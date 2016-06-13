package com.mybus.activity;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFavoritesActivity extends BaseDisplayActivity {
    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_display_favorites;
    }

    @Override
    public int getToolbarId() {
        return R.id.displayFavoritesToolbar;
    }

    @Override
    protected int getToolbarTittle() {
        return R.string.displayFavoritesToolbarTittle;
    }
}
