package ir.saa.android.mt.repositories.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by h.eskandari on 7/22/2018.
 */

public class RetrofitMT {
    private static RetrofitMT instance = null;
    private Retrofit retrofit;
    private MTApi mtApi;

    private RetrofitMT() {
        retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.3.176:645/services/AndroidServices.svc/json/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
            mtApi = retrofit.create(MTApi.class);
    }

    public static RetrofitMT getInstance() {
        if (instance == null) {
            instance = new RetrofitMT();
        }
        return instance;
    }

    public MTApi getMtApi() {
        return mtApi;
    }
}
