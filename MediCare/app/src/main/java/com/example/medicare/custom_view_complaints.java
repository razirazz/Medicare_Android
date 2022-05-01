package com.example.medicare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class custom_view_complaints extends BaseAdapter {
    String[] cid, complaint, date, reply;
    private Context context;

    public custom_view_complaints(Context appcontext,String[] cid1,String[] complaint1, String[] date1, String[] reply1)
    {
        this.context=appcontext;
        this.cid=cid1;
        this.complaint=complaint1;
        this.date=date1;
        this.reply=reply1;
    }

    @Override
    public int getCount() {
        return cid.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_complaint, null);

        } else {
            gridView = (View) view;

        }
        TextView tv_date = (TextView) gridView.findViewById(R.id.textView25);
        TextView tv_complaint = (TextView) gridView.findViewById(R.id.textView26);
        TextView tv_reply = (TextView) gridView.findViewById(R.id.textView27);

//        ImageView bt = (ImageView) gridView.findViewById(R.id.button7);
//        bt.setTag(i);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos=(int)v.getTag();
//
//                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//                String hu = sh.getString("ip", "");
//                String url = "http://" + hu + ":5000/and_delete_complaint";
//
//                RequestQueue requestQueue = Volley.newRequestQueue(context);
//                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                // response
//                                try {
//                                    JSONObject jsonObj = new JSONObject(response);
//                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                        Toast.makeText(context, "DELETED", Toast.LENGTH_LONG).show();
//                                        /// Redirect
//                                        Intent ij=new Intent(context, view_complaint.class);
//                                        ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        context.startActivity(ij);
//
//
//                                    }
//                                    else {
//
//                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
//                                    }
//
//                                }    catch (Exception e) {
//                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // error
//                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
//                        Map<String, String> params = new HashMap<String, String>();
//
//
//                        params.put("complaintid", cid[pos]);
//
//
//                        return params;
//                    }
//                };
//
//                int MY_SOCKET_TIMEOUT_MS=100000;
//
//                postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        MY_SOCKET_TIMEOUT_MS,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                requestQueue.add(postRequest);
//
//            }
//        });

        tv_date.setTextColor(Color.BLACK);
        tv_date.setText(date[i]);

        tv_complaint.setTextColor(Color.BLACK);
        tv_complaint.setText(complaint[i]);

        tv_reply.setTextColor(Color.BLACK);
        tv_reply.setText(reply[i]);

        return gridView;
    }
}
