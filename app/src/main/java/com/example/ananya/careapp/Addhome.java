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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class Addhome extends Fragment {
    CardView c1,c2,c3,c4;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.doc,R.drawable.doc1,R.drawable.dc,R.drawable.dc3};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    public Addhome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View x = inflater.inflate(R.layout.fragment_addhome, container, false);
         c1=x.findViewById(R.id.hoscard);
         c2=x.findViewById(R.id.doccard);
        c3=x.findViewById(R.id.attcard);
        c4=x.findViewById(R.id.patcard);
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) x.findViewById(R.id.pager3);
        mPager.setAdapter(new MyAdapter(getActivity(),XMENArray));
        CircleIndicator indicator = (CircleIndicator) x.findViewById(R.id.indicator3);
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
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                CareHospital h1=new CareHospital();
                ft.replace(R.id.homeframe,h1,"Home");
                ft.addToBackStack("DoctorHome");
                ft.commit();
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                CareDoctor careDoctor=new CareDoctor();
                ft.replace(R.id.homeframe,careDoctor,"Home");
                ft.addToBackStack("PatientDetails");
                ft.commit();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                CareAttendant attendant=new CareAttendant();
                ft.replace(R.id.homeframe,attendant,"Home");
                ft.addToBackStack("DoctorVisit");
                ft.commit();
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                CarePatients carePatients =new CarePatients();
                ft.replace(R.id.homeframe,carePatients,"Home");
                ft.addToBackStack("UpdateCondition");
                ft.commit();
            }
        });
        return x;
    }

}
