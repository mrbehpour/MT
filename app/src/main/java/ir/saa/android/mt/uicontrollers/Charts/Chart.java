package ir.saa.android.mt.uicontrollers.Charts;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * Created by h.mohammadi on 12/17/2017.
 */

public class Chart {

    public void addDataSetForPieChart(PieChart pieChart,float[] yData,String[] xData) {
        ArrayList<PieEntry> yEntrys=new ArrayList<>();
        ArrayList<String> xEntery=new ArrayList<>();

        for(int i=0;i<yData.length;i++){
            yEntrys.add(new PieEntry(yData[i]));
        }

        for(int i=1;i<xData.length;i++){
            xEntery.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"آمار کلی");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);



        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.RED);
        pieDataSet.setColors(colors);
        ArrayList<Integer> colorsText=new ArrayList<>();
        colorsText.add(Color.BLACK);
        colorsText.add(Color.BLACK);
        colorsText.add(Color.BLACK);
        pieDataSet.setValueTextColors(colorsText);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();


    }

}
