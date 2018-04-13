package com.example.ananya.careapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ActDocProfile extends Activity {
    CircleImageView imageUpload;
    EditText ename,eage,emobile;
    Spinner egender,espec,ehos,ecity;
    Button pickimage,savepro;
    FirebaseDatabase database;
    DatabaseReference ref,docref,prof;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_doc_profile);
        database =FirebaseDatabase.getInstance();
        ref = database.getReference("hospitals");
        pickimage = findViewById(R.id.actdocprofedit);
        savepro = findViewById(R.id.actdocprofsave);
        egender = findViewById(R.id.actdocprofgenderspin);
        espec =findViewById(R.id.actdocprofilespecspin);
        ehos = findViewById(R.id.actdocprofilehosspin);
        ecity = findViewById(R.id.actdocprofilecityspin);
        ename = findViewById(R.id.actdocprofname);
        eage = findViewById(R.id.actdocprofage);
        imageUpload =findViewById(R.id.actimageUpload);
        emobile = findViewById(R.id.actdocprofilemob);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    final List<String> Hospitals = new ArrayList<String>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String key = String.valueOf(postSnapshot.getKey());
                        Hospitals.add(key);
                    }

                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Hospitals);
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ehos.setAdapter(areasAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ehos.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                ((TextView) view).setTextColor(Color.BLACK);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        pickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        savepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = ename.getText().toString();
                String Age = eage.getText().toString();
                String Mobile = emobile.getText().toString();
                String Gender =egender.getSelectedItem().toString();
                String Special = espec.getSelectedItem().toString();
                String Hospital = ehos.getSelectedItem().toString();
                String City = ecity.getSelectedItem().toString();
                if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Age) && !TextUtils.isEmpty(Mobile)) {
                    if(City.equals("Select City")){
                        Toast.makeText(getApplicationContext(),"Select a City",Toast.LENGTH_SHORT).show();
                    }else{
                        uploadImage(Name,Age,Mobile,Gender,Special,Hospital,City);
                    }
                }
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                filePath = result.getUri();
                imageUpload.setImageURI(filePath);
            }else if (resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
    }
    private void uploadImage(final String name, final String age, final String mobile, final String gender, final String spec, final String hos, final String city) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            docref=database.getReference("docappoint");
            prof =database.getReference("caremedusers");
            final StorageReference reff = storageReference.child("images/"+ UUID.randomUUID().toString());
            reff.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            reff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .setPhotoUri(filePath)
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        String id = ((MyApp) getApplication()).getId();
                                                        String uid = user.getUid();
                                                        docref.child(id).child("name").setValue(name);
                                                        docref.child(id).child("age").setValue(age);
                                                        docref.child(id).child("gender").setValue(gender);
                                                        docref.child(id).child("hospital").setValue(hos);
                                                        docref.child(id).child("city").setValue(city);
                                                        docref.child(id).child("specialization").setValue(spec);
                                                        docref.child(id).child("mobile").setValue(mobile);
                                                        docref.child(id).child("id").setValue(id);
                                                        prof.child(uid).child("profile").setValue("true");
                                                        prof.child(uid).child("name").setValue(name);
                                                        docref.child(id).child("cityspec").setValue(city+spec);
                                                        docref.child(id).child("cityhos").setValue(city+hos);
                                                    }
                                                }
                                            });
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getApplicationContext(), "Profile Not Updated", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}