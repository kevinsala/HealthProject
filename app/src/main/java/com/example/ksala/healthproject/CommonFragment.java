package com.example.ksala.healthproject;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class CommonFragment extends AbstractFragment implements View.OnClickListener {

    public static int START_PAGE = 0;
    public static int MIDDLE_PAGE = 1;
    public static int END_PAGE = 2;

    private RelativeLayout startLayout, middleLayout, endLayout;
    private Button startButton, cancelButton, restartButton, menuButton;
    private int fragmentPage = 0;

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
        startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        middleLayout = (RelativeLayout) rootView.findViewById(R.id.middleLayout);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        endLayout = (RelativeLayout) rootView.findViewById(R.id.endLayout);
        restartButton = (Button) rootView.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(this);
        menuButton = (Button) rootView.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this);

        startLayout.setVisibility(((fragmentPage == START_PAGE) ? View.VISIBLE : View.INVISIBLE));
        middleLayout.setVisibility(((fragmentPage == MIDDLE_PAGE) ? View.VISIBLE : View.INVISIBLE));
        endLayout.setVisibility(((fragmentPage == END_PAGE) ? View.VISIBLE : View.INVISIBLE));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.startButton) {
            fragmentPage = MIDDLE_PAGE;
            getMainActivity().setSwipeable(false);
            startLayout.setAnimation(Utils.outGoUp(350));
            middleLayout.setAnimation(Utils.inGoUp(350));
            startLayout.setVisibility(View.INVISIBLE);
            middleLayout.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.cancelButton) {
            fragmentPage = START_PAGE;
            getMainActivity().setSwipeable(true);
            middleLayout.setAnimation(Utils.outGoDown(350));
            startLayout.setAnimation(Utils.inGoDown(350));
            middleLayout.setVisibility(View.INVISIBLE);
            startLayout.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.restartButton) {
            fragmentPage = MIDDLE_PAGE;
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
