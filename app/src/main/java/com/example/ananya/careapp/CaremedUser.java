package com.example.ananya.careapp;


/**
 * Created by Ananya on 11-03-2018.
 */

public class CaremedUser{
    String Hospital="com.example.ananya.careapp.Hospital";
    String Occupation="NA";
    String Age="NA";
    String Gender="NA";



    public CaremedUser() {
    }

    public CaremedUser(String occupation) {
        Occupation = occupation;
        Age= "0";
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

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }
}
