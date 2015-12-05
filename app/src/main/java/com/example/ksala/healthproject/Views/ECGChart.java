package com.example.ksala.healthproject.Views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.ksala.healthproject.Utils;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.Serializable;

public class ECGChart {

    public static final int LANDSCAPE = 0;
    public static final int PORTRAIT = 1;

    private static final double LANDSCAPE_MAX = 1000;
    private static final double PORTRAIT_MAX = 500;

    private XYSeries series;
    private XYMultipleSeriesDataset dataset;
    private XYSeriesRenderer renderer = new XYSeriesRenderer();
    private XYMultipleSeriesRenderer multipleRenderer;
    private GraphicalView chartView;

    public ECGChart(Context context, int orientation) {
        series = new XYSeries("Electrocardiograma");
        init(context, orientation);
    }

    public ECGChart(Context context, int orientation, XYSeries series) {
        this.series = series;
        init(context, orientation);
    }

    private void init(Context context, int orientation) {
        dataset = new XYMultipleSeriesDataset();
        renderer = new XYSeriesRenderer();
        multipleRenderer = new XYMultipleSeriesRenderer();

        renderer.setLineWidth(5);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(false);

        multipleRenderer.addSeriesRenderer(renderer);
        multipleRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        multipleRenderer.setPanEnabled(true, false);
        multipleRenderer.setZoomEnabled(false, false);
        multipleRenderer.setClickEnabled(false);
        multipleRenderer.setYAxisMax(30);
        multipleRenderer.setYAxisMin(-30);
        multipleRenderer.setXAxisMin(0);
        double max = (orientation == PORTRAIT) ? PORTRAIT_MAX : LANDSCAPE_MAX;
        multipleRenderer.setXAxisMax(max);
        multipleRenderer.setAntialiasing(false);
        multipleRenderer.setShowGrid(true);
        multipleRenderer.setGridColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        multipleRenderer.setShowLegend(false);

        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        chartView = ChartFactory.getLineChartView(context, dataset, multipleRenderer);
        double [] panLimits = {0, 2000, 0, 0};
        multipleRenderer.setPanLimits(panLimits);
    }

    public void addData(double x, double y) {
        series.add(x, y);
    }

    public XYSeries getData() {
        return series;
    }

    public void setData(XYSeries series) {
        this.series.clear();
        dataset.clear();
        dataset.addSeries(series);
        chartView.repaint();
    }

    public void clearData() {
        series.clearSeriesValues();
        chartView.repaint();
    }

    public GraphicalView getView() {
        return chartView;
    }



    public void setScrollable(boolean scrollable) {
        multipleRenderer.setPanEnabled(scrollable, false);
    }
}
