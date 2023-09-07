package com.example.petapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class login_register extends AppCompatActivity {
    EditText nameRegister;
    EditText emailRegister;
    EditText passwordRegister;
    Button register;

    EditText emailLogin;
    EditText passwordLogin;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        nameRegister = findViewById(R.id.editText1);
        emailRegister = findViewById(R.id.editText2);
        passwordRegister = findViewById(R.id.editText3);
        register = findViewById(R.id.button1);
        emailLogin = findViewById(R.id.editText4);
        passwordLogin = findViewById(R.id.editText5);
        login = findViewById(R.id.button2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameRegister.getText().toString();
                String email = emailRegister.getText().toString();
                String password = passwordRegister.getText().toString();
                String url = "http://"+GobalVal.ip+"/Pet_App/member/insertData.php";
                String type = "register";
                BackgroundWorker backgroundWorker = new BackgroundWorker(login_register.this);
                backgroundWorker.execute(url,type,name,email,password);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameRegister.getText().toString();
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                String url = "http://"+GobalVal.ip+"/Pet_App/member/validateData.php";
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker(login_register.this);
                backgroundWorker.execute(url,type,name,email,password);

                //存id
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                        .Builder()
                        .permitAll()
                        .build();
                StrictMode.setThreadPolicy(policy);
                String jsonText = "";

                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://"+GobalVal.ip+"/Pet_App/member/validateData.php");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("account", email));
                    params.add(new BasicNameValuePair("password", password));
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpResponse httpResponse = new DefaultHttpClient().execute(post);
                    String strResult = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
                    HttpGet get = new HttpGet("http://"+GobalVal.ip+"/member/validateData.php");
                    HttpResponse httpResponse2 = client.execute(get);
                    jsonText = EntityUtils.toString(httpResponse2.getEntity());
                    jsonText = strResult;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //showJson(jsonText);
                try {
                    JSONArray array = new JSONArray(jsonText);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        int id = obj.getInt("id");
                        GobalVal.setUserId(id);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(GobalVal.userId != -1){
                    Intent intent = new Intent(login_register.this,HomePage.class);
                    startActivity(intent);
                }

            }
        });


    }

}
