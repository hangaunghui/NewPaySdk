package com.newmall.network;

import org.newtonproject.newpay.android.sdk.bean.NewAuthLogin;
import org.newtonproject.newpay.android.sdk.bean.NewAuthPay;
import org.newtonproject.newpay.android.sdk.bean.NewAuthProof;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-18--22:22
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class HttpService{

    private DemoApi demoApi;

    private HttpService() {}
    private String baseUrl = "http://47.52.170.176:9999";
    private static HttpService instance;
    public static HttpService getInstance() {
        if(instance == null) {
            synchronized (HttpService.class) {
                if(instance == null) {
                    instance = new HttpService();
                    instance.buildApiClient();
                }
            }
        }
        return instance;
    }

    private void buildApiClient() {
        demoApi = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DemoApi.class);
    }

    public Observable<BaseResponse<NewAuthProof>> getNewAuthProof(String newid) {
        return demoApi.getAuthProof(newid).subscribeOn(Schedulers.io());
    }

    public Observable<BaseResponse<NewAuthLogin>> getNewAuthLogin() {
        return demoApi.getAuthLogin().subscribeOn(Schedulers.io());
    }

    public Observable<BaseResponse<NewAuthPay>> getNewAuthPay(String newid) {
        return demoApi.getAuthPay(newid).subscribeOn(Schedulers.io());
    }

}
