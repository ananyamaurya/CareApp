package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareHospital extends Fragment {
    Button hadd, hview,addhospital;
    LinearLayout hl1,hl2;
    EditText txtname,txtcity,txtaddress;
    public CareHospital() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v3= inflater.inflate(R.layout.fragment_hospital, container, false);
        hadd=v3.findViewById(R.id.hospitaladd);
        hview=v3.findViewById(R.id.hospitalview);
        addhospital=v3.findViewById(R.id.btnaddhospital);
        hl1=v3.findViewById(R.id.hospital1);
        hl2=v3.findViewById(R.id.hospital2);
        txtaddress=v3.findViewById(R.id.hosaddress);
        txtcity=v3.findViewById(R.id.hoscity);
        txtname=v3.findViewById(R.id.hosname);
        hadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            hl1.setVisibility(View.VISIBLE);
            hl2.setVisibility(View.GONE);
            }
        });
        hview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hl2.setVisibility(View.VISIBLE);
                hl1.setVisibility(View.GONE);
            }
        });
        addhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("hospitals");
                String name = txtname.getText().toString();
                String city = txtcity.getText().toString();
                String address = txtaddress.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(address)) {
                    Hospital h=new Hospital(name,city,address);
                    ref.child(name+city).setValue(h);
                    ref.keepSynced(true);
                    Toast.makeText(getActivity(),"Hospital Added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v3;
    }


}
