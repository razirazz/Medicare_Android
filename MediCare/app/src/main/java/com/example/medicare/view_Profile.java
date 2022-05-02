package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_Profile extends AppCompatActivity {

    ImageView pat_img;
    TextView s_name, s_address, s_contact, s_dob, s_gender, s_email, s_place, s_post, s_district, s_pin;
    Button update_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);

        pat_img = findViewById(R.id.view_profile_img);
        s_name = (TextView) findViewById(R.id.view_s_name);
        s_address = (TextView) findViewById(R.id.view_s_address);
        s_contact = (TextView) findViewById(R.id.view_s_contact);
        s_dob = (TextView) findViewById(R.id.view_s_dob);
        s_gender = findViewById(R.id.view_s_gender);
        s_email = (TextView) findViewById(R.id.view_s_mail);
        s_place = (TextView) findViewById(R.id.view_s_place);
        s_post = (TextView) findViewById(R.id.view_s_post);
        s_district = (TextView) findViewById(R.id.view_s_district);
        s_pin = (TextView) findViewById(R.id.view_s_pin);
        update_pro = findViewById(R.id.view_profile_btn);

        update_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), update_Profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_profile";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences (getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();

                                String name = jsonObj.getString("name");
                                s_name.setTextColor(Color.WHITE);
                                s_name.setText(name);

                                String address = jsonObj.getString("address");
                                s_address.setTextColor(Color.WHITE);
                                s_address.setText(address);

                                String contact = jsonObj.getString("contact");
                                s_contact.setTextColor(Color.WHITE);
                                s_contact.setText(contact);

                                String dob = jsonObj.getString("birth_date");
                                s_dob.setTextColor(Color.WHITE);
                                s_dob.setText(dob);

                                String gender = jsonObj.getString("gender");
                                s_gender.setTextColor(Color.WHITE);
                                s_gender.setText(gender);

                                String email = jsonObj.getString("email");
                                s_email.setTextColor(Color.WHITE);
                                s_email.setText(email);

                                String place = jsonObj.getString("place");
                                s_place.setTextColor(Color.WHITE);
                                s_place.setText(place);

                                String post = jsonObj.getString("post");
                                s_post.setTextColor(Color.WHITE);
                                s_post.setText(post);

                                String district = jsonObj.getString("district");
                                s_district.setTextColor(Color.WHITE);
                                s_district.setText(district);

                                String pin = jsonObj.getString("pin");
                                s_pin.setTextColor(Color.WHITE);
                                s_pin.setText(pin);

                                String pic = jsonObj.getString("photo");
                                String url="http://" + hu + ":5000" + pic;
                                Picasso.with(getApplicationContext()).load(url).into(pat_img);

                                Toast.makeText(view_Profile.this, "-----------------"+pic, Toast.LENGTH_SHORT).show();


                                ed.putString("name", name);
                                ed.putString("address", address);
                                ed.putString("contact", contact);
                                ed.putString("birth_date", dob);
                                ed.putString("email", email);
                                ed.putString("place", place);
                                ed.putString("post", post);
                                ed.putString("district", district);
                                ed.putString("pin", pin);
                                ed.putString("post", post);
                                ed.putString("pic", pic);

                                ed.putString("gender", gender);
                                ed.commit();

                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();


                params.put("lid", sh.getString("lid", ""));


                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);


    }
}


