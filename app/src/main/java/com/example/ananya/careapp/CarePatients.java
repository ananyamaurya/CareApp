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

import com.example.ananya.careapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarePatients extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query mQuery;
    private RecyclerView mRecyclerView;

    public CarePatients() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v4= inflater.inflate(R.layout.fragment_patient, container, false);
        mRecyclerView = (RecyclerView) v4.findViewById(R.id.patient_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        allRecord();

        return v4;
    }
    private void allRecord(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("patients");
        myRef.keepSynced(true);
        mQuery = myRef.orderByChild("id");
        mQuery.keepSynced(true);
        FirebaseRecyclerAdapter<PatientModel, PatientHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<PatientModel, PatientHolder>(
                        PatientModel.class,
                        R.layout.patientmodellist,
                        PatientHolder.class, mQuery
                ) {
                    @Override
                    protected void populateViewHolder(PatientHolder viewHolder, PatientModel model, int position) {
                        viewHolder.uid.setText(model.getId());
                        viewHolder.uage.setText(model.getAge());
                        viewHolder.umobile.setText(model.getMobile());
                        viewHolder.ubg.setText(model.getBg());
                        viewHolder.uname.setText(model.getName());
                        viewHolder.ugender.setText(model.getGender());
                    }
                };

        mRecyclerView.setAdapter(recyclerAdapter);

    }
}
