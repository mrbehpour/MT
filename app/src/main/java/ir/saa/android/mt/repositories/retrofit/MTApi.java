package ir.saa.android.mt.repositories.retrofit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.City;
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
import ir.saa.android.mt.model.entities.TariffInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by h.eskandari on 7/22/2018.
 */

public interface MTApi {
    //public final static String WEB_SERVICE_BASE_URL = "http://192.168.3.176:645/services/AndroidServices.svc/json";

    @GET("GetAgentList")
    Single<List<RelUser>> GetAgentList();

    @GET("GetCities")
    Single<List<City>> GetCities();

    @GET("GetAnswerGroups")
    Single<List<AnswerGroup>> GetAnswerGroups();

    @GET("GetClientsTariff")
    Single<List<TariffInfo>> GetClientsTariff();

    @GET("GetGroupingFormat")
    Single<List<GroupingFormat>> GetGroupingFormat();

    @GET("GetMasterGroupDtl")
    Single<List<MasterGroupDetail>> GetMasterGroupDtl();

    @GET("GetPolompGroup")
    Single<List<PolompGroup>> GetPolompGroup();

    @GET("GetPolompGroupingFormat")
    Single<List<PolompGroupingFormat>> GetPolompGroupingFormat();

    @GET("GetPolomps")
    Single<List<Polomp>> GetPolomps();

    @GET("GetPropertyTypies")
    Single<List<PropertyType>> GetPropertyTypies();

    @GET("GetRegions")
    Single<List<Region>> GetRegions();

    @GET("GetRemarkGroup")
    Single<List<RemarkGroup>> GetRemarkGroup();

    @GET("GetRemarks")
    Single<List<Remark>> GetRemarks();

    @GET("GetSettings")
    Single<List<Setting>> GetSettings();
}
