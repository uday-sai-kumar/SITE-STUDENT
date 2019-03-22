package com.example.udaysaikumar.clgattendance.Login;
import com.google.gson.annotations.SerializedName;


public class UserDetails {
    @SerializedName("Name")
   String name;
    @SerializedName("RegNo")
    String regno;
    @SerializedName("Branch")
    String branch;
    @SerializedName("Section")
    String section;
    @SerializedName("SSC")
    String ssc;
    @SerializedName("Inter")
    String inter;
}
