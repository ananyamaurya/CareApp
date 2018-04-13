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
public class DocHome extends Fragment {
    CardView c1,c2,c3,c4;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.doc,R.drawable.doc1,R.drawable.dc,R.drawable.dc3};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
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
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) docHome.findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(getActivity(),XMENArray));
        CircleIndicator indicator = (CircleIndicator) docHome.findViewById(R.id.indicator);
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
                PatientQueues h1=new PatientQueues();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.addToBackStack("DoctorHome");
                ft.commit();
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                PatientDetails h1=new PatientDetails();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.addToBackStack("PatientDetails");
                ft.commit();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                DoctorVisit h1=new DoctorVisit();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.addToBackStack("DoctorVisit");
                ft.commit();
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                UpdateConditions h1=new UpdateConditions();
                ft.replace(R.id.dochomeframe,h1,"Home");
                ft.addToBackStack("UpdateCondition");
                ft.commit();
            }
        });
        return docHome;
    }
    private void init() {

    }

}
