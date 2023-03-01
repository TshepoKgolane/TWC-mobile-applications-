package com.example.work;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login  extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

// ...
DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
    Button login;
    EditText LUsername;
    EditText LPassword;
    Spinner spnner;
    String[] country = { "Machine worker", "Material worker","Manager", "Administrator"};
    //
    private String username;
    private String password;
    String selectedRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.btnLogin);
        LUsername = findViewById(R.id.Loginusername);
        LPassword = findViewById(R.id.Loginpassword);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnner = (Spinner) findViewById(R.id.spinner);
        spnner.setOnItemSelectedListener(this);
        //login button clicke
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginHandling();
            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnner.setAdapter(aa);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedRole = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void loginHandling() {
        try {

            if (LUsername.length() == 0 || LPassword.length() == 0) {
                LUsername.setError("Requirred");
                LPassword.setError("Requirred");
                LPassword.requestFocus();
                LUsername.requestFocus();

            } else {
                try {

                    if (selectedRole.equals("Machine worker")){
                        Retrieve(LUsername.getText().toString(),LPassword.getText().toString(),"Machine worker",MainActivity.class);
                    } else if (selectedRole.equals("Material worker")){
                        Retrieve(LUsername.getText().toString(),LPassword.getText().toString(),"Material worker",MainActivity.class);
                    }else if (selectedRole.equals("Manager")){
                        Retrieve(LUsername.getText().toString(),LPassword.getText().toString(),"Manager",MainActivity.class);
                    }else if (selectedRole.equals("Administrator")){
                        Retrieve(LUsername.getText().toString(),LPassword.getText().toString(),"Administrator",AdminActions.class);
                        //startActivity(new Intent(Login.this, AdminActions.class));
                    }else{
                        Snackbar.make(findViewById(android.R.id.content), "Please select Role", Snackbar.LENGTH_LONG).show();
                    }
                }catch(Exception rt){
                    Toast.makeText(Login.this, rt.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                username = LUsername.getText().toString();
                password = LPassword.getText().toString();


            }
        } catch (Exception err) {
            Toast.makeText(this, "Error\n" + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void Retrieve(String user,String Pass, String role, Class Where2){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        String Username = dataSnapshot.child("username").getValue().toString();
                        String Password = dataSnapshot.child("password").getValue().toString();
                        String Role = dataSnapshot.child("role").getValue().toString();

                        if (user.equals(Username) && Pass.equals(Password) && role.equals(Role)){
                            //Login Successful
                            Toast.makeText(Login.this, "Successfully Logged IN", Toast.LENGTH_SHORT).show();
                            //Saving logged user credentials
                            Users.LoggedRole = Role;
                            Users.Loggedsurname = dataSnapshot.child("surname").getValue().toString();
                            Users.Loggedname = dataSnapshot.child("name").getValue().toString();
                            startActivity(new Intent(Login.this, Where2));
                        }

                    }
                    Snackbar.make(findViewById(android.R.id.content), "Invalid credentials for role", Snackbar.LENGTH_LONG).show();

                }catch(Exception rt){
                    Toast.makeText(Login.this, rt.getMessage().trim(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}