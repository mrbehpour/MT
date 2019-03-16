package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.Report;
import ir.saa.android.mt.uicontrollers.Charts.Chart;
import ir.saa.android.mt.viewmodels.BazdidViewModel;

public class ReportActivity extends AppCompatActivity {

    TextView tvTest;
    TextView tvBazrasi;
    TextView tvPolomp;
    TextView tvTariff;
    PieChart pieChartBazdid;
    AppCompatButton btnExit;
    static  Integer[] arryX={0,0,0,0};
    static  String[] arryY={"","","",""};
    BazdidViewModel bazdidViewModel=null;
    public  void adjustFontScale(Configuration configuration, Float fontSize) {

        configuration.fontScale = (float) fontSize;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

    }
    final Typeface tfByekan = Typeface.createFromAsset(G.context.getAssets(), "fonts/byekan.ttf");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
        }

        bazdidViewModel= ViewModelProviders.of(this).get(BazdidViewModel.class);

        tvTest=(TextView) findViewById(R.id.tvTest);
        tvBazrasi=(TextView) findViewById(R.id.tvBazrasi);
        tvPolomp=(TextView) findViewById(R.id.tvPolomp);
        tvTariff=(TextView) findViewById(R.id.tvGheraat);
        btnExit=(AppCompatButton)findViewById(R.id.btnExit);

        pieChartBazdid=(PieChart)findViewById(R.id.pieChartBazdid);


        pieChartBazdid.setRotationEnabled(true);
        pieChartBazdid.setCenterTextTypeface(tfByekan);
        pieChartBazdid.setEntryLabelTypeface(tfByekan);

        pieChartBazdid.setTransparentCircleAlpha(20);
        pieChartBazdid.setCenterText(getResources().getText(R.string.ChartTitle));
        pieChartBazdid.setCenterTextSize(30);
        pieChartBazdid.setEntryLabelTextSize(25);
        pieChartBazdid.animateY(2000);
        pieChartBazdid.animateX(2000);
        Description description=new Description();
        description.setText("");
        pieChartBazdid.setDescription(description);
        pieChartBazdid.setNoDataText((String) getResources().getText(R.string.PieChartNoData));
        pieChartBazdid.setNoDataTextTypeface(tfByekan);
        pieChartBazdid.setNoDataTextColor(Color.parseColor("#000000"));

        //pieChartBazdid.setDrawEntryLabels(true);
        Chart chart=new Chart();
        arryY[0]= (String) getResources().getText(R.string.PieTestTitle);
        arryY[1]=(String) getResources().getText(R.string.PieBazrasiTitle);;
        arryY[2]=(String) getResources().getText(R.string.PiePolompTitle);;
        arryY[3]=(String) getResources().getText(R.string.PieTariffTitle);;
        bazdidViewModel.getReport().observe(this, new Observer<Report>() {
            @Override
            public void onChanged(@Nullable Report report) {
                if(report!=null){
                    tvBazrasi.setText(report.BazrasiCount.toString());
                    tvTest.setText(report.TestCount.toString());
                    tvTariff.setText(report.TriffCount.toString());
                    tvPolomp.setText(report.PolompCount.toString());
                    arryX[0]=Integer.parseInt(String.format("%.0f",(float)report.TestCount));
                    arryX[1]=Integer.parseInt(String.format("%.0f",(float)report.BazrasiCount));
                    arryX[2]=Integer.parseInt(String.format("%.0f",(float)report.PolompCount));
                    arryX[3]=Integer.parseInt(String.format("%.0f",(float)report.TriffCount));
                    chart.addDataSetForPieChart(pieChartBazdid,arryX,arryY);
                }
            }
        });

        pieChartBazdid.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1=e.toString().indexOf("y: ");
                String count=String.format("%.0f", Double.parseDouble( e.toString().substring(pos1+3)));

                for (int i=0; i<arryX.length;i++) {
                    if (arryX[i] == Float.parseFloat(count)) {
                        pos1 = i;
                        break;
                    }
                }
                String action=arryY[pos1];
                Toast fancyToast = FancyToast.makeText(ReportActivity.this, getResources().getText(R.string.tedadTitle)
                        +" "+action.toString()+" : "+ count, FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                fancyToast.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportActivity.this.finish();
            }
        });
    }
}
