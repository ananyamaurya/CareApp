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
public class CareAttendant extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query mQuery;
    private RecyclerView mRecyclerView;
    public CareAttendant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vx= inflater.inflate(R.layout.fragment_attendant, container, false);
        mRecyclerView = (RecyclerView) vx.findViewById(R.id.attendant_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        allRecord();


        return vx;
    }
    private void allRecord(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("attendants");
        myRef.keepSynced(true);
        mQuery = myRef.orderByChild("id");
        mQuery.keepSynced(true);
        FirebaseRecyclerAdapter<AttendantModel, AttendantHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<AttendantModel, AttendantHolder>(
                        AttendantModel.class,
                        R.layout.attendantmodellist,
                        AttendantHolder.class, mQuery
                ) {
                    @Override
                    protected void populateViewHolder(AttendantHolder viewHolder, AttendantModel model, int position) {
                        viewHolder.uid.setText(model.getId());
                        viewHolder.uage.setText(model.getAge());
                        viewHolder.ucity.setText(model.getCity());
                        viewHolder.uname.setText(model.getName());
                        viewHolder.ugender.setText(model.getGender());
                    }
                };

        mRecyclerView.setAdapter(recyclerAdapter);

    }
}
