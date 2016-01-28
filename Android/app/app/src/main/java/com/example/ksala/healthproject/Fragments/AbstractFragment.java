package com.example.ksala.healthproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ksala.healthproject.Activities.MainActivity;

/**
 * Created by kevin on 22/11/2015.
 */
public abstract class AbstractFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState);

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
