package com.example.ananya.careapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signin, signup;
    EditText nametext,emailtext,passwordtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        nametext=findViewById(R.id.namereg);
        emailtext=findViewById(R.id.e_mailreg);
        passwordtext=findViewById(R.id.passwordreg);
        signup=findViewById(R.id.buttonreg);
        signin=findViewById(R.id.reglogin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSignUp();
            }
        });

    }

    protected void UserSignUp(){
        final String name=nametext.getText().toString();
        final String email=emailtext.getText().toString();
        final String password=passwordtext.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // successfully account created
                            // now the AuthStateListener runs the onAuthStateChanged callback
                            sendVerificationEmail();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Register.this,"Account Created Verify Your Email",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }

                        // ...
                    }
                });
    }
    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this,"Verification Email Sent",Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(Register.this, Login.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Register.this,"Verification Email Not Sent",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
