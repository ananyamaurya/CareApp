package com.example.ananya.careapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ananya on 20-03-2018.
 */

public class MyApp extends Application{
    String Name="Dummy";
    String UID="userId";
    String Occupation="Nahi Pata";
    String Hospital="Tera HospitalModel";

    @Override
    public void onCreate() {
        super.onCreate();

        if (!FirebaseApp.getApps(this).isEmpty())
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }
}
