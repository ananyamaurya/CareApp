package com.example.ananya.careapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        },3000);
}
}
