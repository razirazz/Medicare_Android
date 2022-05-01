package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class signup_page extends AppCompatActivity implements View.OnClickListener {

    ImageView pat_img;
    EditText name, contact, gender, mail, dob, address, place, post, district, pin;
    RadioButton male, female, other;
    Button sign_in;
    String strg_gender = "";
    String path, atype, fname, attach, attatch1;
    byte[] byteArray = null;
    String url3 = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        pat_img = (ImageView) findViewById(R.id.sign_user_image);
        name = (EditText) findViewById(R.id.sign_user_name);
        contact = (EditText) findViewById(R.id.sign_user_contact);
        mail = (EditText) findViewById(R.id.sign_user_mail);
        male = findViewById(R.id.sign_user_gender_male);
        female = findViewById(R.id.sign_user_gender_female);
        other = findViewById(R.id.sign_user_gender_other);
        dob = (EditText) findViewById(R.id.sign_user_dob);
        address = (EditText) findViewById(R.id.sign_user_address);
        place = (EditText) findViewById(R.id.sign_user_place);
        post = (EditText) findViewById(R.id.sign_user_post);
        district = (EditText) findViewById(R.id.sign_user_district);
        pin = (EditText) findViewById(R.id.sign_user_pin);
        sign_in = (Button) findViewById(R.id.sign_user_btn);

        sign_in.setOnClickListener(this);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dob.setText(sdf.format(myCalendar.getTime()));
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(signup_page.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    void showfilechooser(int string) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //getting all types of files

        intent.setType("/");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

        }
    }


    String gen;

    @Override
    public void onClick(View view) {

        if (view == pat_img) {
            showfilechooser(1);
        }
        if (view == sign_in) {
            final String sign_name = name.getText().toString();
            final String sign_contact = contact.getText().toString();
            final String sign_mail = mail.getText().toString();
            final String sign_dob = dob.getText().toString();
            final String sign_gender = gender.getText().toString();
            final String sign_address = address.getText().toString();
            final String sign_place = place.getText().toString();
            final String sign_post = post.getText().toString();
            final String sign_district = district.getText().toString();
            final String sign_pin = pin.getText().toString();

            if (male.isChecked()) {
                gen = "Male";
            } else if (female.isChecked()) {
                gen = "Female";
            } else {
                gen = "Other";
            }

            if (sign_name.length() == 0) {
                name.setError("Fields cannot be Empty!");
            } else if (sign_contact.length() == 0) {
                contact.setError("Fields cannot be Empty!");
            } else if (sign_mail.length() == 0) {
                mail.setError("Fields cannot be Empty!");
            } else if (sign_gender.length() == 0) {
                gender.setError("Fields cannot be Empty!");
            } else if (sign_dob.length() == 0) {
                dob.setError("Fields cannot be Empty!");
            } else if (sign_address.length() == 0) {
                address.setError("Fields cannot be Empty!");
            } else if (sign_place.length() == 0) {
                place.setError("Fields cannot be Empty!");
            } else if (sign_post.length() == 0) {
                post.setError("Fields cannot be Empty!");
            } else if (sign_district.length() == 0) {
                district.setError("Fields cannot be Empty!");
            } else if (sign_pin.length() == 0) {
                pin.setError("Fields cannot be Empty!");
            } else {

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/patient_signup";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), login_page.class);
                                        startActivity(i);
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

                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", sign_name);
                        params.put("phn", sign_contact);
                        params.put("mail", sign_mail);
                        params.put("gender", sign_gender);
                        params.put("dob", sign_dob);
                        params.put("address", sign_address);
                        params.put("place", sign_place);
                        params.put("post", sign_post);
                        params.put("district", sign_district);
                        params.put("pin", sign_pin);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                try {
                    path = FileUtils.getPath(this, uri);
                    File fil = new File(path);
                    float fln = (float) (fil.length() / 1024);
                    atype = path.substring(path.lastIndexOf(".") + 1);
                    fname = path.substring(path.lastIndexOf("/") + 1);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                try {
                    File imgFile = new File(path);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        pat_img.setImageBitmap(myBitmap);
                    }
                    File file = new File(path);
                    byte[] b = new byte[8192];
                    Log.d("bytes read", "bytes read");
                    InputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int bytesRead = 0;
                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }
                    byteArray = bos.toByteArray();
                    String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    attach = str;
                } catch (Exception e) {
                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {

        Intent ii = new Intent(getApplicationContext(), login_page.class);
        startActivity(ii);

    }
}

