package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DocHome extends Fragment {
    CardView c1,c2,c3,c4;

    public DocHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View docHome = inflater.inflate(R.layout.fragment_doc_home, container, false);
        c1=docHome.findViewById(R.id.docpaque);
        c2=docHome.findViewById(R.id.docpadet);
        c3=docHome.findViewById(R.id.docprvis);
        c4=docHome.findViewById(R.id.docupcon);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                PatientQueues h1=new PatientQueues();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.commit();
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                PatientDetails h1=new PatientDetails();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.commit();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                DoctorVisit h1=new DoctorVisit();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.commit();
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                UpdateConditions h1=new UpdateConditions();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.commit();
            }
        });
        return docHome;
    }

}
