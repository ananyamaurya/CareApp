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

import org.w3c.dom.Text;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatViewReport extends Fragment {
    Button blopen,uropen;
    TextView blcheck,urcheck;
    Uri blood,urine;

    public PatViewReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_pat_view_report, container, false);
        final String pid = ((MyApp) getActivity().getApplication()).getId();
        blopen = x.findViewById(R.id.patblooodopen);
        uropen = x.findViewById(R.id.paturineopen);
        blcheck = x.findViewById(R.id.patblcheck);
        urcheck = x.findViewById(R.id.paturcheck);
        FirebaseDatabase d = FirebaseDatabase.getInstance();
        DatabaseReference dr = d.getReference("patients");
        FirebaseStorage s = FirebaseStorage.getInstance();

        if(!TextUtils.isEmpty(pid)){
            dr.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(pid)){
                        StorageReference br,ur;
                        if(!TextUtils.isEmpty(dataSnapshot.child(pid).child("reports").child("blood").getValue().toString())){
                            blcheck.setText(Objects.requireNonNull(dataSnapshot.child(pid).child("reports").child("blood").getValue()).toString());
                            br = s.getReference("documents/"+ Objects.requireNonNull(dataSnapshot.child(pid).child("reports").child("blooduri").getValue()).toString());
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
                        }else{
                            blcheck.setText("false");
                        }


                        if(!TextUtils.isEmpty(dataSnapshot.child(pid).child("reports").child("urine").getValue().toString())){
                            urcheck.setText(Objects.requireNonNull(dataSnapshot.child(pid).child("reports").child("urine").getValue()).toString());
                            ur = s.getReference("documents/"+ Objects.requireNonNull(dataSnapshot.child(pid).child("reports").child("urineuri").getValue()).toString());
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
                        }else{
                            urcheck.setText("false");
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
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

}
