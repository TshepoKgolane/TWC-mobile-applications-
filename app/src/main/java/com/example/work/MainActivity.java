package com.example.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting Texts

        //drawer xml link
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                    {
                       startActivity(new Intent(MainActivity.this,Inventory.class));
                       break;
                    }
                    case R.id.Resources:
                    {
                        startActivity(new Intent(MainActivity.this,Resources.class));
                        break;
                    }
                    case R.id.products:
                    {
                        startActivity(new Intent(MainActivity.this,Products.class));
                        break;
                    }

                    case R.id.myProfile :
                    {
                        startActivity(new Intent(MainActivity.this,Profile.class));
                        break;
                    }
                    case R.id.logOut :
                    {
                        startActivity(new Intent(MainActivity.this,Login.class));
                        Toast.makeText(MainActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        Toast.makeText(MainActivity.this, "Please make a selection", Toast.LENGTH_SHORT).show();;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}