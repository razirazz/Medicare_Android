package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class view_Doctor_Profile extends AppCompatActivity {
    ImageView doc_Image;
    TextView doc_Name, hos_Name, department, qualification, experience, fees, contact, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_profile);

        doc_Image = findViewById(R.id.doctor_profile_img);
        doc_Name = findViewById(R.id.doc_profile_name);
        hos_Name = findViewById(R.id.doc_profile_Hos_name);
        department = findViewById(R.id.doc_profile_department);
        qualification = findViewById(R.id.doc_profile_qualification);
        experience = findViewById(R.id.doc_profile_experience);
        fees = findViewById(R.id.doc_profile_fees);
        contact = findViewById(R.id.doc_profile_contact);
        email = findViewById(R.id.doc_profile_mail);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String doctorlid = sh.getString("doctorlid", "");
//        System.out.println("--------------------------------------------"+doctorlid);
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_doctor_profile";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                String doc_name = jsonObj.getString("doc_name");
                                doc_Name.setTextColor(Color.WHITE);
                                doc_Name.setText(doc_name);

                                String hos_name = jsonObj.getString("hos_name");
                                hos_Name.setTextColor(Color.WHITE);
                                hos_Name.setText(hos_name);

                                String depart = jsonObj.getString("department");
                                department.setTextColor(Color.WHITE);
                                department.setText(depart);

                                String quali = jsonObj.getString("quali");
                                qualification.setTextColor(Color.WHITE);
                                qualification.setText(quali);

                                String exp = jsonObj.getString("experience");
                                experience.setTextColor(Color.WHITE);
                                experience.setText(exp);

                                String fee = jsonObj.getString("fees");
                                fees.setTextColor(Color.WHITE);
                                fees.setText(fee);

                                String ph_number = jsonObj.getString("contact");
                                contact.setTextColor(Color.WHITE);
                                contact.setText(ph_number);

                                String mail = jsonObj.getString("email");
                                email.setTextColor(Color.WHITE);
                                email.setText(mail);

                                String pic = jsonObj.getString("img");
                                String url="http://" + hu + ":5000" + pic;
                                Picasso.with(getApplicationContext()).load(url).into(doc_Image);

                            } else
                            {
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

                params.put("doctorlid", sh.getString("doctorlid", ""));

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