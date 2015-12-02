package com.example.ksala.healthproject.Fragments.ConcreteFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.Utils;

public class ECGFragment extends CommonFragment {

    public ECGFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        startText.setText(Utils.ECG_INSTRUCTIONS);
        return rootView;
    }
}
