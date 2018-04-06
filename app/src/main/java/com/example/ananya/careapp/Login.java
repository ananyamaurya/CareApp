package com.example.ananya.careapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Login extends AppCompatActivity {
    EditText emailtext, passwordtext;
    Button signin,signup;
    private FirebaseAuth mAuth;
    String password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        emailtext=findViewById(R.id.e_mailText);
        passwordtext= findViewById(R.id.passwordText);
        signin=findViewById(R.id.login);
        signup=findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailtext.getText().toString();
                password = passwordtext.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {

                                    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();

                                    if (fuser.isEmailVerified())
                                    {
                                        Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                        getUserData();
                                    }
                                    else
                                    {
                                        Toast.makeText(Login.this, "Email Not Verified", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(Login.this, Login.class));
                                        finish();
                                    }

                                } else {
                                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
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
    @Override
    public void onBackPressed() {

        System.exit(0);
    }
}
