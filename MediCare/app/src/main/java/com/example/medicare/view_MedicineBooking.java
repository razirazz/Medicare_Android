package com.example.medicare;

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

public class view_MedicineBooking extends AppCompatActivity {

    ArrayList<String> book_id, name, brand, date, amount, quantity;
    EditText search;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_medicine_booking);


        search = (EditText) findViewById(R.id.custom_med_book_search);
        ListView lv_medBook = (ListView) findViewById(R.id.lv_med_book);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_med_booking";

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
                                book_id = new ArrayList<>();
                                name = new ArrayList<>();
                                brand = new ArrayList<>();
                                date = new ArrayList<>();
                                amount = new ArrayList<>();
                                quantity = new ArrayList<>();

                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    book_id.add(jo.getString("medbook_id"));
                                    name.add(jo.getString("med_name"));
                                    brand.add(jo.getString("med_brand"));
                                    date.add(jo.getString("date"));
                                    amount.add(jo.getString("cost"));
                                    quantity.add(jo.getString("quantity"));
                                }
                                lv_medBook.setAdapter((ListAdapter) new custom_medicine_booking(getApplicationContext(), book_id, name, brand, quantity, amount, date));
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