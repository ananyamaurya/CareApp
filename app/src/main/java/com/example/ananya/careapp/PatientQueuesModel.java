package com.example.ananya.careapp;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Ananya on 30-03-2018.
 */

public class PatientQueuesModel {
    String uID;
    String utime;
    String uprescription;
    String udoctor;
    String ucondition;
    String udisease;



    public PatientQueuesModel(){

    }
    public PatientQueuesModel(String ID, String time, String prescription, String doctor, String condition, String disease){
        uID=ID;
        utime=time;
        uprescription=prescription;
        udoctor=doctor;
        ucondition=condition;
        udisease=disease;
    }
    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getUprescription() {
        return uprescription;
    }

    public void setUprescription(String uprescription) {
        this.uprescription = uprescription;
    }

    public String getUdoctor() {
        return udoctor;
    }

    public void setUdoctor(String udoctor) {
        this.udoctor = udoctor;
    }

    public String getUcondition() {
        return ucondition;
    }

    public void setUcondition(String ucondition) {
        this.ucondition = ucondition;
    }

    public String getUdisease() {
        return udisease;
    }

    public void setUdisease(String udisease) {
        this.udisease = udisease;
    }
}
