package com.example.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicare.databinding.PatientMainHomeBinding;

public class patient_main_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private PatientMainHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = PatientMainHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPatientMainHome.toolbar);
        binding.appBarPatientMainHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent ij=new Intent(getApplicationContext(), patient_main_home.class);
            startActivity(ij);
        } else if (id == R.id.nav_profile) {
            Intent ij=new Intent(getApplicationContext(), view_Profile.class);
            startActivity(ij);
        } else if (id == R.id.nav_change_pass) {
            Intent ij=new Intent(getApplicationContext(), change_password.class);
            startActivity(ij);
        }else if (id == R.id.nav_doctor) {
            Intent ij=new Intent(getApplicationContext(), view_Doctor.class);
            startActivity(ij);
        }else if (id == R.id.nav_book_doctor) {
            Intent ij=new Intent(getApplicationContext(), view_DoctorBooking.class);
            startActivity(ij);
        }else if (id == R.id.nav_medicine) {
            Intent ij=new Intent(getApplicationContext(), view_Medicine.class);
            startActivity(ij);
        }else if (id == R.id.nav_book_medicine) {
            Intent ij=new Intent(getApplicationContext(), view_MedicineBooking.class);
            startActivity(ij);
        }else if (id == R.id.nav_hospital) {
            Intent ij=new Intent(getApplicationContext(), view_near_Hospital.class);
            startActivity(ij);
        }else if (id == R.id.nav_pharmacy) {
            Intent ij=new Intent(getApplicationContext(), view_near_Pharmacy.class);
            startActivity(ij);
        }else if (id == R.id.nav_log_out) {
            Intent ij=new Intent(getApplicationContext(),  login_page.class);
            startActivity(ij);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}