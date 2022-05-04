package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class view_Schedule extends AppCompatActivity implements TextWatcher {

    ArrayList<String> doc_id, sch_id, hos_id, doc_name, hos_name, department, sche_date, sche_ttime, sche_ftime;
    ListView lv_schedule;
    Button book_btn;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);
        lv_schedule = findViewById(R.id.lv_view_schedule);
        search = findViewById(R.id.view_schedule_search_doctor);

        search.addTextChangedListener(this);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_schedule";

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
                                doc_id = new ArrayList<>();
                                sch_id = new ArrayList<>();
                                hos_id = new ArrayList<>();
                                doc_name = new ArrayList<>();
                                hos_name = new ArrayList<>();
                                department = new ArrayList<>();
                                sche_date = new ArrayList<>();
                                sche_ftime = new ArrayList<>();
                                sche_ttime = new ArrayList<>();

                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    doc_id.add(jo.getString("login_id"));
                                    sch_id.add(jo.getString("sch_id"));
                                    hos_id.add(jo.getString("hos_id"));
                                    doc_name.add(jo.getString("doc_name"));
                                    hos_name.add(jo.getString("hos_name"));
                                    department.add(jo.getString("doc_depart"));
                                    sche_date.add(jo.getString("sch_date"));
                                    sche_ftime.add(jo.getString("sch_ftime"));
                                    sche_ttime.add(jo.getString("sch_ttime"));
                                }

                                lv_schedule.setAdapter((ListAdapter) new custom_doctor_schedule(getApplicationContext(), doc_id, sch_id, hos_id, doc_name, hos_name, department, sche_date, sche_ftime, sche_ttime));
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(final Editable editable) {

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_v_schedule_search";

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
                                doc_id = new ArrayList<>();
                                sch_id = new ArrayList<>();
                                hos_id = new ArrayList<>();
                                doc_name = new ArrayList<>();
                                hos_name = new ArrayList<>();
                                department = new ArrayList<>();
                                sche_date = new ArrayList<>();
                                sche_ftime = new ArrayList<>();
                                sche_ttime = new ArrayList<>();

                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    doc_id.add(jo.getString("login_id"));
                                    sch_id.add(jo.getString("sch_id"));
                                    hos_id.add(jo.getString("hos_id"));
                                    doc_name.add(jo.getString("doc_name"));
                                    hos_name.add(jo.getString("hos_name"));
                                    department.add(jo.getString("doc_depart"));
                                    sche_date.add(jo.getString("sch_date"));
                                    sche_ftime.add(jo.getString("sch_ftime"));
                                    sche_ttime.add(jo.getString("sch_ttime"));
                                }

                                lv_schedule.setAdapter((ListAdapter) new custom_doctor_schedule(getApplicationContext(), doc_id, sch_id, hos_id, doc_name, hos_name, department, sche_date, sche_ftime, sche_ttime));
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
                params.put("doctorlid", sh.getString("doctorlid", ""));
                params.put("search", editable.toString());

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