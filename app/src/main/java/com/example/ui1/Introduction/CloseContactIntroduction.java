package com.example.ui1.Introduction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ui1.R;
import com.example.ui1.UI.Home;

import java.util.Timer;
import java.util.TimerTask;


public class CloseContactIntroduction extends AppCompatActivity {
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_contact_introduction);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(CloseContactIntroduction.this, Home.class);
                startActivity(intent);
                finish();
            }
        },60000*3);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CloseContactIntroduction.this,Home.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}