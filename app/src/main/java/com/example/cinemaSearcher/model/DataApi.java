package com.example.cinemaSearcher.model;

import com.example.cinemaSearcher.model.respons.Data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {

    @GET("films.json")
    Call<Data> getCinemaList();
}
