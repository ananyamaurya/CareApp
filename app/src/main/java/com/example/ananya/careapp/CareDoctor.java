package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareDoctor extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query mQuery;
    private RecyclerView mRecyclerView;
    public CareDoctor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v2= inflater.inflate(R.layout.fragment_doctor, container, false);
        mRecyclerView = (RecyclerView) v2.findViewById(R.id.doctor_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        allRecord();

        return v2;
    }
    private void allRecord(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("docappoint");
        myRef.keepSynced(true);
        mQuery = myRef.orderByChild("id");
        mQuery.keepSynced(true);
        FirebaseRecyclerAdapter<FindDoctorModel, FindDoctorHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<FindDoctorModel, FindDoctorHolder>(
                        FindDoctorModel.class,
                        R.layout.find_doctor_list,
                        FindDoctorHolder.class, mQuery
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
