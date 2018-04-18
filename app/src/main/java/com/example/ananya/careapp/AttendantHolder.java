package com.example.ananya.careapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AttendantHolder extends RecyclerView.ViewHolder{
    public TextView uid;
    public TextView uname;
    public TextView uage;
    public TextView ugender;
    public TextView ucity;

    public AttendantHolder(View itemView) {
        super(itemView);
        uid = itemView.findViewById(R.id.amlid);
        uname =  itemView.findViewById(R.id.amlname);
        uage = itemView.findViewById(R.id.amlage);
        ugender =itemView.findViewById(R.id.amlgender);
        ucity =itemView.findViewById(R.id.amlcity);
    }
}
