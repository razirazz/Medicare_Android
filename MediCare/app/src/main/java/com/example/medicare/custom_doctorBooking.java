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

public class custom_doctorBooking extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> book_id, doc_name, status, date, time, amount;

    public custom_doctorBooking(android.content.Context applicationcontext, ArrayList<String> book_id, ArrayList<String> doc_name, ArrayList<String> status, ArrayList<String> date, ArrayList<String> time, ArrayList<String> amount){
        this.Context = applicationcontext;
        this.book_id = book_id;
        this.doc_name = doc_name;
        this.status = status;
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    @Override
    public int getCount() {
        return doc_name.size();

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
            gridView = inflater.inflate(R.layout.custom_doctor_booking, null);

        }
        else {
            gridView=(View)view;
        }
        TextView lv_doc_name = gridView.findViewById(R.id.booking_doc_name);
        TextView lv_book_status = gridView.findViewById(R.id.booking_book_status);
        TextView lv_book_date = gridView.findViewById(R.id.booking_book_date);
        TextView lv_book_time = gridView.findViewById(R.id.booking_book_time);
        TextView lv_book_amount = gridView.findViewById(R.id.booking_total_amount);
        Button book_doc_btn =  gridView.findViewById(R.id.home_view_doc_booking);



        Button lv_payment_btn = gridView.findViewById(R.id.booking_pay_btn);
        lv_payment_btn.setTag(i);
        lv_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("med_book_id", book_id.get(pos));
                ed.putString("amount", amount.get(pos));
                ed.commit();
                Toast.makeText(Context.getApplicationContext(), "---book_id---"+book_id, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Context.getApplicationContext(), online_doc_Payment_page.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
            }
        });

        lv_doc_name.setTextColor(Color.WHITE);
        lv_doc_name.setText(doc_name.get(i));

        lv_book_status.setTextColor(Color.WHITE);
        lv_book_status.setText(status.get(i));

        lv_book_date.setTextColor(Color.WHITE);
        lv_book_date.setText(date.get(i));

        lv_book_time.setTextColor(Color.WHITE);
        lv_book_time.setText(time.get(i));

        lv_book_amount.setTextColor(Color.WHITE);
        lv_book_amount.setText(amount.get(i));

        return gridView;
    }
}
