package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.model.entities.AddedClientInput;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.repositories.retrofit.DataClass;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;

public class AddedClientViewModel extends AndroidViewModel {
    RetrofitMT retrofitMT=null;

    public MutableLiveData<List<Client>> ClientListMultiLiveData = new MutableLiveData<>();

    public AddedClientViewModel(@NonNull Application application) {
        super(application);
        if(retrofitMT==null)
            retrofitMT= RetrofitMT.getInstance();
    }

    public void getClientFromServer(AddedClientInput addedClientInput){
        retrofitMT.getMtApi().GetClientsForAddedClient(addedClientInput)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<List<Client>>>() {
                    @Override
                    public void onSuccess(DataClass<List<Client>> listDataClass) {

                        if(listDataClass.Data.size()!=0){
                            ClientListMultiLiveData.postValue(listDataClass.Data);
                        }else{
                            ClientListMultiLiveData.postValue(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
