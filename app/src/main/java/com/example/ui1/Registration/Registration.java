package com.example.ui1.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui1.Introduction.FindBlueMACAddress;
import com.example.ui1.R;
import com.example.ui1.SelfAssessment.SelfAssessmentHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistration;
    private Button btnFind;

    private TextInputEditText etFullName, etAdd, etEmail, etPhone, etPassword, etMAC;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private TextView tvLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        etFullName =  findViewById(R.id.etFullName);
        etAdd =  findViewById(R.id.etAdd);
        etEmail = findViewById(R.id.etEmail);
        etPhone =  findViewById(R.id.etPhone);
        etPassword =  findViewById(R.id.etPassword);
        etMAC = findViewById(R.id.etMACAdd);

        tvLogIn = findViewById(R.id.tvLogIn);
        tvLogIn.setOnClickListener(this);

        btnRegistration =  findViewById(R.id.btnRegister);
        btnRegistration.setOnClickListener(this);

        btnFind = (Button) findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindMac();
            }
        });

        progressBar = findViewById(R.id.progressBar);



    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvLogIn:
                Intent intent = new Intent(Registration.this,Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    public void openFindMac(){
        Intent intent = new Intent(this, FindBlueMACAddress.class);
        startActivity(intent);
    }



    public void openSelfAss(){
        Intent intent = new Intent(this, SelfAssessmentHome.class);
        startActivity(intent);
    }

    private void registerUser() {

        String fullName = etFullName.getText().toString().trim();
        String address = etAdd.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String bluMAC = etMAC.getText().toString().trim();

        String regex = "^([0-9A-Fa-f]{2}[:-])"
                + "{5}([0-9A-Fa-f]{2})|"
                + "([0-9a-fA-F]{4}\\."
                + "[0-9a-fA-F]{4}\\."
                + "[0-9a-fA-F]{4})$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(bluMAC);

        String regex1 = "^[0-9]{10}";
        Pattern p1 = Pattern.compile(regex1);
        Matcher m1 = p1.matcher(phone);

        if (fullName.isEmpty()){
            etFullName.setError("Field can't be empty");
            etFullName.requestFocus();
            return;
        }
        if (address.isEmpty()){
            etAdd.setError("Field can't be empty");
            etAdd.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            etPhone.setError("Field can't be empty!");
            etPhone.requestFocus();
            return;
        }
        if (!m1.matches()){
            etPhone.setError("Enter valid phone number");
            etPhone.requestFocus();
            return;
        }
        if (email.isEmpty()){
            etEmail.setError("Field can't be empty");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter Valid Email");
            etEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPassword.setError("Field can't be empty");
            etPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }
        if (bluMAC.isEmpty()){
            etMAC.setError("Field can't be empty");
            etMAC.requestFocus();
            return;
        }
        if (!m.matches()){
            etMAC.setError("Enter Valid Bluetooth MAC Address");
            etMAC.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullName, address, phone, email, bluMAC);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                openSelfAss();
                                                Toast.makeText(Registration.this, "User has been Registered", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }else {
                                                Toast.makeText(Registration.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(Registration.this, "Failed to Register! ", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}