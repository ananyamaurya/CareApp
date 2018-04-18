package com.example.ananya.careapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewReports extends Fragment {

    Button repview,blopen,uropen;
    TextView blcheck,urcheck;
    EditText pdid;
    Uri blood,urine;
    public ViewReports() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_view_reports, container, false);
        repview = x.findViewById(R.id.testrepviwe);
        blopen = x.findViewById(R.id.blooodopen);
        uropen = x.findViewById(R.id.urineopen);
        blcheck = x.findViewById(R.id.blcheck);
        urcheck = x.findViewById(R.id.urcheck);
        pdid = x.findViewById(R.id.testviewid);
        repview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReports();
            }
        });
        blopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blood == null){
                    Toast.makeText(getActivity(),"Report is not available for this patient",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(blood, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        uropen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urine == null){
                    Toast.makeText(getActivity(),"Report is not available for this patient",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(urine, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        return x;
    }
    public void getReports(){
        FirebaseDatabase d = FirebaseDatabase.getInstance();
        DatabaseReference dr = d.getReference("patients");
        String pid = pdid.getText().toString();
        FirebaseStorage s = FirebaseStorage.getInstance();

        if(!TextUtils.isEmpty(pid)){
            dr.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(pid)){
                        blcheck.setText(dataSnapshot.child(pid).child("reports").child("blood").getValue().toString());
                        urcheck.setText(dataSnapshot.child(pid).child("reports").child("urine").getValue().toString());
                        StorageReference br = s.getReference("documents/"+dataSnapshot.child(pid).child("reports").child("blooduri").getValue().toString());
                        StorageReference ur =s.getReference("documents/"+dataSnapshot.child(pid).child("reports").child("urineuri").getValue().toString());
                        br.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                blood =uri;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        ur.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urine =uri;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
