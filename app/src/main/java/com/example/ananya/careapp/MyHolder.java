package com.example.ananya.careapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ananya on 28-03-2018.
 */

public class MyHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MyHolder";

    public TextView mUserName;
    public TextView mFeedBack;
    public TextView mCompanyName;

    public MyHolder(View itemView) {
        super(itemView);

        mCompanyName = (TextView) itemView.findViewById(R.id.hospitalname);
        mFeedBack = (TextView) itemView.findViewById(R.id.hospitaladdress);
        mUserName = (TextView) itemView.findViewById(R.id.hoscity);
    }

}