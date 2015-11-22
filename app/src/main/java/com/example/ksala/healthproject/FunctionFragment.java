package com.example.ksala.healthproject;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class FunctionFragment extends AbstractFragment implements AdapterView.OnItemClickListener {

    private GridView functionGridView;

    public FunctionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_function, container, false);

        functionGridView = (GridView) rootView.findViewById(R.id.functionGridView);
        functionGridView.setAdapter(new ImageAdapter(getMainActivity()));
        functionGridView.setOnItemClickListener(this);

        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int page = 3;
        switch (position) {
            case 0:
                page = 2;
                break;
            case 1:
                page = 4;
                break;
            case 2:
                page = 1;
                break;
            case 3:
                page = 5;
                break;
            case 4:
                page = 0;
                break;
            case 5:
                page = 5;
                break;
        }

        getMainActivity().setCurrentPage(page);
    }
}
