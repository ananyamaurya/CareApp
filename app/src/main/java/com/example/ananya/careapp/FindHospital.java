package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindHospital extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query mQuery,z,xQuery;
    private RecyclerView mRecyclerView;
    private Spinner selectCity;
    String x;
    public FindHospital() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fhos= inflater.inflate(R.layout.fragment_find_hospital, container, false);
        database = FirebaseDatabase.getInstance();
        selectCity = fhos.findViewById(R.id.fhoscity);
        myRef = database.getReference("hospitals");
        myRef.keepSynced(true);
        z=myRef.orderByChild("id");
        mQuery = myRef.orderByChild("hospitalCity").equalTo("Jalandhar");
        xQuery = myRef.orderByChild("hospitalCity").equalTo("Phagwara");
        mQuery.keepSynced(true);
        xQuery.keepSynced(true);
        z.keepSynced(true);
        selectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                x= selectCity.getSelectedItem().toString();
                if(x.equals("Jalandhar")){
                    allRecord(mQuery);
                }else if(x.equals("Phagwara")){
                    allRecord(xQuery);
                }else{
                    allRecord(z);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
        mRecyclerView = (RecyclerView) fhos.findViewById(R.id.fhosrecycler);
        allRecord(mQuery);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        return fhos;
    }
    private void allRecord(Query ref){

        FirebaseRecyclerAdapter<HospitalModel, MyHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<HospitalModel, MyHolder>(
                        HospitalModel.class,
                        R.layout.hospital_list_layout,
                        MyHolder.class, ref
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
                    protected void populateViewHolder(MyHolder viewHolder, HospitalModel model, int position) {
                        viewHolder.mCompanyName.setText(model.getHospitalName());
                        viewHolder.mFeedBack.setText(model.getHospitalCity());
                        viewHolder.mUserName.setText(model.getHospitalAddress());
                        viewHolder.id.setText(model.getId());
                    }

                };

        mRecyclerView.setAdapter(recyclerAdapter);
    }
}
