package com.mybus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mybus.R;
import com.mybus.adapter.BusLineViewAdapter;
import com.mybus.asynctask.BusLinesRequestCallback;
import com.mybus.listener.BusLineListItemListener;
import com.mybus.model.BusLine;
import com.mybus.service.ServiceFacade;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class DisplayBusLinesActivity extends AppCompatActivity implements BusLinesRequestCallback, BusLineListItemListener{

    private ProgressDialog mDialog;

    @Bind(R.id.bus_lines_recycler_view)
    RecyclerView mBusLinesRecyclerView;
    private BusLineViewAdapter mBusLineAdapter;
    private List<BusLine> mBusLines;
    public static final String RESULT_BUS_LINE_ID = "BUS_LINE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bus_lines);
        ButterKnife.bind(this);
        mBusLinesRecyclerView.setHasFixedSize(true);
        mBusLineAdapter = new BusLineViewAdapter(this);
        mBusLinesRecyclerView.setAdapter(mBusLineAdapter);
        mBusLinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Show a progress dialog while bus lines are loading
        showProgressDialog(getString(R.string.toast_bus_lines_searching));
        ServiceFacade.getInstance().getBusLines(this);
    }

    @Override
    public void onBusLinesFound(List<BusLine> busLines) {
        cancelProgressDialog();
        mBusLines = busLines;
        mBusLineAdapter.setDataSet(busLines);
        mBusLineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int position) {
        //Return the bus line id to the MainActivity
        Intent intent = new Intent();
        intent.putExtra(RESULT_BUS_LINE_ID, mBusLines.get(position).getId());
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    protected void showProgressDialog(String text) {
        cancelProgressDialog();
        mDialog = ProgressDialog.show(this, "", text, true, false);

    }

    protected void cancelProgressDialog() {
        if (mDialog != null) {
            mDialog.cancel();
            mDialog = null;
        }
    }
}
