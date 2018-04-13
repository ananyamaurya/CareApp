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
public class PatHome extends Fragment {

    CardView c1,c2,c3,c4;
    public PatHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View patHome = inflater.inflate(R.layout.fragment_pat_home, container, false);
        c1=patHome.findViewById(R.id.patappointment);
        c2=patHome.findViewById(R.id.patfinddoc);
        c3=patHome.findViewById(R.id.patfindhos);
        c4=patHome.findViewById(R.id.patmedhis);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                PatientAppointment h1=new PatientAppointment();
                ft.replace(R.id.patienthomeframe,h1,"Home");
                ft.addToBackStack("Appointment");
                ft.commit();
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                FindDoctor h1=new FindDoctor();
                ft.replace(R.id.patienthomeframe,h1,"Home");
                ft.addToBackStack("FindDoctor");
                ft.commit();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                FindHospital h1=new FindHospital();
                ft.replace(R.id.patienthomeframe,h1,"Home");
                ft.addToBackStack("FindHospital");
                ft.commit();
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                PatientMedicalHistory h1=new PatientMedicalHistory();
                ft.replace(R.id.patienthomeframe,h1,"Home");
                ft.commit();
            }
        });
        return patHome;
    }

}
