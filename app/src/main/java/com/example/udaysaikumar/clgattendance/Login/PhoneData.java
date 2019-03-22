package com.example.udaysaikumar.clgattendance.Login;

import com.google.gson.annotations.SerializedName;

public class PhoneData {
    @SerializedName("regno")
    public String regno;
    @SerializedName("parentmobile")
    public long parentmobile;
    @SerializedName("studentmobile")
    public long studentmobile;

    public String getRegno() {
        return regno;
    }
    public long getParentmobile() {
        return parentmobile;
    }

    public long getStudentmobile() {
        return studentmobile;
    }
}
