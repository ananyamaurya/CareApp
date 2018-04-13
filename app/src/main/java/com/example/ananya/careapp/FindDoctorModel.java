package com.example.ananya.careapp;

/**
 * Created by Ananya on 19-03-2018.
 */

public class FindDoctorModel {
    private static final String TAG = "FindDoctorModel";

    private String Name;
    private String Age;
    private String Gender;
    private String Specialization;
    private String Hospital;
    private String Id;
    public  FindDoctorModel(){

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
