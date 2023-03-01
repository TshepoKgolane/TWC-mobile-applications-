package com.example.work;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    Button back;
    TextView name;
    TextView surname;
    TextView role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        back = findViewById(R.id.btnBack);
        name = findViewById(R.id.Name);
        surname = findViewById(R.id.Surname);
        role = findViewById(R.id.Role);

        name.setText(Users.Loggedname);
        surname.setText(Users.Loggedsurname);
        role.setText(Users.LoggedRole);
        //Going back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });
    }
}