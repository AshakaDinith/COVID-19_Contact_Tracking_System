package com.example.ui1.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;


import com.example.ui1.R;
import com.example.ui1.Registration.Login;
import com.example.ui1.Registration.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private Button btnHome,btnLogout;
    private TextView status;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        status = findViewById(R.id.tvStatusValue);
        status.setText("" + Home.health);

        btnLogout = findViewById(R.id.btnLogout);


        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        //final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView fullNameTextView = (TextView) findViewById(R.id.tvFullName);
        final TextView emailTextView = (TextView) findViewById(R.id.tvEmail);
        final TextView addressTextView = (TextView) findViewById(R.id.tvAdd);
        final TextView phoneTextView =(TextView) findViewById(R.id.tvPhone);
        final TextView blueMACTextView = (TextView) findViewById(R.id.tvMACAdd);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {

                    String fullName = userProfile.fullName;
                    String address = userProfile.address;
                    String phone = userProfile.phone;
                    String email = userProfile.email;
                    String blueMAC = userProfile.blueMac;

                    //greetingTextView.setText("Welcome, "+ fullname+ "!");
                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    addressTextView.setText(address);
                    phoneTextView.setText(phone);
                    blueMACTextView.setText(blueMAC);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(Profile.this);
                builder
                        .setTitle("CoviTrack App")
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(Profile.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", null).show();

            }
        });

    }

    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Profile.this,Home.class);
        startActivity(intent);

    }

}