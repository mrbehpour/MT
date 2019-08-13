package ir.saa.android.mt.uicontrollers.activities;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.repositories.metertester.MT;

public class InfoActivity extends AppCompatActivity {

    TextView tvSoftName,tvProducer,tvCustomer,tvVersion,tvReleaseDate,tvCorrectCoeff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tvSoftName=findViewById(R.id.tvSoftName);
        tvProducer=findViewById(R.id.tvProducer);
        tvCustomer=findViewById(R.id.tvCustomerName);
        tvVersion=findViewById(R.id.tvVersion);
        tvReleaseDate=findViewById(R.id.tvReleaseDate);
        tvCorrectCoeff=findViewById(R.id.tvCorrectCoeff);

        tvSoftName.setText(String.format("%s:   %s", G.context.getText(R.string.SoftName),G.context.getText(R.string.SOFT_NAME)));
        tvProducer.setText(String.format("%s:   %s", G.context.getText(R.string.ProducerName),G.context.getText(R.string.PRODUCER_NAME)));
        tvCustomer.setText(String.format("%s:   %s", G.context.getText(R.string.CustomerName),G.context.getText(R.string.CUSTOMER_NAME)));
        tvVersion.setText(String.format("%s:   %s", G.context.getText(R.string.Version),G.MY_VERSION));
        tvReleaseDate.setText(String.format("%s:   %s", G.context.getText(R.string.ReleaseDate),G.RELEASE_DATE));
        tvCorrectCoeff.setText(String.format("%s:   %s", G.context.getText(R.string.CorrectCoeff), String.valueOf(MT.correctFactor)));
    }
}
