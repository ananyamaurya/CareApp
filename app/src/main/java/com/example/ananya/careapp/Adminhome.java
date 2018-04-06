package com.example.ananya.careapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class Adminhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String occupation;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
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

        TextView nameText= headerView.findViewById(R.id.drawusername);
        TextView emailText= headerView.findViewById(R.id.draweremail);
        ImageView profile= headerView.findViewById(R.id.profilePic);
        TextView occu=headerView.findViewById(R.id.draweroccupation);
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
        Addhome h1=new Addhome();
        ft.replace(R.id.homeframe,h1,"Home");
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
        getMenuInflater().inflate(R.menu.adminhome, menu);
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

        if (id == R.id.hospital) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            CareHospital h1=new CareHospital();
            ft.replace(R.id.homeframe,h1,"Home");

            ft.commit();
            // Handle the camera action
        }else if (id == R.id.home) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Addhome addhome=new Addhome();
            ft.replace(R.id.homeframe,addhome,"Home");

            ft.commit();

        } else if (id == R.id.doctor) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            CareDoctor careDoctor=new CareDoctor();
            ft.replace(R.id.homeframe, careDoctor, "Home");
            ft.commit();

        } else if (id == R.id.attendant) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            CareAttendant attendant=new CareAttendant();
            ft.replace(R.id.homeframe,attendant,"Home");
            ft.commit();
        } else if (id == R.id.carePatients) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            CarePatients carePatients =new CarePatients();
            ft.replace(R.id.homeframe, carePatients,"Home");
            ft.commit();
        } else if (id == R.id.aboutus) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
           Aboutus aboutus=new Aboutus();
            ft.replace(R.id.homeframe,aboutus,"Home");
            ft.commit();
        } else if (id == R.id.contactus) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Contactus contactus=new Contactus();
            ft.replace(R.id.homeframe,contactus,"Home");
            ft.commit();
        }
        else if (id == R.id.Homeexit) {
            System.exit(0);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
