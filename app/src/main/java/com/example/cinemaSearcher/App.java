package com.example.cinemaSearcher;

import android.app.Application;

import com.example.cinemaSearcher.model.DataApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    String BASE_URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/";

    private static DataApi dataApi;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataApi = retrofit.create(DataApi.class);
    }

    public static DataApi getApi() {
        return dataApi;
    }
}
