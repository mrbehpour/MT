package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ir.saa.android.mt.repositories.model.database.MTDatabase;
import ir.saa.android.mt.repositories.model.entities.RelUser;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;

public class UserViewModel extends AndroidViewModel {
    MTDatabase mtDatabase=null;

    public UserViewModel(@NonNull Application application) {
        super(application);
        if(mtDatabase==null)
            mtDatabase = MTDatabase.getInMemoryDatabase(getApplication().getApplicationContext());
            List<RelUser> lst = mtDatabase.userModel().getUsers().getValue();


            RetrofitMT.getInstance().getMtApi().GetAgentList();


    }


//    Retrofit.Builder()
//            .baseUrl(BuildConfig.END_POINT)
//        .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build();
}
