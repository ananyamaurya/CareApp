package com.example.ananya.careapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ananya.careapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareAttendant extends Fragment {
Button aview,adelete;
LinearLayout  al1,al2;
    public CareAttendant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_attendant, container, false);
        aview=v1.findViewById(R.id.attendantview);
        adelete=v1.findViewById(R.id.attendantdelete);
        al1=v1.findViewById(R.id.attendant1);
        al2=v1.findViewById(R.id.attendant2);



        return v1;
    }

}
