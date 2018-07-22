package ir.saa.android.mt.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.List;
import java.util.Observable;

import ir.saa.android.mt.pojos.Pojo1;

public class TestViewModal extends ViewModel{
    private MutableLiveData<List<Pojo1>> pojos1 = null;
    public ObservableInt counter = null;

    public TestViewModal() {
        if (counter == null) {
            counter = new ObservableInt();
            counter.set(0);
        }

    }

    public MutableLiveData<List<Pojo1>> getPojos1(){
        if (pojos1 == null) {
            pojos1 = new MutableLiveData<>();
        }
        return pojos1;
    }

    public void onCountUpClick(View view ,Integer someValue){
        counter.set(++someValue);
    }
}
