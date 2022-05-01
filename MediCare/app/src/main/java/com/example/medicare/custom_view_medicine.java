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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class custom_view_medicine extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> med_id, phar_lid, med_img, med_name, med_brand, med_price;


    public custom_view_medicine(android.content.Context applicationcontext, ArrayList<String> med_img, ArrayList<String> phar_lid, ArrayList<String> med_id, ArrayList<String> med_name, ArrayList<String> med_brand, ArrayList<String> med_price) {
        this.Context = applicationcontext;
        this.med_id = med_id;
        this.phar_lid = phar_lid;
        this.med_name = med_name;
        this.med_brand = med_brand;
        this.med_price = med_price;
    }


    @Override
    public int getCount() {
        return med_name.size();

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
            gridView = inflater.inflate(R.layout.custom_view_medicine, null);

        } else {
            gridView = (View) view;
        }
        TextView lv_med_name = gridView.findViewById(R.id.custom_med_name);
        TextView lv_med_brand = gridView.findViewById(R.id.custom_med_view_brand);
//        TextView lv_med_offer_price = gridView.findViewById(R.id.custom_med_offer_price);
//        TextView lv_med_offer_details = gridView.findViewById(R.id.custom_med_view_offer);
        TextView lv_med_price = gridView.findViewById(R.id.custom_med_view_price);
        Button lv_view_more = gridView.findViewById(R.id.custom_med_view_more_btn);
        lv_view_more.setTag(i);

        lv_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("med_id", med_id.get(pos));
                ed.putString("pharm_lid", phar_lid.get(pos));
                ed.putString("price", med_price.get(pos));
                ed.commit();
//                Toast.makeText(Context.getApplicationContext(), "=========="+med_price.get(pos), Toast.LENGTH_SHORT).show();


                Intent i = new Intent(Context.getApplicationContext(), view_Medicine_Profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
//                Toast.makeText(Context.getApplicationContext(), "----------------------phar"+phar_lid.get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        lv_med_name.setTextColor(Color.WHITE);
        lv_med_name.setText(med_name.get(i));

        lv_med_brand.setTextColor(Color.WHITE);
        lv_med_brand.setText(med_brand.get(i));

//        lv_med_offer_price.setTextColor(Color.BLACK);
//        lv_med_offer_price.setText(med_offer_price.get(i));
//
//        lv_med_offer_details.setTextColor(Color.BLACK);
//        lv_med_offer_details.setText(med_offer_details.get(i));

        lv_med_price.setTextColor(Color.WHITE);
        lv_med_price.setText(med_price.get(i));


        return gridView;
    }
}
