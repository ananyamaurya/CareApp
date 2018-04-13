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
public class PatientMedicalHistory extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef,ref;
    private RecyclerView mRecyclerView;
    private Query mQuery;
    public PatientMedicalHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pmedhis = inflater.inflate(R.layout.fragment_patient_medical_history, container, false);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("patients");
        String pid =((MyApp) getActivity().getApplication()).getId();
        ref= myRef.child(pid).child("medicalHistory");
        mQuery = ref.orderByChild("utime");
        mRecyclerView = (RecyclerView) pmedhis.findViewById(R.id.patientmedicalhistory_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerAdapter<DocVisitModel, DocVisitHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<DocVisitModel, DocVisitHolder>(
                        DocVisitModel.class,
                        R.layout.docvisitlist,
                        DocVisitHolder.class,mQuery
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
                    protected void populateViewHolder(DocVisitHolder viewHolder, DocVisitModel model, int position) {
                        viewHolder.uID.setText(model.getuID());
                        viewHolder.utime.setText(model.getUtime());
                        viewHolder.udoctor.setText(model.getUdoctor());
                        viewHolder.udisease.setText(model.getUdisease());
                        viewHolder.ucondition.setText(model.getUcondition());
                        viewHolder.uprescription.setText(model.getUprescription());
                    }
                };

        mRecyclerView.setAdapter(recyclerAdapter);

        return pmedhis;
    }

}
