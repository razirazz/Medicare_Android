package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class update_Profile extends AppCompatActivity implements View.OnClickListener {

    ImageView pat_img;
    EditText name, address, contact, dob, email, place, post, district, pin;
    RadioButton male, female, other;
    Button update;
    String gen_strg = "";
    String path, atype, fname, attach="aa";
    byte[] byteArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);


        pat_img = (ImageView) findViewById(R.id.update_img);
        name = (EditText) findViewById(R.id.update_name);
        address = (EditText) findViewById(R.id.update_address);
        contact = (EditText) findViewById(R.id.update_contact);
        dob = (EditText) findViewById(R.id.update_dob);
        male = findViewById(R.id.update_gender_male);
        female = findViewById(R.id.update_gender_female);
        other = findViewById(R.id.update_gender_other);
        email = (EditText) findViewById(R.id.update_mail);
        place = (EditText) findViewById(R.id.update_place);
        post = (EditText) findViewById(R.id.update_post);
        district = (EditText) findViewById(R.id.update_district);
        pin = (EditText) findViewById(R.id.update_pin);
        update = (Button) findViewById(R.id.update_btn);
        update.setOnClickListener(this);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name.setText(sh.getString("name", ""));
        address.setText(sh.getString("address", ""));
        contact.setText(sh.getString("contact", ""));
        dob.setText(sh.getString("birth_date", ""));
        email.setText(sh.getString("email", ""));
        place.setText(sh.getString("place", ""));
        post.setText(sh.getString("post", ""));
        district.setText(sh.getString("district", ""));
        pin.setText(sh.getString("pin", ""));
        String gen = sh.getString("gender", "");

        if (gen.equalsIgnoreCase("male")) {
            male.setChecked(true);
        }
        if (gen.equalsIgnoreCase("female")) {
            female.setChecked(true);
        }
        if (gen.equalsIgnoreCase("other")) {
            other.setChecked(true);
        }

        String img = sh.getString("pic", "");
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + sh.getString("pic", "");
        Picasso.with(getApplicationContext()).load(url).into(pat_img);

        pat_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == pat_img) {
            showfilechooser(1);
        } else {
            String stg_pat_name = name.getText().toString();
            String stg_pat_adrs = address.getText().toString();
            String stg_pat_contact = contact.getText().toString();
            String stg_pat_dob = dob.getText().toString();
            String stg_pat_male = male.getText().toString();
            String stg_pat_female = female.getText().toString();
            String stg_pat_other = other.getText().toString();
            String stg_pat_mail = email.getText().toString();
            String stg_pat_place = place.getText().toString();
            String stg_pat_post = post.getText().toString();
            String stg_pat_district = district.getText().toString();
            String stg_pat_pin = pin.getText().toString();


            if (male.isChecked()) {
                gen_strg = "male";
            }
            if (female.isChecked()) {
                gen_strg = "female";
            }
            if (other.isChecked()) {
                gen_strg = "other";
            }

            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/patient_update_profile";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                    Intent ins = new Intent(getApplicationContext(), view_Profile.class);
                                    startActivity(ins);
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

                    params.put("name", stg_pat_name);
                    params.put("address", stg_pat_adrs);
                    params.put("contact", stg_pat_contact);
                    params.put("date_birth", stg_pat_dob);
                    params.put("email", stg_pat_mail);
                    params.put("place", stg_pat_place);
                    params.put("post", stg_pat_post);
                    params.put("district", stg_pat_district);
                    params.put("pin", stg_pat_pin);
                    params.put("gender", gen_strg);
                    params.put("lid", sh.getString("lid", ""));
                    params.put("photo", attach);

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

    void showfilechooser(int string) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //getting all types of files
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
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
        Intent ii=new Intent(getApplicationContext(), patient_main_home.class);
        startActivity(ii);
    }
}
