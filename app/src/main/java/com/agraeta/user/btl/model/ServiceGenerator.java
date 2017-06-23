package com.agraeta.user.btl.model;

import com.agraeta.user.btl.Globals;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nivida new on 24-Mar-17.
 */

public class ServiceGenerator {

    public static Retrofit getService(){
        return new Retrofit.Builder().baseUrl(Globals.server_link).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static AdminAPI getAPIServiceClass(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Globals.server_link)
                .client(getRequestHeader())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(AdminAPI.class);
    }

    private static OkHttpClient getRequestHeader() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();

        return httpClient;
    }
}
