package com.example.ananya.careapp;

import android.app.Application;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ananya on 11-03-2018.
 */

public class CaremedUser extends Application{
    String Hospital="Hospital";
    String Occupation="NA";
    Integer Age=0;
    String Gender="NA";



    public CaremedUser() {
    }

    public CaremedUser(String occupation) {
        Occupation = occupation;
        Age= 0;
        Gender="NA";
        Hospital="NA";
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }
}
