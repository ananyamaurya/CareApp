package com.example.ananya.careapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ananya.careapp.DoctorVisit;
import com.example.ananya.careapp.R;

/**
 * Created by Ananya on 29-03-2018.
 */

public class PatientQueuesHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "DPQHolder";

    public TextView uID;
    public TextView utime;
    public TextView uprescription;
    public TextView udoctor;
    public TextView ucondition;
    public TextView udisease;

    public PatientQueuesHolder(View itemView) {
        super(itemView);
        uID = (TextView) itemView.findViewById(R.id.dvlid);
        utime = (TextView) itemView.findViewById(R.id.dvltime);
        uprescription = itemView.findViewById(R.id.dvlpres);
        ucondition =itemView.findViewById(R.id.dvlcon);
        udoctor = itemView.findViewById(R.id.dvldoc);
        udisease = itemView.findViewById(R.id.dvldis);
    }
}