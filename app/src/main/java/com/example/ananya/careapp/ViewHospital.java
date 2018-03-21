package com.example.ananya.careapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ViewHospital extends AppCompatActivity {
    DatabaseReference databasePatients;
    ListView listviewpatient;
    String ss;
    List<Hospital> patientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hospital);

    }
}
