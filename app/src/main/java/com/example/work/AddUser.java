package com.example.work;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUser extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    Button btnRegister;
    String[] roles = { "Machine worker", "Material worker","Manager", "Administrator"};
    //text fields
    EditText name;
    EditText surname;
    EditText RUsername;
    EditText RPassword;
    //Selected Spinner item
    String Selected;
    //Database Reference
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Spinner spin = (Spinner) findViewById(R.id.spinner2);
        spin.setOnItemSelectedListener(this);
        //initialise register button
        btnRegister = findViewById(R.id.btnRegister);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,roles);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    name=(EditText) findViewById(R.id.RegName);
                    surname=(EditText) findViewById(R.id.RegSurname);
                    RUsername=(EditText) findViewById(R.id.RegUsername);
                    RPassword=(EditText) findViewById(R.id.Regpassword);

                    if (name.length()==0){
                        name.setError("The name is Mandatory");name.requestFocus();
                    }else  if (surname.length()==0){
                        surname.setError("Surname is Mandatory");surname.requestFocus();
                    }else  if (RUsername.length()==0){
                        RUsername.setError("Username is Mandatory");RUsername.requestFocus();
                    }else  if (RPassword.length()==0){
                        RPassword.setError("Your name is Mandatory");RPassword.requestFocus();
                    }else{
                        // Add to database
                        Users users = new Users(name.getText().toString(),surname.getText().toString(),RUsername.getText().toString(),Selected,RPassword.getText().toString());
                        root.push().setValue(users);
                        Snackbar.make(findViewById(android.R.id.content), "Added", Snackbar.LENGTH_LONG).show();

                    }
                }catch (Exception rr){

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Selected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}