package com.example.ksala.healthproject.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.ksala.healthproject.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class ECGChart {

    private LineChart mChart;
    private int beats;
    private long totalTime;

    public ECGChart(LineChart lineChart) {

        beats = 0;
        totalTime = 0;

        mChart = lineChart;
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        // add an empty data object
        mChart.setData(new LineData());
        //mChart.getXAxis().setDrawLabels(false);
        //mChart.getXAxis().setDrawGridLines(false);

        mChart.invalidate();
    }

    public void addData(double x, double y) {

        if (y >= 850) ++beats;
        totalTime = (long) x;

        LineData data = mChart.getData();

        if(data != null) {

            LineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            // add a new x-value first
            data.addXValue(x + "");

            data.addEntry(new Entry((float) y, data.getXValCount() - 1), 0);

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            mChart.setVisibleXRangeMaximum(70);
            mChart.setVisibleYRangeMaximum(2000, YAxis.AxisDependency.LEFT);
//
//          // this automatically refreshes the chart (calls invalidate())
            mChart.moveViewTo(data.getXValCount()-7, 50f, YAxis.AxisDependency.LEFT);

            Log.d(Utils.LOG_TAG, "" + y);
        }
    }

    public void clearData() {
        beats = 0;
        totalTime = 0;
        mChart.clear();
        mChart.setData(new LineData());
    }

    public LineChart getView() {
        return mChart;
    }

    public double getBPM() {
        return (double) beats / ((double) totalTime / (double) 60000);
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Electrocardiography");
        set.setLineWidth(2.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleSize(0);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(0f);

        return set;
    }
}
