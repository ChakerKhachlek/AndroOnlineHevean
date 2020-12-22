package com.example.onlineheaven.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    private static final String BASE_URL = "http://192.168.1.51:8000/api/";

    public static ApiInterface getRetroFitClient(){
        Retrofit.Builder builder=new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(BASE_URL);

        return builder.build().create(ApiInterface.class);

    }

}
