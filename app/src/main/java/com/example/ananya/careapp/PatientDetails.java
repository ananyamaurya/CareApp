package com.example.ananya.careapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDetails extends Fragment {
    EditText name,gender,bg,city,age,weight, mobile;
    EditText eid;
    Button bid,blood,urine;
    String ss;
    Uri uriblood,uriurine;
    DatabaseReference databasePatients;

    public PatientDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View patDet = inflater.inflate(R.layout.fragment_patient_details, container, false);
        bid=patDet.findViewById(R.id.patdebid);
        eid=patDet.findViewById(R.id.patdeeid);
        blood=patDet.findViewById(R.id.patdeblood);
        urine=patDet.findViewById(R.id.patdeurine);
        databasePatients = FirebaseDatabase.getInstance().getReference("patients");
        name=patDet.findViewById(R.id.patdename);
        gender=patDet.findViewById(R.id.patdegender);
        bg=patDet.findViewById(R.id.patdebg);
        age=patDet.findViewById(R.id.patdeage);
        city= patDet.findViewById(R.id.patdecity);
        weight= patDet.findViewById(R.id.patdeweight);
        mobile=patDet.findViewById(R.id.patdemobile);
        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=eid.getText().toString();
                if(!TextUtils.isEmpty(s)) {
                    getDetails(s);
                }else{
                    Toast.makeText(getActivity(),"ID cannot be Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });





        return patDet;
    }
    public void getDetails(final String pdsid) {
        DatabaseReference datanapshot = FirebaseDatabase.getInstance().getReference("patients");
        datanapshot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(pdsid)) {
                    String names = snapshot.child(pdsid).child("name").getValue().toString();
                    String genders = snapshot.child(pdsid).child("gender").getValue().toString();
                    String bgs = snapshot.child(pdsid).child("bg").getValue().toString();
                    String ages = snapshot.child(pdsid).child("age").getValue().toString();
                    String cities = snapshot.child(pdsid).child("city").getValue().toString();
                    String weights = snapshot.child(pdsid).child("weight").getValue().toString();
                    String mobiles = snapshot.child(pdsid).child("mobile").getValue().toString();/*
                    String bloods = snapshot.child(pdsid).child("reports").child("blood").getValue().toString();
                    String urines = snapshot.child(pdsid).child("reports").child("blood").getValue().toString();
                    uriblood = (Uri) snapshot.child(pdsid).child("reports").child("blooduri").getValue();
                    uriurine = (Uri) snapshot.child(pdsid).child("reports").child("urineuri").getValue();*/
                    name.setText(names);
                    age.setText(ages);
                    gender.setText(genders);
                    bg.setText(bgs);
                    city.setText(cities);
                    weight.setText(weights);
                    mobile.setText(mobiles);
                    /*if (!TextUtils.isEmpty(bloods)) {
                        blood.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    } else {
                        blood.setClickable(false);
                    }
                    if (!TextUtils.isEmpty(urines)) {

                    } else {
                        blood.setClickable(false);
                    }*/
                    Toast.makeText(getActivity(), "Patient Details Fetched", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Patient Doesn't Exist With this ID", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        datanapshot.keepSynced(true);
    }
}
