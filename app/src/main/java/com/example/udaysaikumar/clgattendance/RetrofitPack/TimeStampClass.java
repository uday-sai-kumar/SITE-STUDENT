package com.example.udaysaikumar.clgattendance.RetrofitPack;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TimeStampClass {
    public static final String BASE_URLS="http://api.timezonedb.com/v2/";
    public static Retrofit retrofits=null;
    public static Retrofit getTimestamp() {
        if(retrofits==null){
            retrofits=new Retrofit.Builder().baseUrl(BASE_URLS).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofits;
    }
}
