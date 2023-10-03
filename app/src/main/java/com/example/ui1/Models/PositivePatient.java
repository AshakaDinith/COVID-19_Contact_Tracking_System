package com.example.ui1.Models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PositivePatient {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List positiveList;

    public PositivePatient(List positiveMobileNumbers) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        positiveList =  new ArrayList();
        getPositiveMacAddress(positiveMobileNumbers);
    }


    public void getPositiveMacAddress(List positiveMobileNumbers){

        for(Object number:positiveMobileNumbers){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot item_snapshot:snapshot.getChildren()) {
                        String mobileNumber = number.toString();
                        if (item_snapshot.child("phone").getValue().toString().equals(mobileNumber)){
                            positiveList.add(item_snapshot.child("blueMac").getValue().toString().toLowerCase(Locale.ROOT));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public List getPositiveList() {
        return positiveList;
    }
}
