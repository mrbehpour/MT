package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.pojos.Pojo1;
import ir.saa.android.mt.viewmodels.TestViewModal;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TestViewModal testViewModal = ViewModelProviders.of(this).get(TestViewModal.class);
//        ActivityTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test);// ActivityTestBinding.inflate(getLayoutInflater());
//        binding.setViewmodel(testViewModal);

//        testViewModal.getPojos1().observe(this, pojo1s -> {
//
//        });
//        testViewModal.getPojos1().getValue().add(new Pojo1("Hassan",27,"Programmer"));
//        testViewModal.getPojos1().getValue().add(new Pojo1("Iman",27,"Developer"));
//        testViewModal.getPojos1().getValue().add(new Pojo1("Hamid",27,"Room Designer"));

    }
}
