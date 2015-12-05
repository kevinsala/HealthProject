package com.example.ksala.healthproject.Fragments.ConcreteFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Utils;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.XYChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class ECGFragment extends CommonFragment {

    private XYSeries series = new XYSeries("Electrocardiograma");
    private XYSeriesRenderer renderer = new XYSeriesRenderer();

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

        for (int i = 0; i < 10; ++i) series.add(i, i);

        // Now we create the renderer
        renderer.setLineWidth(5);
        renderer.setColor(Color.RED);
        // Include low and max value
        renderer.setDisplayBoundingPoints(true);
        // we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(10);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
// Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(10);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(false, false);


        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        GraphicalView chartView = ChartFactory.getLineChartView(getActivity(), dataset, mRenderer);

        FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.middleFrame);

        frameLayout.addView(chartView,0);

        return rootView;
    }

    @Override
    public boolean hasMiddle() {
        return false;
    }
}
