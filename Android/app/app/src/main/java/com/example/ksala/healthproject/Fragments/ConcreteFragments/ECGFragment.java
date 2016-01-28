package com.example.ksala.healthproject.Fragments.ConcreteFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Utils;
import com.example.ksala.healthproject.Views.ECGChart;
import com.github.mikephil.charting.charts.LineChart;

public class ECGFragment extends CommonFragment {

    private RelativeLayout frame;
    private ECGChart ecgChart;
    private TextView bpmTextView;

    public ECGFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        startText.setText(Utils.ECG_INSTRUCTIONS);

        frame = (RelativeLayout) rootView.findViewById(R.id.measurementFrame);
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
            mesurementEnded();
        }
    }

    @Override
    public void startPressed() {
        getMainActivity().startMeasurement(Utils.ECG_FUNC);
    }

    @Override
    public void restartPressed() {
        ecgChart.clearData();
        bpmTextView.setText(getResources().getString(R.string.ecg_value));
        startPressed();
    }
}
