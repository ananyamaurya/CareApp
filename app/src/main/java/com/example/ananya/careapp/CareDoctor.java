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
public class CareDoctor extends Fragment {
    Button dview,ddelete;
    LinearLayout dl1,dl2;

    public CareDoctor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v2= inflater.inflate(R.layout.fragment_doctor, container, false);
        dview=v2.findViewById(R.id.doctorview);
        ddelete=v2.findViewById(R.id.doctordelete);
        dl1=v2.findViewById(R.id.doctor11);
        dl2=v2.findViewById(R.id.doctor2);
        dview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl1.setVisibility(View.VISIBLE);
                dl2.setVisibility(View.GONE);
            }
        });
        ddelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl2.setVisibility(View.VISIBLE);
                dl1.setVisibility(View.GONE);
            }
        });
        return v2;
    }

}
