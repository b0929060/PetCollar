package com.example.register_login;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class PetList extends AppCompatActivity {
    TextView t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petlist);
        t2 = (TextView) findViewById(R.id.petlist);
        Button btn2 = (Button) findViewById(R.id.backbtn);
        Button pet1 = (Button) findViewById(R.id.pet1);
        Button test =(Button) findViewById(R.id.test);
        String result1;
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetList.this,MainActivity.class);
                startActivity(intent);
            }
        });
        pet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetList.this,PetHomePage.class);
                startActivity(intent);
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetList.this,viewpager.class);
                startActivity(intent);
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);
        String jsonText = "";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://172.21.5.153/Pet_App/PetList.php");
            HttpResponse httpResponse = client.execute(get);
            jsonText = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        showJson(jsonText);
    }

    public void showJson(String jsonText) {
        String s = "";
        try {
            JSONArray array = new JSONArray(jsonText);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String petname = obj.getString("pet_name");
                String gender = obj.getString("gender");
                String age = obj.getString("age");
                String type = obj.getString("type");
                s += "名字: " + petname +"種類: " + type + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        t2.setText(s);
    }

}
