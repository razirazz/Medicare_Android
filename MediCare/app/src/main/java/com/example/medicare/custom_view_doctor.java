package com.example.medicare;


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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class custom_view_doctor extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> doctor_id, doctor_img, doctor_name, department, hospital_name;


    public custom_view_doctor(android.content.Context applicationcontext, ArrayList<String> doctor_id, ArrayList<String> doctor_img, ArrayList<String> doctor_name, ArrayList<String> department, ArrayList<String> hospital_name) {
        this.Context = applicationcontext;
        this.doctor_img = doctor_img;
        this.doctor_name = doctor_name;
        this.department = department;
        this.hospital_name = hospital_name;
        this.doctor_id = doctor_id;
    }


    @Override
    public int getCount() {
        return doctor_name.size();
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
           gridView = inflater.inflate(R.layout.custom_view_doctor, null);

        } else {
            gridView = (View) view;
        }
        ImageView lv_doc_img = (ImageView) gridView.findViewById(R.id.custom_doc_img);
        TextView lv_doc_name = (TextView) gridView.findViewById(R.id.custom_doc_name);
        TextView lv_doc_department = (TextView) gridView.findViewById(R.id.custom_doc_dep);
        TextView lv_doc_hos = (TextView) gridView.findViewById(R.id.custom_doc_hos);
        Button lv_view_more = (Button) gridView.findViewById(R.id.custom_doc_view_more);
        lv_view_more.setTag(i);
        lv_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("doctorlid", doctor_id.get(pos));
//                Toast.makeText(Context.getApplicationContext(), "--------------------------"+doctor_id.get(pos), Toast.LENGTH_SHORT).show();
                ed.commit();
                Intent i = new Intent(Context.getApplicationContext(), view_Doctor_Profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
            }
        });

        Button lv_view_schedule = gridView.findViewById(R.id.btcustom_doc_view_schedule);
        lv_view_schedule.setTag(i);
        lv_view_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("doctorlid", doctor_id.get(pos));
                Toast.makeText(Context.getApplicationContext(), "--------------------------"+doctor_id.get(pos), Toast.LENGTH_SHORT).show();

                ed.commit();
                Intent i = new Intent(Context, view_Schedule.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
            }
        });


        Button lv_doc_chat = gridView.findViewById(R.id.pat_chat);
        lv_doc_chat.setTag(i);
        lv_doc_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("doctorlid", doctor_id.get(pos));
                ed.commit();
//                Toast.makeText(Context.getApplicationContext(), "---dic_id---"+doctor_id.get(pos), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Context, Test.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
            }
        });



        lv_doc_name.setTextColor(Color.WHITE);
        lv_doc_name.setText(doctor_name.get(i));

        lv_doc_department.setTextColor(Color.WHITE);
        lv_doc_department.setText(department.get(i));

        lv_doc_hos.setTextColor(Color.WHITE);
        lv_doc_hos.setText(hospital_name.get(i));

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000" + doctor_img.get(i);

        Picasso.with(Context.getApplicationContext()).load(url).into(lv_doc_img);


        return gridView;
    }


}
