package com.example.ksala.healthproject.Fragments.ConcreteFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.ksala.healthproject.Activities.ECGViewerActivity;
import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Utils;
import com.example.ksala.healthproject.Views.ECGChart;

public class ECGFragment extends CommonFragment {

    private ECGChart middleEcgChart;
    private ECGChart endEcgChart;
    private Button openChartButton;

    private FrameLayout middleFrame;
    private FrameLayout endFrame;
    private RelativeLayout ecgEndLayout;

    public ECGFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        middleEcgChart = new ECGChart(getContext(), ECGChart.PORTRAIT);
        middleEcgChart.setScrollable(true);

        for (int i = 0; i < 50; ++i) {
            for (int j = 0; j < 50; ++j)
                middleEcgChart.addData(i*100 + j, -25 + j);
            for (int j = 0; j < 50; ++j)
                middleEcgChart.addData(i*100 + j + 50, 25 - j);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        startText.setText(Utils.ECG_INSTRUCTIONS);

        middleFrame = (FrameLayout) rootView.findViewById(R.id.middleFrame);
        endFrame = (FrameLayout) rootView.findViewById(R.id.endFrame);

        middleFrame.addView(middleEcgChart.getView());

        return rootView;
    }

    private void initEcgEndLayout() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        ecgEndLayout = (RelativeLayout) inflater.inflate(R.layout.ecg_layout, null);
        openChartButton = (Button) ecgEndLayout.findViewById(R.id.openChartButton);
        openChartButton.setOnClickListener(this);
        RelativeLayout ecgChartLayout = (RelativeLayout) ecgEndLayout.findViewById(R.id.ecgChartLayout);
        ecgChartLayout.addView(endEcgChart.getView());
    }

    public void addData(double x, double y) {
        middleEcgChart.addData(x, y);
    }

    @Override
    public void startPressed() {
        middleEcgChart.setScrollable(true);
    }

    @Override
    public void cancelPressed() {
        middleEcgChart.clearData();
    }

    @Override
    public void restartPressed() {
        endEcgChart.clearData();
        middleEcgChart.clearData();
    }

    @Override
    public void mesurementEnded() {
        if (ecgEndLayout == null) {
            endEcgChart = new ECGChart(getContext(), ECGChart.PORTRAIT, middleEcgChart.getData());
            initEcgEndLayout();
            endFrame.addView(ecgEndLayout);
        } else endEcgChart.setData(middleEcgChart.getData());
        endEcgChart.setScrollable(false);
        super.mesurementEnded();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.openChartButton) {
            middleEcgChart.clearData();
            Intent intent = new Intent(getContext(), ECGViewerActivity.class);
            intent.putExtra("chart-series", endEcgChart.getData());
            startActivity(intent);
        }
        else super.onClick(v);
    }
}
