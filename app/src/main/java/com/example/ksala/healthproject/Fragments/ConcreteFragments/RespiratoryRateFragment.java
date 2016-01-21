package com.example.ksala.healthproject.Fragments.ConcreteFragments;


import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Utils;

public class RespiratoryRateFragment extends CommonFragment {

    private TextView measurementTextView;

    public RespiratoryRateFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        startText.setText(Utils.RESPIRATORY_RATE_INSTRUCTIONS);

        RelativeLayout measurementFrame = (RelativeLayout) rootView.findViewById(R.id.measurementFrame);
        measurementTextView = new TextView(getActivity());
        measurementTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        measurementTextView.setText(getResources().getString(R.string.rr_value));
        measurementFrame.addView(measurementTextView);
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) measurementTextView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        return rootView;
    }

    @Override
    public void addData(double x, double y, boolean finished) {
        if (finished) {
            measurementTextView.setText(String.format( "%.2f breaths per minute", x));
            mesurementEnded();
        }
    }

    @Override
    public void startPressed() {
        getMainActivity().startMeasurement(Utils.RESPIRATORY_RATE_FUNC);
    }

    @Override
    public void restartPressed() {
        measurementTextView.setText(getResources().getString(R.string.rr_value));
        startPressed();
    }
}
