package com.mybus.activity;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayBusLinesActivity extends BaseDisplayActivity {

    @Override
    public int getLayoutToInflate() {
        return R.layout.activity_display_bus_lines;
    }

    @Override
    public int getToolbarId() {
        return R.id.displayBusLinesToolbar;
    }

    @Override
    protected int getToolbarTittle() {
        return R.string.displayBusLinesToolbarTittle;
    }
}
