package com.codigo.ms_retrofit.retrofit.impl;

import com.codigo.ms_retrofit.aggregates.constants.Constans;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSunatServiceImpl {

    private static String BASE_URL = Constans.BASE_URL;

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
