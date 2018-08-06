package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.GetClientInput;
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.model.entities.Setting;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.repositories.roomrepos.AccessAgentAndroidRepo;
import ir.saa.android.mt.repositories.roomrepos.AgentAccessListRepo;
import ir.saa.android.mt.repositories.roomrepos.AnswerGroupDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.AnswerGroupRepo;
import ir.saa.android.mt.repositories.roomrepos.CityRepo;
import ir.saa.android.mt.repositories.roomrepos.ClientRepo;
import ir.saa.android.mt.repositories.roomrepos.ClientTypeRepo;
import ir.saa.android.mt.repositories.roomrepos.CompanyRepo;
import ir.saa.android.mt.repositories.roomrepos.GroupingFormatRepo;
import ir.saa.android.mt.repositories.roomrepos.MasterGroupDetailRepo;
import ir.saa.android.mt.repositories.roomrepos.MasterGroupInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompGroupRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompGroupingFormatRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompRepo;
import ir.saa.android.mt.repositories.roomrepos.PropertyTypeRepo;
import ir.saa.android.mt.repositories.roomrepos.RegionRepo;
import ir.saa.android.mt.repositories.roomrepos.ReluserRepo;
import ir.saa.android.mt.repositories.roomrepos.RemarkGroupRepo;
import ir.saa.android.mt.repositories.roomrepos.RemarkRepo;
import ir.saa.android.mt.repositories.roomrepos.RemarkTypeRepo;
import ir.saa.android.mt.repositories.roomrepos.SettingRepo;
import ir.saa.android.mt.repositories.roomrepos.TariffTypeRepo;

public class BaseInfoViewModel extends AndroidViewModel {
    RetrofitMT retrofitMT=null;

    AccessAgentAndroidRepo accessAgentAndroidRepo = null;
    AgentAccessListRepo agentAccessListRepo = null;
    AnswerGroupDtlRepo answerGroupDtlRepo = null;
    AnswerGroupRepo answerGroupRepo = null;
    CityRepo cityRepo = null;
    ClientRepo clientRepo = null;
    ClientTypeRepo clientTypeRepo = null;
    CompanyRepo companyRepo = null;
    GroupingFormatRepo groupingFormatRepo = null;
    MasterGroupDetailRepo masterGroupDetailRepo = null;
    MasterGroupInfoRepo masterGroupInfoRepo = null;
    PolompGroupingFormatRepo polompGroupingFormatRepo = null;
    PolompGroupRepo polompGroupRepo = null;
    PolompRepo polompRepo = null;
    PropertyTypeRepo propertyTypeRepo = null;
    RegionRepo regionRepo = null;
    ReluserRepo reluserRepo = null;
    RemarkGroupRepo remarkGroupRepo = null;
    RemarkRepo remarkRepo = null;
    RemarkTypeRepo remarkTypeRepo = null;
    TariffTypeRepo tariffTypeRepo = null;
    SettingRepo settingRepo=null;

    //---------------------------------------

    public MutableLiveData<Integer> UsersProgressPercentLiveData = null ;
    public MutableLiveData<Integer> settingProgressPercentLiveData=null;
    public MutableLiveData<Integer> clientProgressPercentLiveData=null;

    public BaseInfoViewModel(@NonNull Application application) {
        super(application);
        if(retrofitMT==null)
            retrofitMT= RetrofitMT.getInstance();
        if(accessAgentAndroidRepo==null)
            accessAgentAndroidRepo = new AccessAgentAndroidRepo(application);
        if(agentAccessListRepo==null)
            agentAccessListRepo = new AgentAccessListRepo(application);
        if(answerGroupDtlRepo==null)
            answerGroupDtlRepo = new AnswerGroupDtlRepo(application);
        if(answerGroupRepo==null)
            answerGroupRepo = new AnswerGroupRepo(application);
        if(cityRepo==null)
            cityRepo = new CityRepo(application);
        if(clientRepo==null)
            clientRepo = new ClientRepo(application);
        if(clientTypeRepo==null)
            clientTypeRepo = new ClientTypeRepo(application);
        if(companyRepo==null)
            companyRepo = new CompanyRepo(application);
        if(groupingFormatRepo==null)
            groupingFormatRepo = new GroupingFormatRepo(application);
        if(masterGroupDetailRepo==null)
            masterGroupDetailRepo = new MasterGroupDetailRepo(application);
        if(masterGroupInfoRepo==null)
            masterGroupInfoRepo = new MasterGroupInfoRepo(application);
        if(polompGroupingFormatRepo==null)
            polompGroupingFormatRepo = new PolompGroupingFormatRepo(application);
        if(polompGroupRepo==null)
            polompGroupRepo = new PolompGroupRepo(application);
        if(polompRepo==null)
            polompRepo = new PolompRepo(application);
        if(propertyTypeRepo==null)
            propertyTypeRepo = new PropertyTypeRepo(application);
        if(regionRepo==null)
            regionRepo = new RegionRepo(application);
        if(reluserRepo==null)
            reluserRepo = new ReluserRepo(application);
        if(remarkGroupRepo==null)
            remarkGroupRepo = new RemarkGroupRepo(application);
        if(remarkRepo==null)
            remarkRepo = new RemarkRepo(application);
        if(remarkTypeRepo==null)
            remarkTypeRepo = new RemarkTypeRepo(application);
        if(tariffTypeRepo==null)
            tariffTypeRepo = new TariffTypeRepo(application);
        if(settingRepo==null)
            settingRepo=new SettingRepo(application);

        //---------------------------------------------
        if(UsersProgressPercentLiveData == null)
            UsersProgressPercentLiveData.setValue(0);

        if(settingProgressPercentLiveData==null)
            settingProgressPercentLiveData.setValue(0);

        if(clientProgressPercentLiveData==null)
            clientProgressPercentLiveData.setValue(0);



        //getAnswerGroupsFromServer();
        //getCitiesFromServer();
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

    public void getUserFromServer(){
        retrofitMT.getMtApi().GetAgentList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<RelUser>>() {
                    @Override
                    public void onSuccess(List<RelUser> userList) {
//                        if(reluserRepo.getUsers().getValue().size()>0)
//                            reluserRepo.deleteAll();
                        //UsersProgressPercentLiveData.postValue(getPercent(x,y));
                        //List<Long> insertedIdList = reluserRepo.insertUsers(userList);
                        //Toast.makeText(getApplication().getApplicationContext(),"insertCount : "+insertedIdList.size(),Toast.LENGTH_SHORT).show();
                        for(Integer i=0;i<userList.size();i++){
                            reluserRepo.insertUser(userList.get(i));
                            UsersProgressPercentLiveData.postValue(getPrecent(i,userList.size()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getSettingFromServer(){
        retrofitMT.getMtApi().GetSettings()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<List<Setting>>() {
            @Override
            public void onSuccess(List<Setting> settings) {
                if(settingRepo.getSettings().getValue().size()>0){
                    settingRepo.deleteAll();
                }
                for(Integer i=0;i<settings.size();i++){
                    settingRepo.insertSetting(settings.get(i));
                    settingProgressPercentLiveData.postValue(getPrecent(i,settings.size()));
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        })
        ;
    }

    public void getClientFromServer(GetClientInput getClientInput){
        retrofitMT.getMtApi().GetClients(getClientInput)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Client>>() {
                    @Override
                    public void onSuccess(List<Client> clients) {
                        for(Integer i=0;i<clients.size();i++){
                            clientRepo.insertClient(clients.get(i));
                            clientProgressPercentLiveData.postValue(getPrecent(i,clients.size()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int getPrecent(int progress,int totalCount){
        return (progress * 100)/totalCount;
    }

}
