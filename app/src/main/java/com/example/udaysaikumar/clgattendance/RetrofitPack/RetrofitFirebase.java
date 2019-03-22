package com.example.udaysaikumar.clgattendance.RetrofitPack;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitFirebase {
    public static final String BASE_URL="https://firebasestorage.googleapis.com/v0/b/site-74340.appspot.com/";
    public static Retrofit retrofits=null;
    public static Retrofit getRetrofits() {
        if(retrofits==null){
            retrofits=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofits;
    }
}

