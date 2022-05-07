package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class custom_doctor_schedule extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> doc_id, sch_id, hos_id, doc_name, hos_name, department, sche_date, sch_ftime, sch_ttime;


    public custom_doctor_schedule(android.content.Context applicationcontext, ArrayList<String> doc_id, ArrayList<String> sch_id, ArrayList<String> hos_id, ArrayList<String> doc_name, ArrayList<String> hos_name, ArrayList<String> department, ArrayList<String> sche_date, ArrayList<String> sch_ftime, ArrayList<String> sch_ttime) {
        this.Context = applicationcontext;
        this.doc_id =  doc_id;
        this.sch_id = sch_id;
        this.hos_id = hos_id;
        this.doc_name = doc_name;
        this.hos_name = hos_name;
        this.department = department;
        this.sche_date = sche_date;
        this.sch_ftime = sch_ftime;
        this.sch_ttime = sch_ttime;
//        this.book_date = book_date;
    }


    @Override
    public int getCount() {
        return doc_name.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View gridView;
        if (view == null) {
            gridView = new View(Context);
            gridView = inflater.inflate(R.layout.custom_doctor_schedule, null);

        } else {
            gridView = (View) view;
        }
        TextView lv_doc_name = gridView.findViewById(R.id.custom_schedule_doc_name);
        TextView lv_hos_name = gridView.findViewById(R.id.custom_schedule_hosp_name);
        TextView lv_doc_depart = gridView.findViewById(R.id.custom_schedule_doc_dep);
        TextView lv_sch_date = gridView.findViewById(R.id.custom_schedule_date);
        TextView lv_sch_ftime = gridView.findViewById(R.id.custom_schedule_ftime);
        TextView lv_sch_ttime = gridView.findViewById(R.id.custom_schedule_ttime);
        Button lv_book_doctors = gridView.findViewById(R.id.custom_book_doctor_btn);
        lv_book_doctors.setTag(i);

        lv_book_doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("doctorlid", doc_id.get(pos));
                ed.putString("doc_sch_id", sch_id.get(pos));
                ed.putString("hos_id", hos_id.get(pos));
                ed.commit();

                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/patient_book_doctors";
                RequestQueue requestQueue = Volley.newRequestQueue(Context.getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String sucs = jsonObj.getString("status");
                                    if (sucs.equalsIgnoreCase("ok")) {

                                        Intent i = new Intent(Context.getApplicationContext(), view_DoctorBooking.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Context.startActivity(i);

                                        Toast.makeText(Context.getApplicationContext(), "Doctor Booking Success", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(Context.getApplicationContext(), "no", Toast.LENGTH_LONG).show();
                                    }
                                }
                                catch (Exception e) {
                                    Toast.makeText(Context.getApplicationContext(), "eeeee" + e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(Context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("doctorlid", doc_id.get(pos));
                        params.put("sch_id", sch_id.get(pos));
                        params.put("hos_id", hos_id.get(pos));
                        params.put("lid", sh.getString("lid",""));
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
        });

        lv_doc_name.setTextColor(Color.WHITE);
        lv_doc_name.setText(doc_name.get(i));

        lv_hos_name.setTextColor(Color.WHITE);
        lv_hos_name.setText(hos_name.get(i));

        lv_doc_depart.setTextColor(Color.WHITE);
        lv_doc_depart.setText(department.get(i));

        lv_sch_date.setTextColor(Color.WHITE);
        lv_sch_date.setText(sche_date.get(i));

        lv_sch_ftime.setTextColor(Color.WHITE);
        lv_sch_ftime.setText(sch_ftime.get(i));

        lv_sch_ttime.setTextColor(Color.WHITE);
        lv_sch_ttime.setText(sch_ttime.get(i));

        return gridView;
    }
}
