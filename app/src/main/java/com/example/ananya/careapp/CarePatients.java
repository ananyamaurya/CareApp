package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarePatients extends Fragment {

    Button pview,pdelete;
    LinearLayout pl1,pl2;

    public CarePatients() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v4= inflater.inflate(R.layout.fragment_patient, container, false);
        pview=v4.findViewById(R.id.patientview);
        pdelete=v4.findViewById(R.id.patientdelete);
        pl1=v4.findViewById(R.id.patient1);
        pl2=v4.findViewById(R.id.patient2);

        return v4;
    }

}
