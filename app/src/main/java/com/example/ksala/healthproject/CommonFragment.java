package com.example.ksala.healthproject;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class CommonFragment extends AbstractFragment implements View.OnClickListener {

    private RelativeLayout startLayout, middleLayout, endLayout;
    private Button startButton, cancelButton, restartButton, menuButton;

    public CommonFragment() {

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

        middleLayout = (RelativeLayout) rootView.findViewById(R.id.middleLayout);
        middleLayout.setVisibility(View.INVISIBLE);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        endLayout = (RelativeLayout) rootView.findViewById(R.id.endLayout);
        endLayout.setVisibility(View.INVISIBLE);
        restartButton = (Button) rootView.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(this);
        menuButton = (Button) rootView.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.startButton) {
            getMainActivity().setSwipeable(false);
            startLayout.setAnimation(Utils.outGoUp(350));
            middleLayout.setAnimation(Utils.inGoUp(350));
            startLayout.setVisibility(View.INVISIBLE);
            middleLayout.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.cancelButton) {
            getMainActivity().setSwipeable(true);
            middleLayout.setAnimation(Utils.outGoUp(350));
            endLayout.setAnimation(Utils.inGoUp(350));
            middleLayout.setVisibility(View.INVISIBLE);
            endLayout.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.restartButton) {
            getMainActivity().setSwipeable(false);
            middleLayout.setAnimation(Utils.inGoDown(350));
            endLayout.setAnimation(Utils.outGoDown(350));
            middleLayout.setVisibility(View.VISIBLE);
            endLayout.setVisibility(View.INVISIBLE);
        }
        else if (id == R.id.menuButton) {
            getMainActivity().onBackPressed();
        }
    }
}
