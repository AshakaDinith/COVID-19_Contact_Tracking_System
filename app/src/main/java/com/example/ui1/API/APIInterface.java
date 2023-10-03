package com.example.ui1.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    String BASE_URL="https://disease.sh/v2/";
    @GET("countries")
    Call<List<ModelClass>> getLocalData();


    String BASE_URL1="https://disease.sh/v2//";

    @GET("all")
    Call<ModelClass1> getGlobalData();
}
