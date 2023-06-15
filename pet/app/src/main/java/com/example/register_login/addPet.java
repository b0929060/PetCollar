package com.example.register_login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class addPet extends AppCompatActivity {
    TextView genderRegister,typeRegister;
    EditText nameRegister,ageRegister;
    String id,pet_name,age,gender,type;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pet);

        b = (Button) findViewById(R.id.button1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        nameRegister = (EditText) findViewById(R.id.addpetname);
        ageRegister = (EditText) findViewById(R.id.editText2);
        genderRegister = (TextView) findViewById(R.id.selectgender) ;
        typeRegister = (TextView) findViewById(R.id.selecttype) ;

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.gender,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setSelection(2, false);
        spinner.setOnItemSelectedListener(spnOnItemSelected);
        genderRegister.setText(spinner.getSelectedItem().toString());
        ArrayAdapter<CharSequence> adapter2 =
                ArrayAdapter.createFromResource(this,
                        R.array.type,
                        android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        //spinner.setSelection(2, false);
        spinner2.setOnItemSelectedListener(spnOnItemSelected2);
        typeRegister.setText(spinner2.getSelectedItem().toString());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pet_name = nameRegister.getText().toString();
                age = ageRegister.getText().toString();
                //insert
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                        .Builder()
                        .permitAll()
                        .build();
                StrictMode.setThreadPolicy(policy);
                String jsonText = "";

                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://172.21.5.153/Pet_App/addPet.php");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id",id));
                    params.add(new BasicNameValuePair("pet_name",pet_name));
                    params.add(new BasicNameValuePair("age",age));
                    params.add(new BasicNameValuePair("gender",gender));
                    params.add(new BasicNameValuePair("type",type));
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpResponse response = new DefaultHttpClient().execute(post);
                    Toast.makeText(v.getContext(), "新增成功", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long idd) {
            String sPos=String.valueOf(pos);
            String sInfo=parent.getItemAtPosition(pos).toString();
            //String sInfo=parent.getSelectedItem().toString();
            genderRegister.setText(sInfo);
            gender = genderRegister.getText().toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };
    private AdapterView.OnItemSelectedListener spnOnItemSelected2
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long idd) {
            String sPos=String.valueOf(pos);
            String sInfo=parent.getItemAtPosition(pos).toString();
            //String sInfo=parent.getSelectedItem().toString();
            typeRegister.setText(sInfo);
            type = typeRegister.getText().toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }

    };



}