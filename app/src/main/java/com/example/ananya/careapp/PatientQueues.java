package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientQueues extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef,ref;
    private RecyclerView mRecyclerView;

    public PatientQueues() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View patientQ=inflater.inflate(R.layout.fragment_patient_queues, container, false);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("docappoint");
        String did = ((MyApp) getActivity().getApplication()).getId();
        ref= myRef.child(did).child("appointment");
        mRecyclerView = (RecyclerView) patientQ.findViewById(R.id.appointment_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerAdapter<AppointmentModel, AppointmentHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<AppointmentModel, AppointmentHolder>(
                        AppointmentModel.class,
                        R.layout.appointment_list,
                        AppointmentHolder.class,ref
                ) {
                    /**
                     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
                     * to be displayed. The first two arguments correspond to the mLayout and mModelClass given to the constructor of
                     * this class. The third argument is the item's position in the list.
                     * <p>
                     * Your implementation should populate the view using the data contained in the model.
                     *
                     * @param viewHolder The view to populate
                     * @param model      The object containing the data used to populate the view
                     * @param position   The position in the list of the view being populated
                     */
                    @Override
                    protected void populateViewHolder(AppointmentHolder viewHolder, AppointmentModel model, int position) {
                        viewHolder.uid.setText(model.getPid());
                        viewHolder.uname.setText(model.getPname());
                        viewHolder.udate.setText(model.getDate());
                        viewHolder.utime.setText(model.getTime());
                    }
                };

        mRecyclerView.setAdapter(recyclerAdapter);


        return patientQ;
    }

}
