package ir.saa.android.mt.repositories.retrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.saa.android.mt.model.entities.AccessAgentAndroid;
import ir.saa.android.mt.model.entities.AgentAccessList;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.City;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientAllInfo;
import ir.saa.android.mt.model.entities.GetClientInput;
import ir.saa.android.mt.model.entities.GroupingFormat;
import ir.saa.android.mt.model.entities.ImiRegisterInput;
import ir.saa.android.mt.model.entities.MasterGroupDetail;
import ir.saa.android.mt.model.entities.Menu;
import ir.saa.android.mt.model.entities.Polomp;
import ir.saa.android.mt.model.entities.PolompColor;
import ir.saa.android.mt.model.entities.PolompGroup;
import ir.saa.android.mt.model.entities.PolompGroupingFormat;
import ir.saa.android.mt.model.entities.PolompType;
import ir.saa.android.mt.model.entities.PropertyType;
import ir.saa.android.mt.model.entities.RecordeSummary;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroup;
import ir.saa.android.mt.model.entities.Setting;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by h.mohammadi on 7/22/2018.
 */

public interface MTApi {
    //public final static String WEB_SERVICE_BASE_URL = "http://192.168.3.176:645/services/AndroidServices.svc/json";

    @GET("GetAgentList")
    Single<DataClass<List<RelUser>>> GetAgentList();

    @GET("GetCities")
    Single<DataClass<List<City>>> GetCities();

    @GET("GetAnswerGroups")
    Single<DataClass<List<AnswerGroup>>> GetAnswerGroups();

    @GET("GetClientsTariff")
    Single<DataClass<List<AnswerGroupDtl>>> GetClientsTariff();

    @GET("GetGroupingFormat")
    Single<DataClass<List<GroupingFormat>>> GetGroupingFormat();

    @GET("GetMasterGroupDtl")
    Single<DataClass<List<MasterGroupDetail>>> GetMasterGroupDtl();

    @GET("GetPolompGroup")
    Single<DataClass<List<PolompGroup>>> GetPolompGroup();

    @GET("GetPolompGroupingFormat")
    Single<DataClass<List<PolompGroupingFormat>>> GetPolompGroupingFormat();

    @GET("GetPolomps")
    Single<DataClass<List<Polomp>>> GetPolomps();

    @GET("GetPolompColor")
    Single<DataClass<List<PolompColor>>> GetPolompColor();

    @GET("GetPolompType")
    Single<DataClass<List<PolompType>>> GetPolompType();

    @GET("GetPropertyTypies")
    Single<DataClass<List<PropertyType>>> GetPropertyTypies();

    @GET("GetRegions")
    Single<DataClass<List<Region>>> GetRegions();

    @GET("GetRemarkGroup")
    Single<DataClass<List<RemarkGroup>>> GetRemarkGroup();

    @GET("GetRemarks")
    Single<DataClass<List<Remark>>> GetRemarks();

    @GET("GetSettings")
    Single<DataClass<List<Setting>>> GetSettings();

    @GET("GetAgentAccessList")
    Single<DataClass<List<AgentAccessList>>> GetAgentAccessList();

    @GET("GetAndroidMenu")
    Single<DataClass<List<Menu>>> GetAndroidMenu();

    @POST("GetClients")
    Single<DataClass<List<Client>>> GetClients(@Body GetClientInput getClientInput);

    @POST("GetAgentAccessByUserIdList")
    Single<DataClass<List<AccessAgentAndroid>>> GetAgentAccessByUserIdList(@Body ArrayList<Integer> agentIdList);

    @POST("SaveClientAllInfoAndroid")
    Single<List<RecordeSummary>> SaveClientAllInfoAndroid(@Body List<ClientAllInfo> clientinfolList);

    @POST("RegisterDeviceImi")
    Single<DataClass<Integer>> RegisterDeviceImi(@Body ImiRegisterInput imiRegisterInput);

    @POST("GetImiConfirmStatus")
    Single<DataClass<Boolean>> GetImiConfirmStatus(@Body ImiRegisterInput imiRegisterInput);
}
