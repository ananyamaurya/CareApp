package com.example.ananya.careapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signin, signup;
    EditText nametext,emailtext,passwordtext;
    Spinner occSpinner;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref= database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        occSpinner = (Spinner)findViewById(R.id.Occupation);
        ArrayAdapter<String> spinnerCountOccuArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.occupatiouser));
        occSpinner.setAdapter(spinnerCountOccuArrayAdapter);
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
        final String occ= occSpinner.getSelectedItem().toString();
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
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/caremed-b9a9b.appspot.com/o/101-doctor-1.png?alt=media&token=b387840b-9576-4468-b1fe-cac7f102d452"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                String uid= user.getUid().toString();
                                                CaremedUser m= new CaremedUser(occ);
                                                ref.keepSynced(true);
                                                ref.child(uid).setValue(m);
                                                ref.child(uid).keepSynced(true);
                                                Toast.makeText(getApplicationContext(),"Account Creation Successful Verify Your e-mail",Toast.LENGTH_SHORT).show();
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
