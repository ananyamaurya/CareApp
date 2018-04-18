package com.example.ananya.careapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PatientHolder extends RecyclerView.ViewHolder {
    public TextView uid;
    public TextView uname;
    public TextView uage;
    public TextView ugender;
    public TextView ubg;
    public TextView umobile;

    public PatientHolder(View itemView) {
        super(itemView);
        uid = itemView.findViewById(R.id.pmlid);
        uname =  itemView.findViewById(R.id.pmlname);
        uage = itemView.findViewById(R.id.pmlage);
        ugender =itemView.findViewById(R.id.pmlgender);
        ubg =itemView.findViewById(R.id.pmlbg);
        umobile = itemView.findViewById(R.id.pmlmobile);
    }
}
