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

public class custom_medicine_booking extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> book_id, name, brand, amount, quantity, date;

    public custom_medicine_booking(android.content.Context applicationcontext, ArrayList<String> book_id, ArrayList<String> name, ArrayList<String> brand, ArrayList<String> quantity, ArrayList<String> amount, ArrayList<String> date){
        this.Context = applicationcontext;
        this.book_id = book_id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.amount = amount;
        this.date = date;
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
        LayoutInflater inflater  = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View gridView;
        if (view==null)
        {
            gridView = new View(Context);
            gridView = inflater.inflate(R.layout.custom_medicine_booking, null);

        }
        else {
            gridView=(View)view;
        }
        TextView lv_med_name = gridView.findViewById(R.id.custom_med_book_name);
        TextView lv_med_brand = gridView.findViewById(R.id.custom_med_book_brand);
        TextView lv_med_quantity = gridView.findViewById(R.id.custom_med_book_quantity);
        TextView lv_med_amount = gridView.findViewById(R.id.custom_med_book_amount);
        TextView lv_med_date = gridView.findViewById(R.id.custom_med_book_date);
        Button book_med_btn =  gridView.findViewById(R.id.home_view_med_booking);

        Button lv_payment_btn = gridView.findViewById(R.id.custom_med_book_pay_btn);
        lv_payment_btn.setTag(i);
        lv_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("book_id", book_id.get(pos));
                ed.putString("amount", amount.get(pos));
                ed.commit();
                Toast.makeText(Context.getApplicationContext(), "---book_id---"+book_id, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Context.getApplicationContext(), online_med_Payment_page.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
            }
        });

        lv_med_name.setTextColor(Color.WHITE);
        lv_med_name.setText(name.get(i));

        lv_med_brand.setTextColor(Color.WHITE);
        lv_med_brand.setText(brand.get(i));

        lv_med_quantity.setTextColor(Color.WHITE);
        lv_med_quantity.setText(quantity.get(i));

        lv_med_amount.setTextColor(Color.WHITE);
        lv_med_amount.setText(amount.get(i));

        lv_med_date.setTextColor(Color.WHITE);
        lv_med_date.setText(date.get(i));

        return gridView;
    }

}