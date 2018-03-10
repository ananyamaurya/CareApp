package com.example.ananya.careapp;

import android.app.Application;

/**
 * Created by Ananya on 01-03-2018.
 */

public class UserCare extends Application{
    String Name="Username";
    String UID="1234567890";

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
}
