package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class online_doc_Payment_page extends AppCompatActivity implements View.OnClickListener {
    Spinner bank_name;
    EditText ac_no, pin_no;
    Button pay;
    String []bankss={"SBI","Fedaral","Indian","Panjab National"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_doc_payment_page);

        bank_name =(Spinner) findViewById(R.id.payement_bank_names);
        ac_no = (EditText) findViewById(R.id.payement_ac_number);
        pin_no = (EditText) findViewById(R.id.payement_ac_pin);
        pay = (Button) findViewById(R.id.payement_pay_btn);

        pay.setOnClickListener(this);

        ArrayAdapter<String> adpt = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,bankss);
        bank_name.setAdapter(adpt);
    }

    @Override
    public void onClick(View view) {

        final String bank_ac_no = ac_no.getText().toString();
        final String bank_pin_no = pin_no.getText().toString();
        if( bank_ac_no.length() == 0 ) {
            ac_no.setError("Fields cannot be Empty!");
        }
        else if( bank_pin_no.length() == 0) {
            pin_no.setError("Fields cannot be Empty!");
        }
        else {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/patient_doc_payment";

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                    Intent i = new Intent(getApplicationContext(), request_success_doc.class);
                                    startActivity(i);

                                    Toast.makeText(online_doc_Payment_page.this, "Done..!!", Toast.LENGTH_SHORT).show();
                                } else if (jsonObj.getString("status").equalsIgnoreCase("less")) {

                                    Toast.makeText(getApplicationContext(), "Bank balance is not enough for this Booking", Toast.LENGTH_LONG).show();
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

                    params.put("acc_no", bank_ac_no);
                    params.put("pin_no", bank_pin_no);
                    params.put("med_book_id", sh.getString("med_book_id", ""));
                    params.put("amount", sh.getString("amount", ""));


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