package com.example.ananya.careapp;

import android.os.Bundle;
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

public class Adminhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        } else {
            super.onBackPressed();
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
            return true;
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
            Hospital h1=new Hospital();
            ft.replace(R.id.homeframe,h1,"Home");

            ft.commit();
            // Handle the camera action
        } else if (id == R.id.doctor) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Doctor d1=new Doctor();
            ft.replace(R.id.homeframe,d1,"Home");

            ft.commit();

        } else if (id == R.id.attendant) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Attendant attendant=new Attendant();
            ft.replace(R.id.homeframe,attendant,"Home");

            ft.commit();

        } else if (id == R.id.patient) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Patient patient=new Patient();
            ft.replace(R.id.homeframe,patient,"Home");

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
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Hospital h1=new Hospital();
            ft.replace(R.id.homeframe,h1,"Home");

            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
