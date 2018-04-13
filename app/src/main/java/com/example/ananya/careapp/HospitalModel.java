package com.example.ananya.careapp;

/**
 * Created by Ananya on 19-03-2018.
 */

public class HospitalModel {
    private static final String TAG = "HospitalModel";

    private String HospitalName;
    private String HospitalCity;
    private String HospitalAddress;
    private String Id;
    public  HospitalModel(){

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public HospitalModel(String HospitalName, String HospitalCity, String HospitalAddress, String Id ) {
        this.HospitalName=HospitalName;
        this.HospitalCity=HospitalCity;
        this.HospitalAddress=HospitalAddress;
        this.Id= Id;

    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getHospitalCity() {
        return HospitalCity;
    }

    public void setHospitalCity(String hospitalCity) {
        HospitalCity = hospitalCity;
    }

    public String getHospitalAddress() {
        return HospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        HospitalAddress = hospitalAddress;
    }





}
