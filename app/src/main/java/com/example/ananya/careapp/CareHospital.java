package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ananya.careapp.HospitalModel;
import com.example.ananya.careapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareHospital extends Fragment {
    Button hadd, hview,addhospital;
    LinearLayout hl1,hl2;
    EditText txtname,txtcity,txtaddress;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query mQuery;
    private RecyclerView mRecyclerView;
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
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("hospitals");
        myRef.keepSynced(true);
        mQuery = myRef.orderByChild("hospitalName");
        mQuery.keepSynced(true);
        mRecyclerView = (RecyclerView) v3.findViewById(R.id.hospital_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        allRecord();

        hadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            hl1.setVisibility(View.VISIBLE);
            hl2.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            }
        });
        hview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hl2.setVisibility(View.VISIBLE);
                hl1.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
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
                    HospitalModel h=new HospitalModel(name,city,address);
                    ref.child(name+city).setValue(h);
                    ref.keepSynced(true);
                    Toast.makeText(getActivity(),"HospitalModel Added",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v3;
    }
    private void allRecord(){

        FirebaseRecyclerAdapter<HospitalModel, MyHolder> recyclerAdapter = new
                FirebaseRecyclerAdapter<HospitalModel, MyHolder>(
                        HospitalModel.class,
                        R.layout.hospital_list_layout,
                        MyHolder.class, myRef
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

                    }

                };

        mRecyclerView.setAdapter(recyclerAdapter);

    }

}
