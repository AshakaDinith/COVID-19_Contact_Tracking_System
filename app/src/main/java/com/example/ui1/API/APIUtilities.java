package com.example.ui1.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtilities {
    public static Retrofit retrofit=null;

    public static APIInterface getAPIInterface(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(APIInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(APIInterface.class);
    }

    public static APIInterface getAPIInterface1(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(APIInterface.BASE_URL1).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(APIInterface.class);
    }

}
