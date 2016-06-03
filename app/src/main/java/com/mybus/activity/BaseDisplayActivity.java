package com.mybus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mybus.R;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public abstract class BaseDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutToInflate());
        initToolbar();
    }

    /**
     * Initialize the toolbar with the correct title and replace the transactions between activities
     */
    private void initToolbar() {

        final Toolbar toolbar = (Toolbar) findViewById(getToolbarId());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getToolbarTittle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Replacing the transaction between the activities
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

    public abstract int getLayoutToInflate();

    public abstract int getToolbarId();

    protected abstract int getToolbarTittle();
}