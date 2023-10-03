package com.example.ui1.Introduction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ui1.R;
import com.example.ui1.UI.Home;

import java.util.Timer;
import java.util.TimerTask;

public class HighRiskInstructions extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_risk_instructions);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(HighRiskInstructions.this, Home.class);
                startActivity(intent);
                finish();
            }
        },60000*3);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighRiskInstructions.this,Home.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}