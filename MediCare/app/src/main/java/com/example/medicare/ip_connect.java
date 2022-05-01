package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ip_connect extends AppCompatActivity implements View.OnClickListener {

    EditText ed_ip;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_connect);
        ed_ip = (EditText)findViewById(R.id.ip_address);
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        final String ipadrs = ed_ip.getText().toString();
        if(ipadrs.length()==0)
        {

            ed_ip.setError("Field cannot be empty");
        }
        else {


            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", ipadrs);
            ed.commit();
            Toast.makeText(getApplicationContext(), ipadrs, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), login_page.class);
            startActivity(i);
        }
    }
}

