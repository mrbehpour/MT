package ir.saa.android.mt.repositories.retrofit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ir.saa.android.mt.model.entities.City;
import ir.saa.android.mt.model.entities.RelUser;
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

//    @GET("trends/place.json")
//    Observable<List<TrendsResponse>> getTrends(@Query("id") String placeId);
}