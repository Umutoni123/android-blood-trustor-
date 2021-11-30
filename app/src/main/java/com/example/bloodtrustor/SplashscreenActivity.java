package com.example.bloodtrustor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView tittle, slogan;

   Animation topAnimation,bottomAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        tittle = findViewById(R.id.title);
        slogan=findViewById(R.id.slogan);

        topAnimation= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation=AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        logo.setAnimation(topAnimation);
        tittle.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

        int SPLASH_SCREEN=4300;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent  intent=new Intent(SplashscreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}