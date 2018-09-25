package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.repositories.roomrepos.RegionRepo;
import ir.saa.android.mt.repositories.roomrepos.ReluserRepo;

public class LoginViewModel extends AndroidViewModel {
    ReluserRepo reluserRepo = null;
    RegionRepo regionRepo=null;
    RetrofitMT retrofitMT=null;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        if(regionRepo==null)
            regionRepo=new RegionRepo(application);
        if(reluserRepo==null)
            reluserRepo = new ReluserRepo(application);
        if(retrofitMT==null)
            retrofitMT= RetrofitMT.getInstance();
        //getAnswerGroupsFromServer();
        //getCitiesFromServer();
        //getUserFromServer();

        initializerUser();
    }

    private void initializerUser(){
        List<RelUser> relUsers=null;

        relUsers=reluserRepo.getUsers();
        if(relUsers.size()==0){
            RelUser relUser=new RelUser();
            relUser.FirstName="مدیر سیستم";
            relUser.LastName="";
            relUser.HandheldPass="1";
            reluserRepo.insertUser(relUser);
            //111111
        }
    }
    public LiveData<List<RelUser>> getUsers() {  return reluserRepo.getUsersLiveData();  }


    public boolean IsLoginValid(int userId,String password){
//       RelUser user = loginViewModel.getUserByUserIdAndPassword(spinnerMap.get(spinner.getSelectedItemPosition()),edtPassword.getText().toString());
        RelUser user = reluserRepo.getUserByUserAndPassword(userId,password);
        if(user!=null) {
            G.setPref("UserName" ,user.FirstName+" "+user.LastName );
            if(user.RegionID!=null){
                G.setPref("RegionName",getRegionNameById(user.RegionID));
            }
            else{
                G.setPref("RegionName","ندارد");
            }

            if(user.RoleID!=null)
                G.setPref("RoleId",user.RoleID.toString());
            else
                G.setPref("RoleId","-1");

            G.setPref("UserID", (Integer.toString(user.UserID)));

            return true;
        }
        else
            return false;
    }

        private String getRegionNameById(Short Id){
            return regionRepo.getRegionById(Id).RegionName;
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

//    public void getCitiesFromServer(){
//        retrofitMT.getMtApi().GetCities()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<City>>() {
//                    @Override
//                    public void onSuccess(List<City> cityList) {
//                        if(mtDatabase.cityModel().getCities().getValue() != null && mtDatabase.cityModel().getCities().getValue().size()>0)
//                            mtDatabase.cityModel().deleteAll();
//                        List<Long> insertedIdList = mtDatabase.cityModel().insertCities(cityList);
//                        Toast.makeText(getApplication().getApplicationContext(),"insertCount : "+insertedIdList.size(),Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

//    public void getUserFromServer(){
//        retrofitMT.getMtApi().GetAgentList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<RelUser>>() {
//                    @Override
//                    public void onSuccess(List<RelUser> userList) {
//                        if(reluserRepo.getUsers().getValue().size()>0)
//                            reluserRepo.deleteAll();
//                        List<Long> insertedIdList = reluserRepo.insertUsers(userList);
//                        Toast.makeText(getApplication().getApplicationContext(),"insertCount : "+insertedIdList.size(),Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

}
