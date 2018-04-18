package com.example.ananya.careapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindDoctor extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private Query myQuery;
    private RecyclerView mRecyclerView;
    CardView c1,c2,c3;
    Button btncity,cityspec,cityhos;
    String x;
    Spinner city,city1,city2,hosp,spec,option;
    public FindDoctor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View findDoc = inflater.inflate(R.layout.fragment_find_doctor, container, false);
        c1 = findDoc.findViewById(R.id.fdcardcity);
        c2 = findDoc.findViewById(R.id.fdcardcityspec);
        c3 = findDoc.findViewById(R.id.fdcardcityhos);
        option = findDoc.findViewById(R.id.fdoption);
        city = findDoc.findViewById(R.id.fdcity);
        city1 = findDoc.findViewById(R.id.fdcity1);
        city2 = findDoc.findViewById(R.id.fdcity2);
        btncity = findDoc.findViewById(R.id.btncity);
        cityspec = findDoc.findViewById(R.id.cityspec);
        cityhos = findDoc.findViewById(R.id.cityhos);
        hosp = findDoc.findViewById(R.id.fdhospital);
        spec = findDoc.findViewById(R.id.fdhspec);
        c1.setVisibility(View.VISIBLE);
        c2.setVisibility(View.GONE);
        c3.setVisibility(View.GONE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("hospitals");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    final List<String> Hospitals = new ArrayList<String>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String key = String.valueOf(postSnapshot.getKey());
                        Hospitals.add(key);
                    }

                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Hospitals);
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hosp.setAdapter(areasAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef = FirebaseDatabase.getInstance().getReference("docappoint");
        myRef.keepSynced(true);
        Query q = myRef.orderByChild("id");
        option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                x= option.getSelectedItem().toString();
                if(x.equals("City")){
                    c1.setVisibility(View.VISIBLE);
                    c2.setVisibility(View.GONE);
                    c3.setVisibility(View.GONE);
                }else if(x.equals("City and Specialization")){
                    c1.setVisibility(View.GONE);
                    c3.setVisibility(View.VISIBLE);
                    c2.setVisibility(View.GONE);
                }else{
                    c1.setVisibility(View.GONE);
                    c3.setVisibility(View.GONE);
                    c2.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
        mRecyclerView = (RecyclerView) findDoc.findViewById(R.id.fddocrecycler);
        allRecord(q);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        btncity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference("docappoint");
                String ciTy = city.getSelectedItem().toString();
                myQuery = myRef.orderByChild("city").equalTo(ciTy);
                allRecord(myQuery);
            }
        });
        cityhos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference("docappoint");
                String hOs = hosp.getSelectedItem().toString();
                String citY = city1.getSelectedItem().toString();
                Toast.makeText(getActivity(),citY+hOs,Toast.LENGTH_SHORT).show();
                myQuery = myRef.orderByChild("cityhos").equalTo(citY+hOs);
                allRecord(myQuery);
            }
        });
        cityspec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spEc = spec.getSelectedItem().toString();
                String ct = city2.getSelectedItem().toString();
                Toast.makeText(getActivity(),ct+spEc,Toast.LENGTH_SHORT).show();
                myQuery = myRef.orderByChild("cityspec").equalTo(ct+spEc);
                allRecord(myQuery);
            }
        });

        return findDoc;
    }
    private void allRecord(Query ref){

        FirebaseRecyclerAdapter<FindDoctorModel, FindDoctorHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<FindDoctorModel, FindDoctorHolder>(
                        FindDoctorModel.class,
                        R.layout.find_doctor_list,
                        FindDoctorHolder.class, ref
                ) {
                    @Override
                    protected void populateViewHolder(FindDoctorHolder viewHolder, FindDoctorModel model, int position) {
                        viewHolder.uid.setText(model.getId());
                        viewHolder.uage.setText(model.getAge());
                        viewHolder.uspec.setText(model.getSpecialization());
                        viewHolder.uhos.setText(model.getHospital());
                        viewHolder.uname.setText(model.getName());
                        viewHolder.ugender.setText(model.getGender());
                    }
                };

        mRecyclerView.setAdapter(recyclerAdapter);
    }
}
