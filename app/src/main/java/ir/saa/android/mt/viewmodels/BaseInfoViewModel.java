package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.model.entities.AccessAgentAndroid;
import ir.saa.android.mt.model.entities.AgentAccessList;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.City;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.GetClientInput;
import ir.saa.android.mt.model.entities.GroupingFormat;
import ir.saa.android.mt.model.entities.MasterGroupDetail;
import ir.saa.android.mt.model.entities.Polomp;
import ir.saa.android.mt.model.entities.PolompGroup;
import ir.saa.android.mt.model.entities.PolompGroupingFormat;
import ir.saa.android.mt.model.entities.PropertyType;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroup;
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

    public MutableLiveData<Integer> UsersProgressPercentLiveData = new MutableLiveData<>() ;
    public MutableLiveData<Integer> settingProgressPercentLiveData=new MutableLiveData<>();
    public MutableLiveData<Integer> clientProgressPercentLiveData=new MutableLiveData<>();
    public MutableLiveData<Integer> baseinfoProgressPercentLiveData=new MutableLiveData<>();


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

        if(baseinfoProgressPercentLiveData==null)
            baseinfoProgressPercentLiveData.setValue(0);



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
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                 List<RelUser> relUsers=null;
                 List<AgentAccessList> agentAccessLists=null;
                 List<AccessAgentAndroid> accessAgentAndroids=null;

                relUsers=retrofitMT.getMtApi().GetAgentList().blockingGet();
                ArrayList<Integer> agentIdList=new ArrayList<>();

                for(Integer i=0;i<relUsers.size();i++){
                    agentIdList.add(relUsers.get(i).UserID);
                }
                accessAgentAndroids=retrofitMT.getMtApi().GetAgentAccessByUserIdList(agentIdList).blockingGet();
                agentAccessLists=retrofitMT.getMtApi().GetAgentAccessList().blockingGet();

                Integer totalCount=relUsers.size()+agentAccessLists.size()+accessAgentAndroids.size();
                Integer startProgress=0;
                for(Integer i=0;i<relUsers.size();i++){
                    reluserRepo.insertUser(relUsers.get(i));
                    UsersProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=relUsers.size();

                for(Integer i=0;i<accessAgentAndroids.size();i++){
                    accessAgentAndroidRepo.insertAccessAgentAndroid(accessAgentAndroids.get(i));
                    UsersProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=relUsers.size()+accessAgentAndroids.size();

                for(Integer i=0;i<agentAccessLists.size();i++){
                    agentAccessListRepo.insertAgentAccessList(agentAccessLists.get(i));
                    UsersProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }




            }
        }).subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                       // Toast.makeText(getApplication().getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
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
        //.observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<List<Setting>>() {
            @Override
            public void onSuccess(List<Setting> settings) {
//                if(settingRepo.getSettings().getValue().size()>0){
//                    settingRepo.deleteAll();
//                }
                for(Integer i=0;i<settings.size();i++){
                    settingRepo.insertSetting(settings.get(i));
                    settingProgressPercentLiveData.postValue(getPrecent(i+1,settings.size()));
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
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Client>>() {
                    @Override
                    public void onSuccess(List<Client> clients) {
                        for(Integer i=0;i<clients.size();i++){
                            clientRepo.insertClient(clients.get(i));
                            clientProgressPercentLiveData.postValue(getPrecent(i+1,clients.size()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                });

    }

    public void getBaseInfoFromServer(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                List<ir.saa.android.mt.model.converters.AnswerGroup> answerGroups=null;

                List<ir.saa.android.mt.model.converters.AnswerGroupDtl> answerGroupDtls=new ArrayList<>();
                List<PropertyType> propertyTypes=null;
                List<Region>  regions=null;
                List<City> cities=null;
                List<Remark> remarks=null;
                List<GroupingFormat> groupingFormats=null;
                List<RemarkGroup> remarkGroups=null;
                List<MasterGroupDetail> masterGroupDetails=null;
                List<Polomp> polomps=null;
                List<PolompGroup> polompGroups=null;
                List<PolompGroupingFormat> polompGroupingFormats=null;

                answerGroups=retrofitMT.getMtApi().GetAnswerGroups().blockingGet();

//                for(Integer i=0;i<answerGroups.size();i++){
//                    if(answerGroups.get(i)!=null){
//                        for(Integer j=0;j<answerGroups.get(i).size();j++){
//                            answerGroupDtls.add(answerGroups.get(i).answerGroupDtls.get(j));
//                        }
//                    }
//
//                }
                propertyTypes=retrofitMT.getMtApi().GetPropertyTypies().blockingGet();
                regions=retrofitMT.getMtApi().GetRegions().blockingGet();
                cities=retrofitMT.getMtApi().GetCities().blockingGet();
                List<AnswerGroupDtl> ClientTarif=null;
                ClientTarif=retrofitMT.getMtApi().GetClientsTariff().blockingGet();
                for (Integer i=0;i<ClientTarif.size();i++){
                    //answerGroupDtls.add(ClientTarif.get(i));
                }
                remarks=retrofitMT.getMtApi().GetRemarks().blockingGet();
                groupingFormats=retrofitMT.getMtApi().GetGroupingFormat().blockingGet();
                remarkGroups=retrofitMT.getMtApi().GetRemarkGroup().blockingGet();
                masterGroupDetails=retrofitMT.getMtApi().GetMasterGroupDtl().blockingGet();
                polomps=retrofitMT.getMtApi().GetPolomps().blockingGet();
                polompGroups=retrofitMT.getMtApi().GetPolompGroup().blockingGet();
                polompGroupingFormats=retrofitMT.getMtApi().GetPolompGroupingFormat().blockingGet();

                Integer totalCount=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+
                        regions.size()+cities.size()+remarks.size()+groupingFormats.size()+
                        remarkGroups.size()+masterGroupDetails.size()+polomps.size()+polompGroups.size()+
                        polompGroupingFormats.size();
                Integer startProgress=0;
                for (Integer i=0;i<answerGroups.size();i++){
                    //answerGroupRepo.insertAnswerGroup(answerGroups.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size();
                for (Integer i=0;i<answerGroupDtls.size();i++){
                    //answerGroupDtlRepo.insertAnswerGroupdtl(answerGroupDtls.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size();
                for (Integer i=0;i<propertyTypes.size();i++){
                    propertyTypeRepo.insertPropertyType(propertyTypes.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size();
                for (Integer i=0;i<regions.size();i++){
                    regionRepo.insertRegion(regions.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size();
                for (Integer i=0;i<cities.size();i++){
                    cityRepo.insertCity(cities.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size()+
                        cities.size();
                for (Integer i=0;i<remarks.size();i++){
                    remarkRepo.insertRemark(remarks.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size()+
                        cities.size()+remarks.size();
                for (Integer i=0;i<remarkGroups.size();i++){
                    remarkGroupRepo.insertRemarkGroup(remarkGroups.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size()+
                        cities.size()+remarks.size()+remarkGroups.size();
                for (Integer i=0;i<groupingFormats.size();i++){
                    groupingFormatRepo.insertGroupingFormat(groupingFormats.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size()+
                        cities.size()+remarks.size()+remarkGroups.size()+groupingFormats.size();

                for (Integer i=0;i<masterGroupDetails.size();i++){
                    masterGroupDetailRepo.insertMasterGroupDetail(masterGroupDetails.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size()+
                        cities.size()+remarks.size()+remarkGroups.size()+groupingFormats.size()+masterGroupDetails.size()+
                        masterGroupDetails.size();

                for (Integer i=0;i<polomps.size();i++){
                    polompRepo.insertPolomp(polomps.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size()+
                        cities.size()+remarks.size()+remarkGroups.size()+groupingFormats.size()+masterGroupDetails.size()+
                        masterGroupDetails.size()+polomps.size();

                for (Integer i=0;i<polompGroups.size();i++){
                    polompGroupRepo.insertPolompGroup(polompGroups.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }
                startProgress=answerGroups.size()+answerGroupDtls.size()+propertyTypes.size()+regions.size()+
                        cities.size()+remarks.size()+remarkGroups.size()+groupingFormats.size()+masterGroupDetails.size()+
                        masterGroupDetails.size()+polomps.size()+polompGroups.size();

                for (Integer i=0;i<polompGroupingFormats.size();i++){
                    polompGroupingFormatRepo.insertPolompGroupingFormat(polompGroupingFormats.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress+(i+1),totalCount));
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //Toast.makeText(getApplication().getApplicationContext(),d.,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplication().getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
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
