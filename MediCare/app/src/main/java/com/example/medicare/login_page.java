package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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


public class login_page extends AppCompatActivity implements View.OnClickListener {

    EditText name, password;
    //    EditText pass;
    Button log_in;
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        name = (EditText) findViewById(R.id.login_user_name);
        password = (EditText) findViewById(R.id.login_user_pass);
        log_in = (Button) findViewById(R.id.login_user_btn);
        sign_up = findViewById(R.id.sign_up_btn);

        log_in.setOnClickListener(this);
        sign_up.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == sign_up) {
            Intent i = new Intent(getApplicationContext(), signup_page.class);
            startActivity(i);
        }
        if (view == log_in) {
            final String login_name = name.getText().toString();
            final String login_pass = password.getText().toString();
            if (login_name.length() == 0) {
                name.setError("Fields cannot be Empty!");
            } else if (login_pass.length() == 0) {
                password.setError("Fields cannot be Empty!");
            } else {

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/patient_login";
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
                                        String lid = jsonObj.getString("lid");
                                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor ed = sh.edit();
                                        ed.putString("lid", lid);
                                        ed.commit();

                                        if (jsonObj.getString("type").equalsIgnoreCase("patient")) {
                                            Intent ij1 = new Intent(getApplicationContext(), patient_main_home.class);
                                            startActivity(ij1);
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "invalid username and password", Toast.LENGTH_LONG).show();
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

                        params.put("user_name", login_name);
                        params.put("password", login_pass);

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


    }
}
