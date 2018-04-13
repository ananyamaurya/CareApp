package com.example.ananya.careapp;

public class AppointmentModel {
    private String Pid;
    private String Pname;
    private String Date;
    private String Time;

    public AppointmentModel(){

    }
    public AppointmentModel(String pid, String pname, String date, String time) {
        Pid = pid;
        Pname = pname;
        Date = date;
        Time = time;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
