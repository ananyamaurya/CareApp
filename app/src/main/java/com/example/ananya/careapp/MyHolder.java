package com.example.ananya.careapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Ananya on 28-03-2018.
 */

public class MyHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MyHolder";

    public EditText mUserName;
    public TextView mFeedBack;
    public EditText mCompanyName;
    public TextView id;


    public MyHolder(View itemView) {
        super(itemView);

        mCompanyName = itemView.findViewById(R.id.hospitalname);
        mFeedBack = (TextView) itemView.findViewById(R.id.hospitaladdress);
        mUserName = itemView.findViewById(R.id.hoscity);
        id = itemView.findViewById(R.id.hosid);

    }

}