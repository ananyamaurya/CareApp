package com.example.ananya.careapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateConditions extends Fragment {
    EditText pid,disease,prescription;
    Button submit;
    Spinner condition;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref= database.getReference("patients");
    public UpdateConditions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_update_conditions, container, false);
        pid=v.findViewById(R.id.ucpid);
        disease=v.findViewById(R.id.ucdisease);
        prescription=v.findViewById(R.id.ucpres);
        submit=v.findViewById(R.id.ucsub);
        condition=v.findViewById(R.id.uccondi);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCondition();
            }
        });
        return v;
    }
    public void setCondition(){
        final String spid = pid.getText().toString();
        final String sdisease = disease.getText().toString();
        final String spres = prescription.getText().toString();
        final String scondi = condition.getSelectedItem().toString();
        final String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        final String sdoctor = user.getDisplayName();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(spid)) {
                    long count = (long)snapshot.child(spid).child("medicalCount").getValue();
                    count=count+1;
                    ref.child(spid).child("medicalCount").setValue(count);
                    DocVisitModel d = new DocVisitModel(spid,timeStamp,spres,sdoctor,scondi,sdisease);
                    ref.child(spid).child("medicalHistory").child(String.valueOf(count)).setValue(d);

                }
                else {
                    Toast.makeText(getActivity(),"Patient Not Found",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        ref.keepSynced(true);
    }
}
