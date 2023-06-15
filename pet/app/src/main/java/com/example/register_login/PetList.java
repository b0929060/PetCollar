package com.example.register_login;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class PetList extends AppCompatActivity {
    TextView t2;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petlist);
        t2 = (TextView) findViewById(R.id.petlist);
        Button btn2 = (Button) findViewById(R.id.backbtn);
        Button pet1 = (Button) findViewById(R.id.pet1);
        Button test =(Button) findViewById(R.id.test);
        Button add =(Button) findViewById(R.id.addbtn);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

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
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("petid","1");
                intent.putExtras(bundle);
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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetList.this,addPet.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("id",id);
                intent.putExtras(bundle2);
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
            HttpPost post = new HttpPost("http://172.21.5.153/Pet_App/PetList.php");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(post);
            String strResult = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
            HttpGet get = new HttpGet("http://172.21.5.153/Pet_App/PetList.php");
            HttpResponse httpResponse2 = client.execute(get);
            jsonText = EntityUtils.toString(httpResponse2.getEntity());
            jsonText = strResult;
            //t2.setText(strResult);
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
