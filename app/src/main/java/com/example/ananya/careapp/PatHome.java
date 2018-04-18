package com.example.ananya.careapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatHome extends Fragment {

    CardView c1,c2,c3,c4;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.doc,R.drawable.doc1,R.drawable.dc,R.drawable.dc3};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    public PatHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View patHome = inflater.inflate(R.layout.fragment_pat_home, container, false);
        c1=patHome.findViewById(R.id.patappointment);
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) patHome.findViewById(R.id.pager2);
        mPager.setAdapter(new MyAdapter(getActivity(),XMENArray));
        CircleIndicator indicator = (CircleIndicator) patHome.findViewById(R.id.indicator2);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
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
