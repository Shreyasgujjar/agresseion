package com.example.agressiondetection;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class MainActivity extends AppCompatActivity {

    private LineChart acc_chart;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc_chart = (LineChart) findViewById(R.id.acc_data_chart);
        acc_chart.getDescription().setEnabled(true);
        acc_chart.getDescription().setText("RealTime Plot");
        acc_chart.setTouchEnabled(true);
        acc_chart.setDragEnabled(false);
        acc_chart.setScaleEnabled(false);
        acc_chart.setDrawGridBackground(false);
        acc_chart.setPinchZoom(false);
        acc_chart.setBackgroundColor(Color.WHITE);


        LineData lineData = new LineData();
        lineData.setValueTextColor(Color.WHITE);
        acc_chart.setData(lineData);

        Legend l = new Legend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = acc_chart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = acc_chart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = acc_chart.getAxisRight();
        rightAxis.setEnabled(false);

        acc_chart.getAxisLeft().setDrawGridLines(false);
        acc_chart.getXAxis().setDrawGridLines(false);
        acc_chart.setDrawBorders(false);

        startPlot();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(thread != null){
            thread.interrupt();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }

    private void startPlot(){
        if(thread != null){
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){

                    }
                }
            }
        });
    }

    public void addEntry(){
        LineData data = acc_chart.getData();

        if(data != null){
            ILineDataSet set = data.getDataSetByIndex(0);
            if(set == null){
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), 1+5), 0);
            data.notifyDataChanged();
            acc_chart.setMaxVisibleValueCount(150);
            acc_chart.moveViewToX(data.getEntryCount());
        }
    }

    private LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3f);
        set.setColor(Color.BLACK);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        return set;
    }
}
