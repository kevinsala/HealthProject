package com.example.ksala.healthproject;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DummyFragment extends AbstractFragment implements View.OnClickListener {

    private RelativeLayout startLayout, endLayout;
    private Button startButton, backButton;

    public DummyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_common, container, false);

        startLayout = (RelativeLayout) rootView.findViewById(R.id.startLayout);
        startLayout.setVisibility(View.VISIBLE);
        startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        endLayout = (RelativeLayout) rootView.findViewById(R.id.endLayout);
        endLayout.setVisibility(View.INVISIBLE);
        backButton = (Button) rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.startButton) {
            startLayout.setAnimation(Utils.outGoUp(350));
            endLayout.setAnimation(Utils.inGoUp(350));
            startLayout.setVisibility(View.INVISIBLE);
            endLayout.setVisibility(View.VISIBLE);
        }
        else {
            startLayout.setAnimation(Utils.inGoDown(250));
            endLayout.setAnimation(Utils.outGoDown(250));
            endLayout.setVisibility(View.INVISIBLE);
            startLayout.setVisibility(View.VISIBLE);
        }
    }
}
