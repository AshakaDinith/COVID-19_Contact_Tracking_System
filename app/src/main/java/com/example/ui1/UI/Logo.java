package com.example.ui1.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ui1.Introduction.CloseContactIntroduction;
import com.example.ui1.Introduction.HighRiskInstructions;
import com.example.ui1.Introduction.ModerateRiskInstructions;
import com.example.ui1.Introduction.PositiveInstructions;
import com.example.ui1.R;
import com.example.ui1.SelfAssessment.SelfAssessment;
import com.google.firebase.auth.FirebaseAuth;

public class Logo extends AppCompatActivity {
    private Button btnLogo;
    private FirebaseAuth mAuth;
    public static String health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        SharedPreferences sharedPreferences = getSharedPreferences(SelfAssessment.SHARED_PREFS, MODE_PRIVATE);
        health = sharedPreferences.getString(SelfAssessment.TEXT, "");

        btnLogo = (Button) findViewById(R.id.btnNext);
        mAuth = FirebaseAuth.getInstance();
        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrivacy();
            }
        });
    }

    public void openPrivacy(){
        Intent intent = new Intent(this, Privacy.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){

            if(health.equals("POSITIVE")){

                Intent intent = new Intent(Logo.this, PositiveInstructions.class);
                startActivity(intent);
                finish();
                //Toast.makeText(this, "POSITIVE POSITIVE", Toast.LENGTH_SHORT).show();
            }
            else if (health.equals("HIGH RISK")){
                Intent intent = new Intent(Logo.this, HighRiskInstructions.class);
                startActivity(intent);
                finish();
                //Toast.makeText(this, "High Risk", Toast.LENGTH_SHORT).show();
            }
            else if(health.equals("MODERATE RISK")){
                Intent intent = new Intent(Logo.this, ModerateRiskInstructions.class);
                startActivity(intent);
                finish();
                //Toast.makeText(this, "MODERATE RISK", Toast.LENGTH_SHORT).show();
            }
            else if(health.equals("CLOSE CONTACT")){
                Intent intent = new Intent(Logo.this, CloseContactIntroduction.class);
                startActivity(intent);
                finish();
                //Toast.makeText(this, "CLOSE CONTACT", Toast.LENGTH_SHORT).show();
            }
            else
            {
//                Toast.makeText(this,"Already Logged In!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Logo.this, Home.class));
                finish();
            }
        }
        else{
//            Toast.makeText(Logo.this,"You can login now!",Toast.LENGTH_SHORT).show();
        }
    }
}