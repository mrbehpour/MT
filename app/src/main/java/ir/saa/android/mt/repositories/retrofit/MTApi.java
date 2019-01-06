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
import ir.saa.android.mt.model.entities.MasterGroupDetail;
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
    Single<DataClass<RelUser>> GetAgentList();

    @GET("GetCities")
    Single<DataClass<City>> GetCities();

    @GET("GetAnswerGroups")
    Single<DataClass<AnswerGroup>> GetAnswerGroups();

    @GET("GetClientsTariff")
    Single<DataClass<AnswerGroupDtl>> GetClientsTariff();

    @GET("GetGroupingFormat")
    Single<DataClass<GroupingFormat>> GetGroupingFormat();

    @GET("GetMasterGroupDtl")
    Single<DataClass<MasterGroupDetail>> GetMasterGroupDtl();

    @GET("GetPolompGroup")
    Single<DataClass<PolompGroup>> GetPolompGroup();

    @GET("GetPolompGroupingFormat")
    Single<DataClass<PolompGroupingFormat>> GetPolompGroupingFormat();

    @GET("GetPolomps")
    Single<DataClass<Polomp>> GetPolomps();

    @GET("GetPolompColor")
    Single<DataClass<PolompColor>> GetPolompColor();

    @GET("GetPolompType")
    Single<DataClass<PolompType>> GetPolompType();

    @GET("GetPropertyTypies")
    Single<DataClass<PropertyType>> GetPropertyTypies();

    @GET("GetRegions")
    Single<DataClass<Region>> GetRegions();

    @GET("GetRemarkGroup")
    Single<DataClass<RemarkGroup>> GetRemarkGroup();

    @GET("GetRemarks")
    Single<DataClass<Remark>> GetRemarks();

    @GET("GetSettings")
    Single<DataClass<Setting>> GetSettings();

    @GET("GetAgentAccessList")
    Single<DataClass<AgentAccessList>> GetAgentAccessList();

    @POST("GetClients")
    Single<DataClass<Client>> GetClients(@Body GetClientInput getClientInput);

    @POST("GetAgentAccessByUserIdList")
    Single<DataClass<AccessAgentAndroid>> GetAgentAccessByUserIdList(@Body ArrayList<Integer> agentIdList);

    @POST("SaveClientAllInfoAndroid")
    Single<List<RecordeSummary>> SaveClientAllInfoAndroid(@Body List<ClientAllInfo> clientinfolList);
}
