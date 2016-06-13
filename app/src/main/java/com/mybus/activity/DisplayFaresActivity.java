package com.mybus.activity;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayFaresActivity extends BaseDisplayActivity {
    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_display_fares;
    }

    @Override
    public int getToolbarId() {
        return R.id.displayFaresToolbar;
    }

    @Override
    protected int getToolbarTittle() {
        return R.string.displayFaresToolbarTittle;
    }
}
