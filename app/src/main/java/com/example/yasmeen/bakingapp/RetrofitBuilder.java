package com.example.yasmeen.bakingapp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitBuilder {

    static RetrofitInterface retrofitInterface;
    public static RetrofitInterface Retrive()
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (retrofitInterface == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .callFactory(httpClientBuilder.build())
                    .build();
            retrofitInterface = retrofit.create(RetrofitInterface.class);
        }
        return  retrofitInterface;

    }

}
