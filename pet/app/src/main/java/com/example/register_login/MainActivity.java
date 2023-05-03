package com.example.register_login;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;


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
                Intent intent = new Intent(MainActivity.this,PetList.class);
                startActivity(intent);

            }
        });


    }
}