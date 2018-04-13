package com.example.ananya.careapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ananya.careapp.DoctorVisit;
import com.example.ananya.careapp.R;

/**
 * Created by Ananya on 29-03-2018.
 */

public class FindDoctorHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "DVLHolder";

    public TextView uid;
    public TextView uname;
    public TextView uage;
    public TextView ugender;
    public TextView uspec;
    public TextView uhos;

    public FindDoctorHolder(View itemView) {
        super(itemView);

        uid = (TextView) itemView.findViewById(R.id.fdid);
        uname =  itemView.findViewById(R.id.fddocname);
        uage = itemView.findViewById(R.id.fddocage);
        ugender =itemView.findViewById(R.id.fddocgender);
        uspec = itemView.findViewById(R.id.fddocspec);
        uhos = itemView.findViewById(R.id.fddochospital);
    }

}