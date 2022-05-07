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

        tv_date.setTextColor(Color.BLACK);
        tv_date.setText(date[i]);

        tv_complaint.setTextColor(Color.BLACK);
        tv_complaint.setText(complaint[i]);

        tv_reply.setTextColor(Color.BLACK);
        tv_reply.setText(reply[i]);

        return gridView;
    }
}
