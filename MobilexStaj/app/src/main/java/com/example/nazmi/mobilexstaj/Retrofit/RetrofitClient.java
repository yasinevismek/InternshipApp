package com.example.nazmi.mobilexstaj.Retrofit;
/**
 * Created by Nazmican GÖKBULUT
 */

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//Todo: Client Server Retrofit
public class RetrofitClient {
    //Todo: Identify Retrofit
    private static Retrofit instance;

    //Todo: Retrofit Link
    public static Retrofit getInstance() {
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


        return instance;
    }
}
