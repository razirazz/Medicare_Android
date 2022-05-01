package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class view_Doctor extends AppCompatActivity {
    EditText search_doc;
    ListView lv_doctor;
    ArrayList<String> doctor_id, doctor_img, doctor_name, department, hospital_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor);
        lv_doctor = (ListView) findViewById(R.id.lv_view_doctor);
        search_doc = findViewById(R.id.search_discount);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_doctor";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String sucs = jsonObj.getString("status");
                            if (sucs.equalsIgnoreCase("ok")) {


                                JSONArray ja = jsonObj.getJSONArray("data");
                                doctor_id = new ArrayList<>();
                                doctor_img = new ArrayList<>();
                                doctor_name = new ArrayList<>();
                                department = new ArrayList<>();
                                hospital_name = new ArrayList<>();


                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    doctor_id.add((jo.getString("login_id")));
                                    doctor_img.add(jo.getString("doc_img"));
                                    doctor_name.add(jo.getString("doc_name"));
                                    department.add(jo.getString("doc_depart"));
                                    hospital_name.add(jo.getString("hos_name"));

//                                    Toast.makeText(view_Doctor.this, "-------"+doctor_name, Toast.LENGTH_SHORT).show();
                                }

                                lv_doctor.setAdapter((ListAdapter) new custom_view_doctor(getApplicationContext(), doctor_id, doctor_img, doctor_name, department, hospital_name));
                            } else {
                                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "eeeee" + e.toString(), Toast.LENGTH_LONG).show();
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
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
//                params.put("doclid", sh.getString("doclid", ""));

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

