package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareHospital extends Fragment {
    Button hview,hdelete;
    LinearLayout hl1,hl2;

    public CareHospital() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v3= inflater.inflate(R.layout.fragment_hospital, container, false);
        hview=v3.findViewById(R.id.hospitalview);
        hdelete=v3.findViewById(R.id.hospitaldelete);
        hl1=v3.findViewById(R.id.hospital1);
        hl2=v3.findViewById(R.id.hospital2);


        return v3;
    }

}
