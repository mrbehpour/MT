package ir.saa.android.mt.uicontrollers.Charts;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Typeface;
import android.media.audiofx.LoudnessEnhancer;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;

/**
 * Created by h.mohammadi on 12/17/2017.
 */

public class Chart {

    public void addDataSetForPieChart(PieChart pieChart,Integer[] yData,String[] xData) {
        ArrayList<PieEntry> yEntrys=new ArrayList<>();
        ArrayList<String> xEntery=new ArrayList<>();

        for(int i=0;i<yData.length;i++){

                yEntrys.add(new PieEntry(yData[i], xData[i]));

        }

        for(int i=1;i<xData.length;i++){

                xEntery.add(xData[i]);

        }


        PieDataSet pieDataSet=new PieDataSet(yEntrys, (String) G.context.getResources().getText(R.string.ChartAmarTitle));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20);



        final Typeface tfByekan = Typeface.createFromAsset(G.context.getAssets(), "fonts/byekan.ttf");
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(G.context.getResources().getColor(R.color.blue_300));
        colors.add(G.context.getResources().getColor(R.color.red_300));
        colors.add(G.context.getResources().getColor(R.color.orange_300));
        colors.add(G.context.getResources().getColor(R.color.green_300));

        ArrayList<Integer> colorsText=new ArrayList<>();
        colorsText.add(Color.WHITE);
        colorsText.add(Color.WHITE);
        colorsText.add(Color.WHITE);
        colorsText.add(Color.WHITE);
        pieDataSet.setValueTextColors(colorsText);
        pieDataSet.setValueTypeface(tfByekan);
        pieDataSet.setHighlightEnabled(true);


        LegendEntry legendEntryTest=new LegendEntry();
        LegendEntry legendEntryBazrasi=new LegendEntry();
        LegendEntry legendEntryPolomp=new LegendEntry();
        LegendEntry legendEntryTariff=new LegendEntry();

        legendEntryTest.label= (String) G.context.getResources().getText(R.string.PieTestTitle);
        legendEntryTest.form= Legend.LegendForm.SQUARE;
        legendEntryTest.formSize=20;
        legendEntryTest.formColor=G.context.getResources().getColor(R.color.blue_300);

        legendEntryBazrasi.label=(String) G.context.getResources().getText(R.string.PieBazrasiTitle);
        legendEntryBazrasi.form= Legend.LegendForm.SQUARE;
        legendEntryBazrasi.formColor=G.context.getResources().getColor(R.color.red_300);
        legendEntryBazrasi.formSize=20;

        legendEntryPolomp.label=(String) G.context.getResources().getText(R.string.PiePolompTitle);
        legendEntryPolomp.form= Legend.LegendForm.SQUARE;
        legendEntryPolomp.formColor=G.context.getResources().getColor(R.color.orange_300);
        legendEntryPolomp.formSize=20;

        legendEntryTariff.label=(String) G.context.getResources().getText(R.string.PieTariffTitle);
        legendEntryTariff.form= Legend.LegendForm.SQUARE;
        legendEntryTariff.formColor=G.context.getResources().getColor(R.color.green_300);
        legendEntryTariff.formSize=20;

        LegendEntry[] legendEntries={legendEntryTest,legendEntryBazrasi,legendEntryPolomp,legendEntryTariff};

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setTextSize(20);
        legend.setTypeface(tfByekan);
        legend.setCustom(legendEntries);

        for(int i=0;i<pieDataSet.getEntryCount();i++){
            if(pieDataSet.getEntryForIndex(i).getValue()==0){
               pieDataSet.removeEntry(i);
               colors.remove(i);
               }

        }
        pieDataSet.setColors(colors);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();


    }

}
