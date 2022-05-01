package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class patient_home extends AppCompatActivity implements View.OnClickListener {

    Button view_profile, change_pass, view_doctor, view_medicine, view_discount, view_near_hospital, view_near_pharmacy, view_doc_book, view_med_book, complaint, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home);

        view_profile = (Button) findViewById(R.id.home_view_profile_btn);
        change_pass = (Button) findViewById(R.id.home_change_pass_btn);
        view_doctor = (Button) findViewById(R.id.home_view_doctor_btn);
//        book_doctor = (Button) findViewById(R.id.home_book_doctors);
        view_medicine = (Button) findViewById(R.id.home_view_medicine_btn);
        view_discount = (Button) findViewById(R.id.home_view_discount_btn);
        view_near_hospital = findViewById(R.id.home_view_near_hos_btn);
        view_near_pharmacy = (Button) findViewById(R.id.home_view_near_pharm_btn);
        view_doc_book = findViewById(R.id.home_view_doc_booking);
        view_med_book = findViewById(R.id.home_view_med_booking);
        complaint = findViewById(R.id.home_view_complaint_btn);
        logout = (Button) findViewById(R.id.home_log_out_btn);

        view_profile.setOnClickListener(this);
        change_pass.setOnClickListener(this);
        view_doctor.setOnClickListener(this);
//        book_doctor.setOnClickListener(this);
        view_medicine.setOnClickListener(this);
        view_discount.setOnClickListener(this);
        view_near_hospital.setOnClickListener(this);
        view_near_pharmacy.setOnClickListener(this);
        view_doc_book.setOnClickListener(this);
        view_med_book.setOnClickListener(this);
        complaint.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == view_profile) {
            Intent i = new Intent(getApplicationContext(),view_Profile.class);
            startActivity(i);
        }
        else if(view == change_pass) {
            Intent i = new Intent(getApplicationContext(), change_password.class);
            startActivity(i);
        }
        else if(view == view_doctor) {
            Intent i = new Intent(getApplicationContext(), view_Doctor.class);
            startActivity(i);
        }

        else if(view == view_medicine) {
            Intent i = new Intent(getApplicationContext(), view_Medicine.class);
            startActivity(i);
        }
        else if(view == view_discount) {
            Intent i = new Intent(getApplicationContext(), view_Discount.class);
            startActivity(i);
        }
        else if(view == view_near_hospital) {
            Intent i = new Intent(getApplicationContext(), view_near_Hospital.class);
            startActivity(i);
        }
        else if(view == view_near_pharmacy) {
            Intent i = new Intent(getApplicationContext(), view_near_Pharmacy.class);
            startActivity(i);
        }
        else if(view == view_doc_book) {
            Intent i = new Intent(getApplicationContext(), view_DoctorBooking.class);
            startActivity(i);
        }
        else if(view == view_med_book) {
            Intent i = new Intent(getApplicationContext(), view_MedicineBooking.class);
            startActivity(i);
        }
        else if(view == complaint) {
            Intent i = new Intent(getApplicationContext(), view_complaint.class);
            startActivity(i);
        }
        else if(view == logout) {
            Intent i = new Intent(getApplicationContext(), login_page.class);
            startActivity(i);
        }
    }
}