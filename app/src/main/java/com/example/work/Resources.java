package com.example.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.Date;

public class Resources extends AppCompatActivity {
    EditText matValue;
    Button btnAdd;
    Button btnBack;
    TextView MeasureValue;
    int sum = 0;
    int Total = 0;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Resources");
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Resources");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        btnAdd = (Button) findViewById(R.id.btnMaterial);
        btnBack = (Button) findViewById(R.id.btnBack);
        matValue = (EditText) findViewById(R.id.txtMeasurement);
        MeasureValue = (TextView) findViewById(R.id.AvailabelResourceValue);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        int NumFrmDB = Integer.parseInt(dataSnapshot.child("measurement").getValue().toString());
                        Total = Total + NumFrmDB;
                    }
                    sum = sum + Total;
                    if (sum == 0) {
                        MeasureValue.setTextColor(Color.RED);
                    }
                    MeasureValue.setText("" + sum);
                } catch (Exception rt) {
                    Toast.makeText(Resources.this, rt.getMessage().trim(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String material = "Polypropylene";

                try {
                    if (matValue.getText().length() != 0) {
                        startActivity(new Intent(Resources.this, Resources.class));
                        Users users = new Users(material, Integer.parseInt(matValue.getText().toString()));
                        root.push().setValue(users);
                        Toast.makeText(Resources.this, "Added Resource to Database", Toast.LENGTH_SHORT).show();
                    } else {
                        matValue.setError("Insert an Amount");
                    }
                } catch (NumberFormatException err) {
                    matValue.setError("Enter an integer value");
                    Toast.makeText(Resources.this, "Please enter a numeric value", Toast.LENGTH_LONG).show();
                } catch (Exception err) {
                    Toast.makeText(Resources.this, "" + err.getMessage().trim(), Toast.LENGTH_LONG).show();
                }


            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Resources.this, MainActivity.class));
            }
        });
    }

}