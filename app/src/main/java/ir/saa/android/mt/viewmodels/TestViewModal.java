package ir.saa.android.mt.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.List;

import io.reactivex.Observable;
import ir.saa.android.mt.pojos.Pojo1;

public class TestViewModal extends ViewModel{
    public ObservableInt counter = null;


    public TestViewModal() {
        if (counter == null) {
            counter = new ObservableInt();
            counter.set(0);
        }

    }
    public void onCountUpClick(View view ,Integer someValue){
        counter.set(++someValue);
    }
}
