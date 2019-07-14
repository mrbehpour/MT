package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.daos.MenuDao;
import ir.saa.android.mt.model.entities.AccessAgentAndroid;
import ir.saa.android.mt.model.entities.AgentAccessList;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.Bazdid;
import ir.saa.android.mt.model.entities.City;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.GetClientInput;
import ir.saa.android.mt.model.entities.GroupingFormat;
import ir.saa.android.mt.model.entities.MasterGroupDetail;
import ir.saa.android.mt.model.entities.Menu;
import ir.saa.android.mt.model.entities.Polomp;
import ir.saa.android.mt.model.entities.PolompColor;
import ir.saa.android.mt.model.entities.PolompGroup;
import ir.saa.android.mt.model.entities.PolompGroupingFormat;
import ir.saa.android.mt.model.entities.PolompType;
import ir.saa.android.mt.model.entities.PropertyType;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroup;
import ir.saa.android.mt.model.entities.Setting;
import ir.saa.android.mt.repositories.retrofit.DataClass;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.repositories.roomrepos.AccessAgentAndroidRepo;
import ir.saa.android.mt.repositories.roomrepos.AgentAccessListRepo;
import ir.saa.android.mt.repositories.roomrepos.AnswerGroupDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.AnswerGroupRepo;
import ir.saa.android.mt.repositories.roomrepos.BazdidRepo;
import ir.saa.android.mt.repositories.roomrepos.CityRepo;
import ir.saa.android.mt.repositories.roomrepos.ClientRepo;
import ir.saa.android.mt.repositories.roomrepos.ClientTypeRepo;
import ir.saa.android.mt.repositories.roomrepos.CompanyRepo;
import ir.saa.android.mt.repositories.roomrepos.GroupingFormatRepo;
import ir.saa.android.mt.repositories.roomrepos.MasterGroupDetailRepo;
import ir.saa.android.mt.repositories.roomrepos.MasterGroupInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.MenuRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompColorRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompGroupRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompGroupingFormatRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompTypeRepo;
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
    PolompTypeRepo polompTypeRepo=null;
    PolompColorRepo polompColorRepo=null;
    BazdidRepo bazdidRepo=null;
    MenuRepo menuRepo=null;

    //---------------------------------------

    public MutableLiveData<Integer> UsersProgressPercentLiveData = new MutableLiveData<>() ;
    public MutableLiveData<Integer> settingProgressPercentLiveData=new MutableLiveData<>();
    public MutableLiveData<Integer> clientProgressPercentLiveData=new MutableLiveData<>();
    public MutableLiveData<Integer> baseinfoProgressPercentLiveData=new MutableLiveData<>();
    public MutableLiveData<String> messageErrorLiveData=new MutableLiveData<>();


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
        if(polompColorRepo==null)
            polompColorRepo=new PolompColorRepo(application);
        if(polompTypeRepo==null)
            polompTypeRepo=new PolompTypeRepo(application);
        if(bazdidRepo==null){
            bazdidRepo=new BazdidRepo(application);
        }

        if(menuRepo==null){
            menuRepo=new MenuRepo(application);
        }

        //---------------------------------------------
        if(UsersProgressPercentLiveData == null)
            UsersProgressPercentLiveData.setValue(0);

        if(settingProgressPercentLiveData==null)
            settingProgressPercentLiveData.setValue(0);

        if(clientProgressPercentLiveData==null)
            clientProgressPercentLiveData.setValue(0);

        if(baseinfoProgressPercentLiveData==null)
            baseinfoProgressPercentLiveData.setValue(0);
        if(messageErrorLiveData==null)
            messageErrorLiveData.setValue("");



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

    public List<Bazdid> getBazdidIsSend(Boolean hasSend){
        return bazdidRepo.getBazdidIsSend(hasSend);
    }

    public void getUserFromServer(){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                DataClass<List<RelUser>> relUsers = null;
                DataClass<List<AgentAccessList>> agentAccessLists = null;
                DataClass<List<AccessAgentAndroid>> accessAgentAndroids = null;

                relUsers = retrofitMT.getMtApi().GetAgentList().blockingGet();
                ArrayList<Integer> agentIdList = new ArrayList<>();
                if(relUsers.Success) {
                    for (Integer i = 0; i < relUsers.Data.size(); i++) {
                        agentIdList.add(relUsers.Data.get(i).UserID);
                    }
                }else{
                    messageErrorLiveData.postValue(relUsers.Message);
                    return;
                }

                accessAgentAndroids = retrofitMT.getMtApi().GetAgentAccessByUserIdList(agentIdList).blockingGet();
                if(!accessAgentAndroids.Success){
                    messageErrorLiveData.postValue(accessAgentAndroids.Message);
                    return;
                }
                agentAccessLists = retrofitMT.getMtApi().GetAgentAccessList().blockingGet();
                if(!agentAccessLists.Success){
                    messageErrorLiveData.postValue(agentAccessLists.Message);
                    return;
                }
                if(reluserRepo.getUsers().size()>0)
                {
                    reluserRepo.deleteAll();
                }

                Integer totalCount = relUsers.Data.size() + agentAccessLists.Data.size() + accessAgentAndroids.Data.size();
                Integer startProgress = 0;
                for (Integer i = 0; i < relUsers.Data.size(); i++) {
                    reluserRepo.insertUser(relUsers.Data.get(i));
                    UsersProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = relUsers.Data.size();

                for (Integer i = 0; i < accessAgentAndroids.Data.size(); i++) {
                    accessAgentAndroidRepo.insertAccessAgentAndroid(accessAgentAndroids.Data.get(i));
                    UsersProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = relUsers.Data.size() + accessAgentAndroids.Data.size();

                for (Integer i = 0; i < agentAccessLists.Data.size(); i++) {
                    agentAccessListRepo.insertAgentAccessList(agentAccessLists.Data.get(i));
                    UsersProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.SaveOperationSuccess_msg));


            }
        }).subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        //Toast.makeText(getApplication().getApplicationContext(), "اطلاعات مامورین با موفقیت دریافت شد", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getApplication().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.RecieveProblem_msg));
                    }
                });

    }

    public void getSettingFromServer(){


        retrofitMT.getMtApi().GetSettings()
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<List<Setting>>>() {
                    @Override
                    public void onSuccess(DataClass<List<Setting>> settings) {
//                if(settingRepo.getSettings().getValue().size()>0){
//                    settingRepo.deleteAll();
//                }
                        if(settings.Success) {
                            for (Integer i = 0; i < settings.Data.size(); i++) {
                                settingRepo.insertSetting(settings.Data.get(i));
                                settingProgressPercentLiveData.postValue(getPrecent(i + 1, settings.Data.size()));
                                messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.SaveOperationSuccess_msg));
                            }
                        }else{
                            messageErrorLiveData.postValue(settings.Message);
                        }


                        //ast.makeText(getApplication().getApplicationContext(), "تنظیمات با موفقیت دریافت شد", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.RecieveProblem_msg));
                    }
                })
        ;

    }

    public void getClientFromServer(GetClientInput getClientInput){

        Gson gson = new Gson();
        String val=gson.toJson(getClientInput);
        retrofitMT.getMtApi().GetClients(getClientInput)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DataClass<List<Client>>>() {
                    @Override
                    public void onSuccess(DataClass<List<Client>> clients) {
                        if(clients.Success) {
                            for (Integer i = 0; i < clients.Data.size(); i++) {
                                clientRepo.insertClient(clients.Data.get(i));

                                clientProgressPercentLiveData.postValue(getPrecent(i + 1, clients.Data.size()));

                            }
                            messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.SaveOperationSuccess_msg));
                        }else {
                            messageErrorLiveData.postValue(clients.Message);
                        }
                        // Toast.makeText(getApplication().getApplicationContext(), "اطلاعات مشترکین با موفقیت دریافت شد", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.RecieveProblem_msg));
                    }

                });


    }


    public void getBaseInfoFromServer(){


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                DataClass<List<AnswerGroup>> answerGroups = new DataClass<List<AnswerGroup>>();

                List<AnswerGroupDtl> answerGroupDtls = null;
                answerGroupDtls=new ArrayList<>();
                DataClass<List<PropertyType>> propertyTypes = null;
                DataClass<List<Region>> regions = null;
                DataClass<List<City>> cities = null;
                DataClass<List<Remark>> remarks = null;
                DataClass<List<GroupingFormat>> groupingFormats = null;
                DataClass<List<RemarkGroup>> remarkGroups = null;
                DataClass<List<MasterGroupDetail>> masterGroupDetails = null;
                DataClass<List<Polomp>> polomps = null;
                DataClass<List<PolompGroup>> polompGroups = null;
                DataClass<List<PolompGroupingFormat>> polompGroupingFormats = null;
                DataClass<List<PolompColor>> polompColors=null;
                DataClass<List<PolompType>> polompTypes=null;
                DataClass<List<Menu>> menus;

                answerGroups = retrofitMT.getMtApi().GetAnswerGroups().blockingGet();
                if(answerGroups.Success) {
                    for (Integer i = 0; i < answerGroups.Data.size(); i++) {
                        if (answerGroups.Data.get(i) != null) {
                            if(answerGroups.Data.get(i).AnswerGroupDtl!=null) {
                                for (Integer j = 0; j < answerGroups.Data.get(i).AnswerGroupDtl.size(); j++) {
                                    answerGroupDtls.add(answerGroups.Data.get(i).AnswerGroupDtl.get(j));
                                }
                            }
                        }

                    }
                }
                propertyTypes = retrofitMT.getMtApi().GetPropertyTypies().blockingGet();
                if(!propertyTypes.Success){
                    messageErrorLiveData.postValue(propertyTypes.Message);

                }
                regions = retrofitMT.getMtApi().GetRegions().blockingGet();
                if(!regions.Success){
                    messageErrorLiveData.postValue(regions.Message);

                }
                cities = retrofitMT.getMtApi().GetCities().blockingGet();
                if(!cities.Success){
                    messageErrorLiveData.postValue(cities.Message);

                }
                DataClass<List<AnswerGroupDtl>> ClientTarif = null;
                ClientTarif = retrofitMT.getMtApi().GetClientsTariff().blockingGet();
                if(ClientTarif.Success) {
                    for (Integer i = 0; i < ClientTarif.Data.size(); i++) {
                        answerGroupDtls.add(ClientTarif.Data.get(i));
                    }
                }
                else {
                    messageErrorLiveData.postValue(ClientTarif.Message);

                }
                remarks = retrofitMT.getMtApi().GetRemarks().blockingGet();
                if(!remarks.Success){
                    messageErrorLiveData.postValue(remarks.Message);
                }
                groupingFormats = retrofitMT.getMtApi().GetGroupingFormat().blockingGet();
                if(!groupingFormats.Success){
                    messageErrorLiveData.postValue(groupingFormats.Message);
                }
                remarkGroups = retrofitMT.getMtApi().GetRemarkGroup().blockingGet();
                if(!remarkGroups.Success){
                    messageErrorLiveData.postValue(remarkGroups.Message);
                }
                masterGroupDetails = retrofitMT.getMtApi().GetMasterGroupDtl().blockingGet();
                if(!masterGroupDetails.Success){
                    messageErrorLiveData.postValue(masterGroupDetails.Message);
                }
                polomps = retrofitMT.getMtApi().GetPolomps().blockingGet();
                if(!polomps.Success){
                    messageErrorLiveData.postValue(polomps.Message);
                }
                polompGroups = retrofitMT.getMtApi().GetPolompGroup().blockingGet();
                if(!polompGroups.Success){
                    messageErrorLiveData.postValue(polompGroups.Message);
                }
                polompGroupingFormats = retrofitMT.getMtApi().GetPolompGroupingFormat().blockingGet();
                if(!polompGroupingFormats.Success){
                    messageErrorLiveData.postValue(polompGroupingFormats.Message);
                }
                polompColors=retrofitMT.getMtApi().GetPolompColor().blockingGet();
                if(!polompColors.Success){
                    messageErrorLiveData.postValue(polompColors.Message);
                }
                polompTypes=retrofitMT.getMtApi().GetPolompType().blockingGet();
                if(!polompTypes.Success){
                    messageErrorLiveData.postValue(polompTypes.Message);
                }
                menus=retrofitMT.getMtApi().GetAndroidMenu().blockingGet();
                if(!menus.Success){
                    messageErrorLiveData.postValue(menus.Message);
                }



                Integer totalCount = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() +
                        regions.Data.size() + cities.Data.size() + remarks.Data.size() + groupingFormats.Data.size() +
                        remarkGroups.Data.size() + masterGroupDetails.Data.size() + polomps.Data.size() + polompGroups.Data.size() +
                        polompGroupingFormats.Data.size()+polompColors.Data.size()+polompTypes.Data.size()+menus.Data.size();
                Integer startProgress = 0;
                for (Integer i = 0; i < answerGroups.Data.size(); i++) {
                    answerGroupRepo.insertAnswerGroup(answerGroups.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size();
                for (Integer i = 0; i < answerGroupDtls.size(); i++) {
                    answerGroupDtlRepo.insertAnswerGroupdtl(answerGroupDtls.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size();
                for (Integer i = 0; i < propertyTypes.Data.size(); i++) {
                    propertyTypeRepo.insertPropertyType(propertyTypes.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size();
                for (Integer i = 0; i < regions.Data.size(); i++) {
                    regionRepo.insertRegion(regions.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() +regions.Data.size();
                for (Integer i = 0; i < cities.Data.size(); i++) {
                    cityRepo.insertCity(cities.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size()+
                        cities.Data.size();
                remarkRepo.deleteAll();
                for (Integer i = 0; i < remarks.Data.size(); i++) {
                    remarkRepo.insertRemark(remarks.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() +regions.Data.size() +
                        cities.Data.size() + remarks.Data.size();
                for (Integer i = 0; i < remarkGroups.Data.size(); i++) {
                    remarkGroupRepo.insertRemarkGroup(remarkGroups.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size() +
                        cities.Data.size() + remarks.Data.size() + remarkGroups.Data.size();
                for (Integer i = 0; i < groupingFormats.Data.size(); i++) {
                    groupingFormatRepo.insertGroupingFormat(groupingFormats.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size() +
                        cities.Data.size() + remarks.Data.size() + remarkGroups.Data.size() + groupingFormats.Data.size();

                for (Integer i = 0; i < masterGroupDetails.Data.size(); i++) {
                    masterGroupDetailRepo.insertMasterGroupDetail(masterGroupDetails.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size() +
                        cities.Data.size() + remarks.Data.size() + remarkGroups.Data.size() + groupingFormats.Data.size() + masterGroupDetails.Data.size() +
                        masterGroupDetails.Data.size();

                for (Integer i = 0; i < polomps.Data.size(); i++) {
                    polompRepo.insertPolomp(polomps.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size() +
                        cities.Data.size() + remarks.Data.size() + remarkGroups.Data.size() + groupingFormats.Data.size() + masterGroupDetails.Data.size() +
                        masterGroupDetails.Data.size() + polomps.Data.size();

                for (Integer i = 0; i < polompGroups.Data.size(); i++) {
                    polompGroupRepo.insertPolompGroup(polompGroups.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size() +
                        cities.Data.size() + remarks.Data.size() + remarkGroups.Data.size() + groupingFormats.Data.size() + masterGroupDetails.Data.size() +
                        masterGroupDetails.Data.size() + polomps.Data.size() + polompGroups.Data.size();

                for (Integer i = 0; i < polompGroupingFormats.Data.size(); i++) {
                    polompGroupingFormatRepo.insertPolompGroupingFormat(polompGroupingFormats.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + groupingFormats.Data.size() + masterGroupDetails.Data.size() +
                        masterGroupDetails.Data.size() + polomps.Data.size() + polompGroups.Data.size()+polompGroupingFormats.Data.size();

                for(Integer i = 0; i < polompColors.Data.size(); i++){
                    polompColorRepo.insertPolompColor(polompColors.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size() +
                        cities.Data.size() + remarks.Data.size() + remarkGroups.Data.size() + groupingFormats.Data.size() + masterGroupDetails.Data.size() +
                        masterGroupDetails.Data.size() + polomps.Data.size() + polompGroups.Data.size()+polompGroupingFormats.Data.size()+
                        polompColors.Data.size();

                for(Integer i = 0; i < polompTypes.Data.size(); i++){
                    polompTypeRepo.insertPolompType(polompTypes.Data.get(i));
                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                startProgress = answerGroups.Data.size() + answerGroupDtls.size() + propertyTypes.Data.size() + regions.Data.size() +
                        cities.Data.size() + remarks.Data.size() + remarkGroups.Data.size() + groupingFormats.Data.size() + masterGroupDetails.Data.size() +
                        masterGroupDetails.Data.size() + polomps.Data.size() + polompGroups.Data.size()+polompGroupingFormats.Data.size()+
                        polompColors.Data.size()+polompTypes.Data.size();
                for (Integer i = 0; i < menus.Data.size(); i++){
                    menuRepo.deleteMenu(menus.Data.get(i));
                    menuRepo.insertMenu(menus.Data.get(i));

                    baseinfoProgressPercentLiveData.postValue(getPrecent(startProgress + (i + 1), totalCount));
                }
                messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.SaveOperationSuccess_msg));


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
                        // Toast.makeText(getApplication().getApplicationContext(), "اطلاعات پایه با موفقیت دریافت شد", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Toast.makeText(getApplication().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.RecieveProblem_msg));
                    }
                });



    }

    public LiveData<List<Region>> getRegion(){
        return regionRepo.getRegions();
    }

    private int getPrecent(int progress,int totalCount){
        return (progress * 100)/totalCount;
    }

}
