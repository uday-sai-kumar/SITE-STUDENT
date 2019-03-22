package com.example.udaysaikumar.clgattendance.RetrofitPack;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    public static final String BASE_URL="https://api.mongolab.com/api/1/databases/sasi_attendance/";
    public static Retrofit retrofit=null;
    public static Retrofit getRetrofit() {
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
