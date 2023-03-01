package com.example.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Inventory extends AppCompatActivity {
TextView resource;
TextView Usedresource;
TextView product;
TextView Revenue;
Button back;

    int Total = 0;
    int sum = 0;
    int TotalUsed = 0;
    long kids = 0;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Resources");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Products");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        resource = findViewById(R.id.AvailabelResourceValue);
        Usedresource = findViewById(R.id.UsedResourceValue);
        product = findViewById(R.id.productsValue);
        Revenue = findViewById(R.id.NoIDValue);
        back = findViewById(R.id.btnBack);
        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inventory.this,MainActivity.class));
            }
        });
        //
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
                    Toast.makeText(Inventory.this, rt.getMessage().trim(), Toast.LENGTH_LONG).show();
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
                        TotalUsed = TotalUsed + Integer.parseInt(dataSnapshot.child("prodHowMuchUsed").getValue().toString());
                    }
                    //available resources
                    int remaining = sum - TotalUsed;
                    //setting available resource
                    Usedresource.setText(""+TotalUsed + " M");
                    //setting available resource
                    resource.setText(remaining + " M");
                    //items count
                    product.setText("" + kids);
                    int Price =remaining*4;
                    //setting estimated value
                    Revenue.setText("+R "+Price);
                } catch (Exception rt) {
                    Toast.makeText(Inventory.this, rt.getMessage().trim(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}