package com.example.ananya.careapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String mm=user.getDisplayName().toString();
            ((UserCare) getApplicationContext()).setName(mm);
            ((UserCare) getApplicationContext()).setUID(mm);
            startActivity(new Intent(Login.this,Adminhome.class));
        }
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
                                        startActivity(new Intent(Login.this, Adminhome.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(Login.this, "Email Not Verified", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(Login.this, Login.class));
                                    }

                                } else {
                                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

}
