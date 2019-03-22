package com.example.udaysaikumar.clgattendance.RetrofitPack;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitMarksServer {
    public static final String BASE_URL = "https://api.mongolab.com/api/1/databases/sasi_attendance/";

    public static Retrofit retro = null;

    public static Retrofit getSecRetrofit() {
        if (retro == null) {
            retro = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retro;
    }
}