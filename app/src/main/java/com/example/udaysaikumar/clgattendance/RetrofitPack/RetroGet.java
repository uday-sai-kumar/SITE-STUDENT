package com.example.udaysaikumar.clgattendance.RetrofitPack;
import com.example.udaysaikumar.clgattendance.Login.LoginData;
import com.example.udaysaikumar.clgattendance.Login.PhoneData;
import com.example.udaysaikumar.clgattendance.Login.UserDetails;

import java.util.List;

import androidx.annotation.Keep;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
@Keep
public interface RetroGet {
    @GET("collections/{col}")
    Call<List<LoginData>> getPhone(@Path("col") String col,@Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{col}")
    Call<List<PhoneData>> getPhoneData(@Path("col") String col, @Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{coll}")
    Call<List<LoginData>> getMlabData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{coll}")
    Call<List<UserDetails>> getData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q, @Query("f") String f);
    @GET("collections/{coll}")
    Call<String> getMarksData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{coll}")
    Call<String> getProfile(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{coll}")
    Call<String> getMarkData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q,@Query("f")String f);
    @GET("collections/{coll}")
    Call<String> getAttendance(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{coll}")
    Call<String> getPercentage(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{coll}")
    Call<String> getProfileData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
    @GET("o/Photos%2F{photo}")
    Call<String> getFireImage(@Path("photo") String photo);
    String ss="username=sasicollege&password=SITE2002&from=INSITE&to=9494345589&msg=Hello%20USK&type=1";
    @GET("sms.php")
    Call<String> getOTP(@Query("username") String un,@Query("password") String pass,@Query("from") String from,@Query("to")String to,@Query("msg") String msg,@Query("type") String type);
    @GET("list-time-zone?key=A16EM4FQ8A5H&format=json&country=IN")
    Call<String> getTimeStamp();
    @POST("collections/{coll}")
    Call<String> sendFeedback(@Header("Content-Type") String header, @Body String body, @Path("coll")String coll, @Query("apiKey") String key);
}
