package com.example.ksala.healthproject.Activities;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Views.ECGChart;
import com.github.mikephil.charting.data.LineDataSet;

import org.achartengine.model.XYSeries;

public class ECGViewerActivity extends AppCompatActivity {

    private ECGChart ecgChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecgviewer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*ecgChart = new ECGChart(this, ECGChart.LANDSCAPE,
                (XYSeries) getIntent().getSerializableExtra("chart-series"));*/

        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        rootView.addView(ecgChart.getView(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
