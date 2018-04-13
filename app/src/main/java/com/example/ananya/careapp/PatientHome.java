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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PatientHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Uri.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.pat_nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String usersname=user.getDisplayName().toString();
        String usersemail=user.getEmail().toString();

        TextView nameText= headerView.findViewById(R.id.patdrawusername);
        TextView emailText= headerView.findViewById(R.id.patdraweremail);
        ImageView profile= headerView.findViewById(R.id.patprofilePic);
        TextView occu=headerView.findViewById(R.id.patdraweroccupation);
        String ss=((MyApp) getApplication()).getOccupation();
        occu.setText(ss);
        Uri uri = user.getPhotoUrl();
        Picasso.with(getApplicationContext()).load(uri).into(profile);
        nameText.setText(usersname);
        emailText.setText(usersemail);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ProfilePat addhome=new ProfilePat();
                ft.replace(R.id.patienthomeframe,addhome,"Home");
                ft.commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        PatHome findHospital=new PatHome();
        ft.replace(R.id.patienthomeframe,findHospital,"Home");
        ft.addToBackStack("PatHome");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_home, menu);
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

        if (id == R.id.patappointment) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            PatientAppointment findHospital=new PatientAppointment();
            ft.replace(R.id.patienthomeframe,findHospital,"Home");
            ft.addToBackStack("PatientAppointment");
            ft.commit();
            // Handle the camera action
        } else if (id == R.id.patDoctor) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            FindDoctor findHospital=new FindDoctor();
            ft.replace(R.id.patienthomeframe,findHospital,"Home");
            ft.addToBackStack("Find Doctor");
            ft.commit();
        } else if (id == R.id.patHospital) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            FindHospital findHospital=new FindHospital();
            ft.replace(R.id.patienthomeframe,findHospital,"Home");
            ft.addToBackStack("FindHospital");
            ft.commit();

        } else if (id == R.id.pataboutus) {

        } else if (id == R.id.patcontactus) {

        } else if (id == R.id.patHomeexit) {

        }else if (id == R.id.patmedicalHistory) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            PatientMedicalHistory findHospital=new PatientMedicalHistory();
            ft.replace(R.id.patienthomeframe,findHospital,"Home");
            ft.addToBackStack("FindHospital");
            ft.commit();

        } else if (id == R.id.pathome) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            PatHome findHospital=new PatHome();
            ft.replace(R.id.patienthomeframe,findHospital,"Home");
            ft.addToBackStack("PatHome");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
