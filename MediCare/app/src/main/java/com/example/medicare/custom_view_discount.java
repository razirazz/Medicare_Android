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

public class custom_view_discount extends BaseAdapter
{

    private android.content.Context Context;
    ArrayList<String> dis_lid, med_name, discount_price, org_price, offer_detail, med_brand, phar_lid;



    public custom_view_discount(android.content.Context applicationcontext, ArrayList<String> dis_lid, ArrayList<String> med_name, ArrayList<String> discount_price, ArrayList<String> org_price, ArrayList<String> offer_detail, ArrayList<String> med_brand, ArrayList<String> phar_lid){
        this.Context = applicationcontext;
        this.dis_lid = dis_lid;
        this.med_name = med_name;
        this.discount_price = discount_price;
        this.org_price = org_price;
        this.offer_detail = offer_detail;
        this.med_brand = med_brand;
        this.phar_lid = phar_lid;

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
        LayoutInflater inflater  = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View gridView;
        if (view==null)
        {
            gridView = new View(Context);
            gridView = inflater.inflate(R.layout.custom_view_discount, null);

        }
        else {
            gridView=(View)view;
        }
        TextView lv_med_name = gridView.findViewById(R.id.custom_dis_med_name);
        TextView lv_med_disount_price = gridView.findViewById(R.id.custom_dis_price);
        TextView lv_med_price = gridView.findViewById(R.id.custom_dis_med_price);
        TextView lv_dis_details = gridView.findViewById(R.id.custom_dis_detail);
        TextView lv_med_brand = gridView.findViewById(R.id.custom_dis_med_brand);

        Button lv_view_more = gridView.findViewById(R.id.custom_dis_view_more);
        lv_view_more.setTag(i);
        lv_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("med_id", dis_lid.get(pos));
                ed.putString("pharm_lid", phar_lid.get(pos));
//                ed.putString("phar_lid", phar_lid.get(pos));
                ed.putString("price", discount_price.get(pos));
                ed.commit();
                Toast.makeText(Context.getApplicationContext(), "-------pharmlid-------"+phar_lid+"-------disprice---------"+discount_price, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Context.getApplicationContext(), view_Discount_Profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
            }
        });

        lv_med_name.setTextColor(Color.WHITE);
        lv_med_name.setText(med_name.get(i));

        lv_med_disount_price.setTextColor(Color.WHITE);
        lv_med_disount_price.setText(discount_price.get(i));

        lv_med_price.setTextColor(Color.WHITE);
        lv_med_price.setText(org_price.get(i));

        lv_dis_details.setTextColor(Color.WHITE);
        lv_dis_details.setText(offer_detail.get(i));

        lv_med_brand.setTextColor(Color.WHITE);
        lv_med_brand.setText(med_brand.get(i));


        return gridView;
    }
}
