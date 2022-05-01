package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class view_near_Pharmacy extends AppCompatActivity {

    EditText search_near_pharm;
    ArrayList<String> name, contact, email, place, post, district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_near_pharmacy);

        search_near_pharm = (EditText) findViewById(R.id.search_near_pharm);
        ListView lv_near_pharm = findViewById(R.id.lv_view_near_pharm);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_near_by_pharmacy";

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
                                name = new ArrayList<>();
                                contact = new ArrayList<>();
                                email = new ArrayList<>();
                                place = new ArrayList<>();
                                post = new ArrayList<>();
                                district = new ArrayList<>();


                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    name.add(jo.getString("phar_name"));
                                    contact.add(jo.getString("phar_number"));
                                    email.add(jo.getString("e_mail"));
                                    place.add(jo.getString("place"));
                                    post.add(jo.getString("post"));
                                    district.add(jo.getString("district"));
                                }

                                lv_near_pharm.setAdapter((ListAdapter) new custom_view_pharmacy(getApplicationContext(), name, contact, email, place, post, district));
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
                params.put("lati", LocationService.lati);

                params.put("longi", LocationService.logi);

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