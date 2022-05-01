package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_Discount_Profile extends AppCompatActivity {
    ImageView img;
    TextView name, brand, description, price, offer_price, offer_details, offer_date, offer_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_discount_profile);

        name = findViewById(R.id.dis_pro_med_name);
        brand = findViewById(R.id.dis_pro_med_brand);
        description = findViewById(R.id.dis_pro_med_desc);
        price = findViewById(R.id.dis_pro_med_price);
        offer_price = findViewById(R.id.dis_pro_med_dis_price);
        offer_details = findViewById(R.id.dis_pro_med_dis_details);
        offer_date = findViewById(R.id.dis_pro_med_dis_date);
        offer_time = findViewById(R.id.dis_pro_med_dis_time);

        Button cart = findViewById(R.id.dis_pro_med_cart_btn);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String medid = sh.getString("medlid", "");

                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/patient_med_add_to_cart";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String sucs = jsonObj.getString("status");
                                    if (sucs.equalsIgnoreCase("ok")) {
                                        Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG).show();
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
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("med_id", sh.getString("med_id", ""));

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
        });

        Button buy = findViewById(R.id.dis_pro_med_buy_button);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String medid = sh.getString("med_id", "");
                String pharid = sh.getString("pharm", "");
                String price = sh.getString("price", "");

//                Toast.makeText(view_Medicine_Profile.this, "--------------"+pharid, Toast.LENGTH_SHORT).show();


                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/patient_med_buy";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String sucs = jsonObj.getString("status");
                                    if (sucs.equalsIgnoreCase("ok")) {
                                        Toast.makeText(getApplicationContext(), "medicine purchase request success", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "medicine purchase request error", Toast.LENGTH_LONG).show();
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
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("med_id", sh.getString("med_id", ""));
                        params.put("pharm_lid", sh.getString("pharm_lid", ""));
                        params.put("price", sh.getString("price", ""));
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
        });

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/patient_view_discount_profile";


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

                                String med_name = jsonObj.getString("name");
                                name.setTextColor(Color.WHITE);
                                name.setText(med_name);

                                String med_brand = jsonObj.getString("brand");
                                brand.setTextColor(Color.WHITE);
                                brand.setText(med_brand);

                                String med_desc = jsonObj.getString("description");
                                description.setTextColor(Color.WHITE);
                                description.setText(med_desc);

                                String med_price = jsonObj.getString("price");
                                price.setTextColor(Color.WHITE);
                                price.setText(med_price);

                                String dis_price = jsonObj.getString("offer_price");
                                offer_price.setTextColor(Color.WHITE);
                                offer_price.setText(dis_price);

                                String dis_details = jsonObj.getString("offer_details");
                                offer_details.setTextColor(Color.WHITE);
                                offer_details.setText(dis_details);

                                String dis_date = jsonObj.getString("offer_date");
                                offer_date.setTextColor(Color.WHITE);
                                offer_date.setText(dis_date);

                                String dis_time = jsonObj.getString("offer_time");
                                offer_time.setTextColor(Color.WHITE);
                                offer_time.setText(dis_time);

//                                String pat_img = jsonObj.getString("image");
//
//                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                String ip=sh.getString("ip","");
//
//                                String url="http://" + ip + ":5000"+pat_img;
//
//
//                                Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()). into(pat_img);


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


                params.put("lid", sh.getString("med_id", ""));


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