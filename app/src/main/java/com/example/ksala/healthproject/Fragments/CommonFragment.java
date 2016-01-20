package com.example.ksala.healthproject.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Utils;

public abstract class CommonFragment extends AbstractFragment implements View.OnClickListener {

    public static int START_PAGE = 0;
    public static int MEASUREMENT_PAGE = 1;

    protected RelativeLayout startLayout, measurementLayout;
    protected Button startButton, restartButton;
    protected TextView startText;
    protected int fragmentPage;
    protected boolean running;

    public CommonFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentPage = START_PAGE;
        running = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_common, container, false);

        startLayout = (RelativeLayout) rootView.findViewById(R.id.startLayout);
        startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        measurementLayout = (RelativeLayout) rootView.findViewById(R.id.measurementLayout);
        restartButton = (Button) rootView.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(this);
        restartButton.setVisibility(((fragmentPage == MEASUREMENT_PAGE && !running) ? View.VISIBLE : View.INVISIBLE));

        startLayout.setVisibility(((fragmentPage == START_PAGE) ? View.VISIBLE : View.INVISIBLE));
        measurementLayout.setVisibility(((fragmentPage == MEASUREMENT_PAGE) ? View.VISIBLE : View.INVISIBLE));

        startText = (TextView) rootView.findViewById(R.id.startText);

        return rootView;
    }

    public abstract void addData(double x, double y, boolean finished);

    public abstract void startPressed();

    public abstract void restartPressed();

    public void mesurementEnded() {
        running = false;
        getMainActivity().setSwipeable(true);
        restartButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.startButton) {
            startPressed();
            running = true;
            fragmentPage = MEASUREMENT_PAGE;
            getMainActivity().setSwipeable(false);
            restartButton.setVisibility(View.INVISIBLE);
            startLayout.setAnimation(Utils.outGoUp(350));
            measurementLayout.setAnimation(Utils.inGoUp(350));
            startLayout.setVisibility(View.INVISIBLE);
            measurementLayout.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.restartButton) {
            restartPressed();
            running = true;
            fragmentPage = MEASUREMENT_PAGE;
            getMainActivity().setSwipeable(false);
            restartButton.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isRunning() {
        return running;
    }
}
