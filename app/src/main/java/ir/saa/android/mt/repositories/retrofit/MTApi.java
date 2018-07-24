package ir.saa.android.mt.repositories.retrofit;

import java.util.List;

import io.reactivex.Flowable;
import ir.saa.android.mt.repositories.model.entities.City;
import ir.saa.android.mt.repositories.model.entities.RelUser;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by h.eskandari on 7/22/2018.
 */
public interface MTApi {
    //public final static String WEB_SERVICE_BASE_URL = "http://192.168.3.176:645/services/AndroidServices.svc/json";

    @GET("GetAgentList")
    Flowable<List<RelUser>> GetAgentList();

    @GET("GetCities")
    Flowable<List<City>> GetCities();

//    @GET("trends/place.json")
//    Observable<List<TrendsResponse>> getTrends(@Query("id") String placeId);
}