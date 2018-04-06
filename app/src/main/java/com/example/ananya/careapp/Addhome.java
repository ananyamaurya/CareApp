package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Addhome extends Fragment {
    CardView c1,c2,c3,c4;
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
        return x;
    }

}
