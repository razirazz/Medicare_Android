package com.example.medicare;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
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

public class view_Medicine extends AppCompatActivity {

    EditText search_med;
    ListView lv_medicine;
    ArrayList<String> medicine_img, med_id, medicine_name, med_brand, med_price, pharm_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_medicine);
        lv_medicine = findViewById(R.id.lv_view_medicine);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_medicine";

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
                                medicine_img = new ArrayList<>();
                                medicine_name = new ArrayList<>();
                                med_brand = new ArrayList<>();
                                med_price = new ArrayList<>();
                                med_id = new ArrayList<>();
                                pharm_id = new ArrayList<>();

                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    med_id.add(jo.getString("med_id"));
                                    medicine_img.add(jo.getString("med_img"));
                                    medicine_name.add(jo.getString("med_name"));
                                    med_brand.add(jo.getString("med_brand"));
//                                    med_offer_price.add(jo.getString("offer_price"));
                                    med_price.add(jo.getString("price"));
                                    pharm_id.add(jo.getString("phar_lid"));


                                }

                                lv_medicine.setAdapter((ListAdapter) new custom_view_medicine(getApplicationContext(), medicine_img, pharm_id, med_id, medicine_name, med_brand, med_price));
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
                params.put("med_id", sh.getString("med_id", ""));
                params.put("pharm_lid", sh.getString("pharm_lid", ""));
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