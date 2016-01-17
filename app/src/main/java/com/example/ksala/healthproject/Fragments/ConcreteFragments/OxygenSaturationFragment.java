package com.example.ksala.healthproject.Fragments.ConcreteFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.Utils;

public class OxygenSaturationFragment extends CommonFragment {

    public OxygenSaturationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        startText.setText(Utils.OXYGEN_SATURATION_INSTRUCTIONS);
        return rootView;
    }

    @Override
    public void addData(double x, double y, boolean finished) {
        assert (finished);
    }

    @Override
    public void startPressed() {
        getMainActivity().startMeasurement(Utils.BLOOD_PRESSURE_FUNC);
    }

    @Override
    public void cancelPressed() {
        getMainActivity().cancelMeasurement(Utils.BLOOD_PRESSURE_FUNC);
    }

    @Override
    public void restartPressed() {
        cancelPressed();
        startPressed();
    }

    @Override
    public void mesurementEnded() {
        getMainActivity().endMeasurement(Utils.BLOOD_PRESSURE_FUNC);
    }
}
