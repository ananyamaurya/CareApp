package com.example.ananya.careapp;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddReports extends Fragment {
    EditText pid;
    TextView bloodava,urineava;
    Button fetch,bloodc,bloods,urines,urinec;
    String availb,availu,blooduid,urineuid;
    Uri blooduri,urineuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageReference ref,ref2,bloodref,urineref;
    int bloodi=0;
    public AddReports() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addRep = inflater.inflate(R.layout.fragment_add_reports, container, false);
        fetch = addRep.findViewById(R.id.testrepfetch);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        pid = addRep.findViewById(R.id.testrepeid);
        bloodava = addRep.findViewById(R.id.bloodavail);
        urineava = addRep.findViewById(R.id.urineavail);
        bloodc = addRep.findViewById(R.id.blooodselect);
        urinec = addRep.findViewById(R.id.urineselect);
        bloods = addRep.findViewById(R.id.blooodupload);
        urines = addRep.findViewById(R.id.urineupload);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=pid.getText().toString();
                if(!TextUtils.isEmpty(s)) {
                    getDetails(s);
                }else{
                    Toast.makeText(getActivity(),"ID cannot be Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bloods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=pid.getText().toString();
                if(!TextUtils.isEmpty(s)) {
                    uploadImage(blooduri,"blood",s);
                    bloodref = storageReference.child("documents/"+ blooduid);
                }else{
                    Toast.makeText(getActivity(),"ID cannot be Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bloodc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage("blood");
            }
        });
        urines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=pid.getText().toString();
                if(!TextUtils.isEmpty(s)) {
                    uploadImage(urineuri,"urine",s);
                }else{
                    Toast.makeText(getActivity(),"ID cannot be Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
        urinec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage("urine");
            }
        });
        return addRep;
    }
    private void chooseImage(String role) {
        Intent intent = new Intent();
        intent.setType("application/pdf/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(role.equals("blood")){
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
        }
        else{
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), 2);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode==1) {
                blooduri = data.getData();
                Toast.makeText(getActivity(), "File Selected ", Toast.LENGTH_SHORT).show();
            }else{
                urineuri = data.getData();
                Toast.makeText(getActivity(), "File Selected ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void getDetails(final String pdsid) {
        DatabaseReference datanapshot = FirebaseDatabase.getInstance().getReference("patients");
        datanapshot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(pdsid)) {
                    availb = snapshot.child(pdsid).child("reports").child("blood").getValue().toString();
                    availu = snapshot.child(pdsid).child("reports").child("urine").getValue().toString();
                    if(availb.equals("true")){
                        bloodava.setText("true");
                    }
                    if(availu.equals("true")){
                        urineava.setText("true");
                    }
                } else {
                    Toast.makeText(getActivity(), "Patient Doesn't Exist With this ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        datanapshot.keepSynced(true);
    }
    private void uploadImage(Uri path, final String ss,final String pdsid) {

        if(path != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final DatabaseReference dataapshot = FirebaseDatabase.getInstance().getReference("patients");
            String filename=UUID.randomUUID().toString()+".pdf";
            ref = storageReference.child("documents/"+ filename);
            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            if(ss.equals("urine")){
                            dataapshot.child(pdsid).child("reports").child("urine").setValue("true");
                            dataapshot.child(pdsid).child("reports").child("urineuri").setValue(filename);
                            }else{
                                dataapshot.child(pdsid).child("reports").child("blood").setValue("true");
                                dataapshot.child(pdsid).child("reports").child("blooduri").setValue(filename);
                            }

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });

        }
    }
}
