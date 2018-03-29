package com.example.ananya.careapp;


/**
 * Created by Ananya on 11-03-2018.
 */

public class CaremedUser{
    String Hospital="com.example.ananya.careapp.HospitalModel";
    String Occupation="NA";
    String Age="NA";
    String Gender="NA";
    long Id;

    public CaremedUser(String occupation, long Ids) {
        Occupation = occupation;
        Age= "0";
        Gender="NA";
        Hospital="NA";
        Id=Ids;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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
