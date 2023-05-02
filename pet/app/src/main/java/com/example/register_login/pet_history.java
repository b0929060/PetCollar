package com.example.register_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


public class pet_history extends Activity {
    TextView t1; // 把視圖的元件宣告成全域變數
    String result; // 儲存資料用的字串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_history);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);
        t1 = (TextView) findViewById(R.id.textview);
        String jsonText = "";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://172.31.57.244/Pet_App/PetList.php");
            HttpResponse httpResponse = client.execute(get);
            jsonText = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        showJson(jsonText);
        Button btn2 = (Button) findViewById(R.id.backbtn4);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pet_history.this, PetHomePage.class);
                startActivity(intent);
            }
        });
    }

    public void showJson(String jsonText) {
        String s = "";
        try {
            JSONArray array = new JSONArray(jsonText);
            for (int i = 0; i <1; i++) {
                JSONObject obj = array.getJSONObject(i);
                String petname = obj.getString("pet_name");
                String gender = obj.getString("gender");
                String age = obj.getString("age");
                String type = obj.getString("type");
                s += "名字: " + petname + "\n"
                        +"性別: " + gender + "\n"
                        +"年齡: " + age + "\n"
                        +"種類: " + type + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //t1.setText(s);
    }
}



