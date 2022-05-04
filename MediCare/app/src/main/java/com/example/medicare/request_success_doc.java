package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class request_success_doc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_success_doc);

        View animationView = findViewById(R.id.animationView);

        Button done = findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), view_DoctorBooking.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), view_DoctorBooking.class);
        startActivity(i);
    }
}