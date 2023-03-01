package com.example.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Products extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Resources");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Products");
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Products");
    int Total = 0;
    int sum = 0;
    int TotalUsed = 0;
    long kids = 0;
    Button btnAdd;
    Button btnBack;
    EditText measured;
    TextView availproducts;
    TextView avail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        avail = findViewById(R.id.AvailableValue);
        measured = findViewById(R.id.txtMeasurement);
        availproducts = findViewById(R.id.AvailableProducts);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        int NumFrmDB = Integer.parseInt(dataSnapshot.child("measurement").getValue().toString());
                        Total = Total + NumFrmDB;
                    }
                    sum = sum + Total;

                } catch (Exception rt) {
                    Toast.makeText(Products.this, rt.getMessage().trim(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        dataSnapshot.getChildrenCount();
                        kids = snapshot.getChildrenCount();
                        TotalUsed =TotalUsed+ Integer.parseInt(dataSnapshot.child("prodHowMuchUsed").getValue().toString());
                    }
                    //available resources
                    sum = sum - TotalUsed;
                    //setting available resource
                    avail.setText(sum + " M");
                    //items count
                    availproducts.setText("" + kids);
                } catch (Exception rt) {
                    Toast.makeText(Products.this, rt.getMessage().trim(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Products.this, MainActivity.class));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (sum == 0) {

                    } else {
                        if (Integer.parseInt(measured.getText().toString()) > sum) {
                            measured.setError("Value cannot be more than available amount");
                        } else {
                            //how much material was used

                            if (measured.getText().length() != 0) {
                                Users users = new Users(Integer.parseInt(measured.getText().toString()));
                                root.push().setValue(users);
                                startActivity(new Intent(Products.this, Products.class));
                                Toast.makeText(Products.this, "Captured Product", Toast.LENGTH_SHORT).show();
                            } else {
                                measured.setError("Insert an Amount");
                            }
                        }
                    }

                } catch (NumberFormatException err) {
                    measured.setError("Enter an integer value");
                    Toast.makeText(Products.this, "Please enter a numeric value", Toast.LENGTH_LONG).show();
                } catch (Exception err) {
                    Toast.makeText(Products.this, "" + err.getMessage().trim(), Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}