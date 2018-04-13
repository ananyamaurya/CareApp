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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActPatProfile extends Activity {
    CircleImageView imageUpload;
    EditText ename,eage,emobile,eweight;
    Spinner egender,ecity;
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
        setContentView(R.layout.activity_act_pat_profile);
        database =FirebaseDatabase.getInstance();
        pickimage = findViewById(R.id.actpatprofedit);
        savepro = findViewById(R.id.actpatprofsave);
        egender = findViewById(R.id.actpatprofgenderspin);
        ecity = findViewById(R.id.actpatprofilecityspin);
        ename = findViewById(R.id.actpatprofname);
        eage = findViewById(R.id.actpatprofage);
        eweight = findViewById(R.id.actpatprofweight);
        imageUpload =findViewById(R.id.actpatimageUpload);
        emobile = findViewById(R.id.actpatprofilemob);
        savepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                finish();

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
                String Gender = egender.getSelectedItem().toString();
                String City = ecity.getSelectedItem().toString();
                String Weight = eweight.getText().toString();
                if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Age) && !TextUtils.isEmpty(Mobile) && !TextUtils.isEmpty(Weight)) {
                    if(City.equals("Select City")){
                        Toast.makeText(getApplicationContext(),"Select a City",Toast.LENGTH_SHORT).show();
                    }else{
                        uploadImage(Name,Age,Mobile,Gender,City,Weight);
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
    private void uploadImage(final String name, final String age, final String mobile, final String gender, final String city,final String weight) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            docref=database.getReference("patients");
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
                                                        docref.child(id).child("city").setValue(city);
                                                        docref.child(id).child("mobile").setValue(mobile);
                                                        docref.child(id).child("weight").setValue(weight);
                                                        prof.child(uid).child("profile").setValue("true");
                                                        prof.child(uid).child("name").setValue(name);
                                                    }
                                                }
                                            });
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(),PatientHome.class));
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
        }else{
            Toast.makeText(getApplicationContext(),"Select Profile Picture",Toast.LENGTH_SHORT).show();
        }
    }
}
