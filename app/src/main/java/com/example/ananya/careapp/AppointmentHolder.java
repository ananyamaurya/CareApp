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

public class AppointmentHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "Appointment Holder";

    public TextView uid;
    public TextView uname;
    public TextView udate;
    public TextView utime;
    public AppointmentHolder(View itemView) {
        super(itemView);
        uid = itemView.findViewById(R.id.apid);
        uname =  itemView.findViewById(R.id.apname);
        udate = itemView.findViewById(R.id.apdate);
        utime =itemView.findViewById(R.id.aptime);
    }
}