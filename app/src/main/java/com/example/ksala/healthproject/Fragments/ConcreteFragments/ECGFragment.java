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
import android.widget.TextView;

import com.example.ksala.healthproject.Activities.ECGViewerActivity;
import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Utils;
import com.example.ksala.healthproject.Views.ECGChart;
import com.github.mikephil.charting.charts.LineChart;

public class ECGFragment extends CommonFragment {

    private FrameLayout frame;
    private ECGChart ecgChart;
    private TextView bpmTextView;
    private boolean running;

    public ECGFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        running = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        startText.setText(Utils.ECG_INSTRUCTIONS);

        frame = (FrameLayout) rootView.findViewById(R.id.middleFrame);
        RelativeLayout ecgLayout = (RelativeLayout) inflater.inflate(R.layout.ecg_layout, null);
        frame.addView(ecgLayout);

        LineChart lineChart = (LineChart) rootView.findViewById(R.id.ecgChart);
        ecgChart = new ECGChart(lineChart);

        bpmTextView = (TextView) rootView.findViewById(R.id.bpmText);

        return rootView;
    }

    @Override
    public void addData(double x, double y, boolean finished) {
        ecgChart.addData(x, y);

        if (finished) {
            bpmTextView.setText(String.format( "%.1f BPM", ecgChart.getBPM() ));
            cancelButton.setText(getResources().getString(R.string.restart));
            cancelButton.setVisibility(View.VISIBLE);
            running = false;
        }
    }

    @Override
    public void startPressed() {
        getMainActivity().startMeasurement(Utils.ECG_FUNC);
        running = true;
        cancelButton.setVisibility(View.INVISIBLE);
        cancelButton.setText(getResources().getString(R.string.cancel));
    }

    @Override
    public void cancelPressed() {
        getMainActivity().cancelMeasurement(Utils.ECG_FUNC);
        running = false;
        ecgChart.clearData();
        bpmTextView.setText(getResources().getString(R.string.bpm_value));
    }

    @Override
    public void restartPressed() {
        ecgChart.clearData();
        bpmTextView.setText(getResources().getString(R.string.bpm_value));
        startPressed();
    }

    @Override
    public void mesurementEnded() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancelButton && !running) restartPressed();
        else super.onClick(v);
    }
}
