package com.example.udaysaikumar.clgattendance.Fragments;

public class MyAttendance {
    String subjectName;
    String period;

    public MyAttendance(String subjectName, String period) {
        this.subjectName = subjectName;
        this.period = period;
    }

    public String getSubjectName() {
        return subjectName;
    }


    public String getPeriod() {
        return period;
    }

}
