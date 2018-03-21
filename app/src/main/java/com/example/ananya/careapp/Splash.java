package com.example.ananya.careapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        ImageView img = (ImageView) findViewById(R.id.i);
        img.setBackgroundResource(R.drawable.list);
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.start();
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        img.startAnimation(animation);
        Handler h=new Handler();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getUserData();
                }
            }, 3000);
        }else{
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }
    public void getUserData(){
        try{
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            final String pdsid= user.getUid().toString();
            DatabaseReference datanapshot = FirebaseDatabase.getInstance().getReference("caremedusers");
            datanapshot.keepSynced(true);
            datanapshot.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(pdsid)) {
                        if(snapshot.child(pdsid).child("occupation").getValue().toString()=="null"){
                            ((MyApp) getApplication()).setOccupation("Khali Ba");
                        }else{
                            String ss= snapshot.child(pdsid).child("occupation").getValue().toString();
                            ((MyApp) getApplication()).setOccupation(ss);
                            if(Objects.equals(ss, "Doctor")){
                                startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                                Toast.makeText(getApplicationContext(),"Occupation Fetched",Toast.LENGTH_SHORT).show();
                            }else if(Objects.equals(ss, "Attendant")){
                                startActivity(new Intent(getApplicationContext(),AttendantHome.class));
                                Toast.makeText(getApplicationContext(),"Occupation Fetched",Toast.LENGTH_SHORT).show();
                            }else if(Objects.equals(ss, "Patient")){
                                startActivity(new Intent(getApplicationContext(),PatientHome.class));
                                Toast.makeText(getApplicationContext(),"Occupation Fetched",Toast.LENGTH_SHORT).show();
                            }else if(Objects.equals(ss, "Admin")){
                                startActivity(new Intent(getApplicationContext(),Adminhome.class));
                                Toast.makeText(getApplicationContext(),"Occupation Fetched",Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                    else {
                        ((MyApp) getApplication()).setOccupation("Nahi Milal");
                        Toast.makeText(getApplicationContext(),"No Occupation Added",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                }
            });
            datanapshot.keepSynced(true);
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),"Soem Error Occured",Toast.LENGTH_SHORT).show();
        }

    }
}
