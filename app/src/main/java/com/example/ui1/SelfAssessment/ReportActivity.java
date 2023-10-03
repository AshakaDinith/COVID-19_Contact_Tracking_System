package com.example.ui1.SelfAssessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.ui1.R;
import com.example.ui1.UI.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SelfDataAdapter selfDataAdapter;
    ArrayList<SelfData> list;

    private FirebaseAuth mAuth;
    private DatabaseReference selfAssData;
    private String userId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        selfAssData = FirebaseDatabase.getInstance().getReference("SelfData");

        recyclerView = findViewById(R.id.selfStatusList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        selfDataAdapter = new SelfDataAdapter(this,list);
        recyclerView.setAdapter(selfDataAdapter);

        selfAssData.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    SelfData selfData= dataSnapshot.getValue(SelfData.class);
                    list.add(selfData);
                }
                selfDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReportActivity.this, Home.class);
        startActivity(intent);
        finish();

    }
}