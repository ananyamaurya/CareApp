package com.example.ananya.careapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDoc extends Fragment {
    EditText dname,dage,dmobile;
    Spinner dhospital,dspecializaton,dgender,dcity;
    TextView tname,tage,tmobile,thospital,tspecial,tgender,tcity;
    Button dedit,dsave;
    int i=0,j=0;
    CircleImageView imageUpload,image;
    FirebaseDatabase database;
    DatabaseReference ref,docref,prof,docreff;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath,zix;
    private final int PICK_IMAGE_REQUEST = 71;
    public ProfileDoc() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileDoc = inflater.inflate(R.layout.fragment_profile_doc, container, false);
        database =FirebaseDatabase.getInstance();
        ref = database.getReference("hospitals");
        dname = profileDoc.findViewById(R.id.editdocprofname);
        dage = profileDoc.findViewById(R.id.editdocprofage);
        dmobile = profileDoc.findViewById(R.id.editdocprofilemob);
        thospital = profileDoc.findViewById(R.id.docprofhos);
        tspecial = profileDoc.findViewById(R.id.docprofspec);
        tgender = profileDoc.findViewById(R.id.docprofgender);
        tcity =profileDoc.findViewById(R.id.docprofcity);
        tname =profileDoc.findViewById(R.id.docprofname);
        tage = profileDoc.findViewById(R.id.docprofage);
        tmobile = profileDoc.findViewById(R.id.docprofmobile);
        dhospital = profileDoc.findViewById(R.id.docprofilehosspin);
        dspecializaton = profileDoc.findViewById(R.id.docprofilespecspin);
        dgender = profileDoc.findViewById(R.id.docprofgenderspin);
        dcity = profileDoc.findViewById(R.id.docprofilecityspin);
        dedit = profileDoc.findViewById(R.id.docprofedit);
        dsave = profileDoc.findViewById(R.id.docprofilesave);
        imageUpload = profileDoc.findViewById(R.id.docprofcircleimage1);
        image = profileDoc.findViewById(R.id.docprofcircleimage);
        FirebaseUser u =FirebaseAuth.getInstance().getCurrentUser();
        zix = u.getPhotoUrl();
        Picasso.with(getActivity()).load(zix).into(image);
        getProfile();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    final List<String> Hospitals = new ArrayList<String>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String key = String.valueOf(postSnapshot.getKey());
                        Hospitals.add(key);
                    }

                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Hospitals);
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dhospital.setAdapter(areasAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dhospital.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                ((TextView) view).setTextColor(Color.BLACK);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        dedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        dsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( i==0 ){
                    tname.setVisibility(View.GONE);
                    tage.setVisibility(View.GONE);
                    tgender.setVisibility(View.GONE);
                    thospital.setVisibility(View.GONE);
                    tspecial.setVisibility(View.GONE);
                    tcity.setVisibility(View.GONE);
                    tmobile.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    dhospital.setVisibility(View.VISIBLE);
                    dcity.setVisibility(View.VISIBLE);
                    dspecializaton.setVisibility(View.VISIBLE);
                    dgender.setVisibility(View.VISIBLE);
                    dname.setVisibility(View.VISIBLE);
                    imageUpload.setVisibility(View.VISIBLE);
                    dage.setVisibility(View.VISIBLE);
                    dmobile.setVisibility(View.VISIBLE);
                    dedit.setVisibility(View.VISIBLE);
                    dsave.setText("SAVE");
                    i=1;
                }else{

                    String Name = dname.getText().toString();
                    String Age = dage.getText().toString();
                    String Mobile = dmobile.getText().toString();
                    String Gender = dgender.getSelectedItem().toString();
                    String Hospital = dhospital.getSelectedItem().toString();
                    String Special = dspecializaton.getSelectedItem().toString();
                    String City =dcity.getSelectedItem().toString();
                    uploadImage(Name,Age,Mobile,Gender,Special,Hospital,City);
                    tname.setVisibility(View.VISIBLE);
                    tage.setVisibility(View.VISIBLE);
                    tgender.setVisibility(View.VISIBLE);
                    thospital.setVisibility(View.VISIBLE);
                    tspecial.setVisibility(View.VISIBLE);
                    tcity.setVisibility(View.VISIBLE);
                    tmobile.setVisibility(View.VISIBLE);
                    dhospital.setVisibility(View.GONE);
                    dcity.setVisibility(View.GONE);
                    dspecializaton.setVisibility(View.GONE);
                    dgender.setVisibility(View.GONE);
                    dname.setVisibility(View.GONE);
                    dage.setVisibility(View.GONE);
                    dmobile.setVisibility(View.GONE);
                    dsave.setText("EDIT");
                    getProfile();
                    FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                    DocHome h1=new DocHome();
                    ft.replace(R.id.dochomeframe,h1,"Home");
                    ft.addToBackStack("PatientHome");
                    ft.commit();
                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    i=0;
                }
            }
        });
        return profileDoc;
    }
    public void getProfile(){
        final String id = ((MyApp) getActivity().getApplication()).getId();
        docreff=database.getReference("docappoint");
        docreff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tname.setText(dataSnapshot.child(id).child("name").getValue().toString());
                tage.setText(dataSnapshot.child(id).child("age").getValue().toString());
                tgender.setText(dataSnapshot.child(id).child("gender").getValue().toString());
                thospital.setText(dataSnapshot.child(id).child("hospital").getValue().toString());
                tspecial.setText(dataSnapshot.child(id).child("specialization").getValue().toString());
                tcity.setText(dataSnapshot.child(id).child("city").getValue().toString());
                tmobile.setText(dataSnapshot.child(id).child("mobile").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1,1)
                    .start(getContext(), this);
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

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        docref=database.getReference("docappoint");
        prof =database.getReference("caremedusers");
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final StorageReference reff = storageReference.child("images/"+ UUID.randomUUID().toString());
            reff.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
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
                                                        String id = ((MyApp) getActivity().getApplication()).getId();
                                                        String uid = user.getUid();
                                                        docref.child(id).child("name").setValue(name);
                                                        docref.child(id).child("age").setValue(age);
                                                        docref.child(id).child("gender").setValue(gender);
                                                        docref.child(id).child("hospital").setValue(hos);
                                                        docref.child(id).child("city").setValue(city);
                                                        docref.child(id).child("specialization").setValue(spec);
                                                        docref.child(id).child("mobile").setValue(mobile);
                                                        prof.child(uid).child("profile").setValue("true");
                                                        docref.child(id).child("id").setValue(id);
                                                        prof.child(uid).child("name").setValue(name);
                                                        docref.child(id).child("cityspec").setValue(city+spec);
                                                        docref.child(id).child("cityhos").setValue(city+hos);
                                                    }
                                                }
                                            });
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getActivity(), "Profile Not Updated", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String id = ((MyApp) getActivity().getApplication()).getId();
            String uid = user.getUid();
            docref.child(id).child("name").setValue(name);
            docref.child(id).child("age").setValue(age);
            docref.child(id).child("gender").setValue(gender);
            docref.child(id).child("hospital").setValue(hos);
            docref.child(id).child("city").setValue(city);
            docref.child(id).child("id").setValue(id);
            docref.child(id).child("specialization").setValue(spec);
            docref.child(id).child("mobile").setValue(mobile);
            prof.child(uid).child("profile").setValue("true");
            prof.child(uid).child("name").setValue(name);
            docref.child(id).child("cityspec").setValue(city+spec);
            docref.child(id).child("cityhos").setValue(city+hos);
        }
    }
}