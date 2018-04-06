package com.example.ananya.careapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class DoctorHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String usersname=user.getDisplayName().toString();
        String usersemail=user.getEmail().toString();

        TextView nameText= headerView.findViewById(R.id.docdrawusername);
        TextView emailText= headerView.findViewById(R.id.docdraweremail);
        ImageView profile= headerView.findViewById(R.id.docprofilePic);
        TextView occu=headerView.findViewById(R.id.docdraweroccupation);
        String ss=((MyApp) getApplication()).getOccupation();
        occu.setText(ss);
        profile.setImageURI(user.getPhotoUrl());
        Uri url = user.getPhotoUrl();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),url);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Profile Pic loaded",Toast.LENGTH_SHORT).show();
        }
        profile.setImageBitmap(bitmap);
        nameText.setText(usersname);
        emailText.setText(usersemail);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        DocHome addhome=new DocHome();
        ft.replace(R.id.dochomeframe,addhome,"Home");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments > 1) {
                super.onBackPressed();
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();;
            mAuth.signOut();
            startActivity( new Intent(getApplicationContext(),Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dochome) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            DocHome addhome=new DocHome();
            ft.replace(R.id.dochomeframe,addhome,"Home");
            ft.commit();
            // Handle the camera action
        } else if (id == R.id.docqueue) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            PatientQueues patientQueues=new PatientQueues();
            ft.replace(R.id.dochomeframe,patientQueues,"Home");
            ft.commit();

        }else if (id == R.id.docdetails) {

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            PatientDetails patientDetails=new PatientDetails();
            ft.replace(R.id.dochomeframe,patientDetails,"Home");
            ft.commit();
        } else if (id == R.id.docvisit) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            DoctorVisit doctorVisit=new DoctorVisit();
            ft.replace(R.id.dochomeframe,doctorVisit,"Home");
            ft.commit();

        } else if (id == R.id.doccondition) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            UpdateConditions doctorVisit=new UpdateConditions();
            ft.replace(R.id.dochomeframe,doctorVisit,"Home");
            ft.commit();

        } else if (id == R.id.doctoraboutus) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Aboutus aboutus=new Aboutus();
            ft.replace(R.id.dochomeframe,aboutus,"Home");
            ft.commit();

        } else if (id == R.id.doctorcontactus) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Contactus contactus=new Contactus();
            ft.replace(R.id.dochomeframe,contactus,"Home");
            ft.commit();
        }else if (id == R.id.doctorHomeexit) {
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}