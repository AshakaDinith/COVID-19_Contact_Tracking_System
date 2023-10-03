package com.example.ui1.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ui1.R;

public class SplashScreenLogo extends AppCompatActivity {

    private static int SPLASH = 3000;
    Animation animation;
    private ImageView ivLogo;
    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_logo);

        animation = AnimationUtils.loadAnimation(this,R.anim.animation);

        ivLogo = findViewById(R.id.ivLogo);
        //appName= findViewById(R.id.tvWelcome);

        ivLogo.setAnimation(animation);
        //appName.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenLogo.this, Logo.class);
                startActivity(intent);
                finish();
            }

        },SPLASH );
    }
}