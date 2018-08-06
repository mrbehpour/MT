package ir.saa.android.mt.repositories.roomrepos;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import  ir.saa.android.mt.model.daos.*;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.RelUser;

public class ReluserRepo  {
    private RelUserDao relUserDao;
    private LiveData<List<RelUser>> relUsers;
    public ReluserRepo(Application application) {
        MTDatabase db = MTDatabase.getDatabase(application);
        relUserDao = db.userModel();
        relUsers = relUserDao.getUsers();
    }

    public LiveData<List<RelUser>> getUsers() {
        return relUsers;
    }

    public List<Long> insertUsers(List<RelUser> relUserList){
        return relUserDao.insertRelusers(relUserList);
    }

    public void insertUsersAsync(List<RelUser> relUserList) {
        Single.fromCallable(new Callable<List<Long>>() {
            @Override
            public List<Long> call() throws Exception {
                return relUserDao.insertRelusers(relUserList);
            }
        })
            .subscribeOn(Schedulers.io())
            .subscribeWith(new DisposableSingleObserver<List<Long>>() {
                @Override
                public void onSuccess(List<Long> insertedIds) {

                }

                @Override
                public void onError(Throwable e) {

                }
            });
    }

    public void updateUserAsync(RelUser relUser) {

        Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return relUserDao.updateRelUser(relUser);
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer id) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }
    public long updateUser(RelUser relUser) {
        return relUserDao.updateRelUser(relUser);
    }

    public void deleteAll(){
        relUserDao.deleteAll();
    }

    public RelUser getUserByUserAndPassword(int userId,String passWord){
        return  relUserDao.getUserByUserAndPassword(userId,passWord);
    }

  public  void insertUser(RelUser relUser){
        relUserDao.insertReluser(relUser);
    }

    public LiveData<RelUser> getUserById(int userId){
        return  relUserDao.getUserById(userId);
    }


}
