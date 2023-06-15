package com.example.register_login;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

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


public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
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
                String url = "http://172.21.5.153/Pet_App/member/insertData.php"; /**ipconfig**/
                String type = "register";
                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                backgroundWorker.execute(url,type,name,email,password);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameRegister.getText().toString();
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                String url = "http://172.21.5.153/Pet_App/member/validateData.php"; /**ipconfig**/
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
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
                    HttpPost post = new HttpPost("http://172.21.5.153/Pet_App/member/validateData.php");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("account", email));
                    params.add(new BasicNameValuePair("password", password));
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpResponse httpResponse = new DefaultHttpClient().execute(post);
                    String strResult = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
                    HttpGet get = new HttpGet("http://172.21.5.153/member/validateData.php");
                    HttpResponse httpResponse2 = client.execute(get);
                    jsonText = EntityUtils.toString(httpResponse2.getEntity());
                    jsonText = strResult;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //showJson(jsonText);
                String s = "";
                try {
                    JSONArray array = new JSONArray(jsonText);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        int id = obj.getInt("id");
                        s += id;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //login.setText(s);

                Intent intent = new Intent(MainActivity.this,PetList.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",s);
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

    }

    public void showJson(String jsonText) {
        String s = "";
        try {
            JSONArray array = new JSONArray(jsonText);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                s += id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}