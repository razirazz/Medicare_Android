package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class custom_view_pharmacy extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> name, contact, email, place, post, district;


    public custom_view_pharmacy(android.content.Context applicationcontext, ArrayList<String> name,  ArrayList<String> contact, ArrayList<String> email, ArrayList<String> place, ArrayList<String> post, ArrayList<String> district) {
        this.Context = applicationcontext;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.place = place;
        this.post = post;
        this.district = district;

    }

    @Override
    public int getCount() {
        return name.size();
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
            gridView = inflater.inflate(R.layout.custom_view_pharmacy, null);

        } else {
            gridView = (View) view;
        }
        TextView lv_name = gridView.findViewById(R.id.pharm_name);
        TextView lv_contact = gridView.findViewById(R.id.pharm_contact);
        TextView lv_email = gridView.findViewById(R.id.pharm_email);
        TextView lv_place = gridView.findViewById(R.id.pharm_place);
        TextView lv_post = gridView.findViewById(R.id.pharm_post);
        TextView lv_district = gridView.findViewById(R.id.pharm_district);
        Button lv_near_pharm_btn = gridView.findViewById(R.id.home_view_near_pharm_btn);

        lv_name.setTextColor(Color.WHITE);
        lv_name.setText(name.get(i));

        lv_contact.setTextColor(Color.WHITE);
        lv_contact.setText(contact.get(i));

        lv_email.setTextColor(Color.WHITE);
        lv_email.setText(email.get(i));

        lv_place.setTextColor(Color.WHITE);
        lv_place.setText(place.get(i));

        lv_post.setTextColor(Color.WHITE);
        lv_post.setText(post.get(i));

        lv_district.setTextColor(Color.WHITE);
        lv_district.setText(district.get(i));

        return gridView;
    }
}