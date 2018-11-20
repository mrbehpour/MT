package ir.saa.android.mt.repositories.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by h.eskandari on 7/22/2018.
 */

public class RetrofitMT {
    private static RetrofitMT instance = null;
    private Retrofit retrofit;
    private MTApi mtApi;
    private String ServerAddress;
    private RetrofitMT() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        ServerAddress= G.getPref(SharePrefEnum.AddressServer)+"/services/AndroidServices.svc/json/";
//http://192.168.3.176:645/services/AndroidServices.svc/json/"
        //"http://89.43.6.254:808/services/AndroidServices.svc/json/
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
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
