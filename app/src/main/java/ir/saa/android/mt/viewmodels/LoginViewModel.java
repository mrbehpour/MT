package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.City;
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;

public class LoginViewModel extends AndroidViewModel {
    MTDatabase mtDatabase=null;
    RetrofitMT retrofitMT=null;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        if(mtDatabase==null)
            mtDatabase = MTDatabase.getDatabase(getApplication().getApplicationContext());
        if(retrofitMT==null)
            retrofitMT= RetrofitMT.getInstance();
        //getAnswerGroupsFromServer();
        getCitiesFromServer();
        //getUserFromServer();
    }


//    public void getAnswerGroupsFromServer(){
//        retrofitMT.getMtApi().GetAnswerGroups()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<AnswerGroup>>() {
//                    @Override
//                    public void onSuccess(List<AnswerGroup> answerGroupList) {
//                        if(mtDatabase.userModel().getUsers().getValue().size()>0)
//                            mtDatabase.userModel().deleteAll();
//                        List<Long> insertedIdList = mtDatabase.answerGroupModel().insertAnswerGroups(answerGroupList);
//                        Toast.makeText(getApplication().getApplicationContext(),"insertCount : "+insertedIdList.size(),Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    public void getCitiesFromServer(){
        retrofitMT.getMtApi().GetCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<City>>() {
                    @Override
                    public void onSuccess(List<City> cityList) {
                        if(mtDatabase.cityModel().getCities().getValue() != null && mtDatabase.cityModel().getCities().getValue().size()>0)
                            mtDatabase.cityModel().deleteAll();
                        List<Long> insertedIdList = mtDatabase.cityModel().insertCities(cityList);
                        Toast.makeText(getApplication().getApplicationContext(),"insertCount : "+insertedIdList.size(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getUserFromServer(){
        retrofitMT.getMtApi().GetAgentList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<RelUser>>() {
                    @Override
                    public void onSuccess(List<RelUser> userList) {
                        if(mtDatabase.userModel().getUsers().getValue().size()>0)
                            mtDatabase.userModel().deleteAll();
                        List<Long> insertedIdList = mtDatabase.userModel().insertRelusers(userList);
                        Toast.makeText(getApplication().getApplicationContext(),"insertCount : "+insertedIdList.size(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
