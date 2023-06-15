package com.example.register_login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.TimePicker;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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

public class pet_history extends AppCompatActivity {
    //顯示日期、時間
    TextView textDate,result;
    Button check;
    int year=2023, month=5, dayOfMonth=4;
    //這個dialog的監聽物件(目前空)
    DatePickerDialog.OnDateSetListener pickerDialog;
    TimePickerDialog.OnTimeSetListener timeDialog;
    Calendar calendar = Calendar.getInstance();//用來做date
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_history);
        textDate=findViewById(R.id.textview);
        result = findViewById(R.id.result);
        check = (Button)findViewById(R.id.check);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        String petid = bundle.getString("petid");
        //date裡面dialog的日期選擇給Calendar.xxx及日期文字的顯示
        pickerDialog= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);//年
                calendar.set(Calendar.MONTH,month);//月(*注意：此處的月份從0~11*)
                calendar.set(Calendar.DATE,dayOfMonth);//日
                textDate.setText("日期："+year+"/"+(month+1)+"/"+dayOfMonth);//使其月份+1顯示
            }
        };
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = textDate.getText().toString();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                        .Builder()
                        .permitAll()
                        .build();
                StrictMode.setThreadPolicy(policy);
                String jsonText = "";

                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://172.21.5.153/Pet_App/history.php");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("pet_id", petid));
                    params.add(new BasicNameValuePair("year", Integer.toString(year)));
                    params.add(new BasicNameValuePair("month", Integer.toString(month)));
                    params.add(new BasicNameValuePair("day", Integer.toString(dayOfMonth)));
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpGet get = new HttpGet("http://172.21.5.153/Pet_App/history.php");
                    HttpResponse response = new DefaultHttpClient().execute(post);
                    HttpResponse httpResponse2 = client.execute(get);
                    jsonText = EntityUtils.toString(httpResponse2.getEntity());
                    showJson(jsonText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void datePicker(View v){
        //建立date的dialog
        DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                pickerDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    public void showJson(String jsonText) {
        String s = "?";
        try {
            JSONArray array = new JSONArray(jsonText);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String maxHeart = obj.getString("maxHeart");
                String minHeart = obj.getString("minHeart");
                s += "最大心跳: " + maxHeart +"最小心跳: " + minHeart + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setText(jsonText+s);
    }



}