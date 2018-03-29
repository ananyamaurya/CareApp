package com.example.ananya.careapp;

/**
 * Created by Ananya on 19-03-2018.
 */

public class HospitalModel {
    private static final String TAG = "HospitalModel";

    String HospitalName;
    String HospitalCity;
    String HospitalAddress;
    public  HospitalModel(){

    }
    public HospitalModel(String HospitalName, String HospitalCity, String HospitalAddress ) {
        this.HospitalName=HospitalName;
        this.HospitalCity=HospitalCity;
        this.HospitalAddress=HospitalAddress;
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
