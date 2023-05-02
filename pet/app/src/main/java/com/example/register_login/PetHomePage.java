package com.example.register_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class PetHomePage extends AppCompatActivity{
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pet_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Button btn2 = (Button) findViewById(R.id.backbtn2);
        Button databtn = (Button) findViewById(R.id.databtn);
        Button hisbtn = (Button) findViewById(R.id.hisbtn);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHomePage.this, PetList.class);
                startActivity(intent);
            }
        });
        databtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHomePage.this, pet_data.class);
                startActivity(intent);
            }
        });
        hisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHomePage.this, pet_history.class);
                startActivity(intent);
            }
        });
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (id){
                case R.id.pet_data:
                    Toast.makeText(this, "按下  data。", Toast.LENGTH_LONG).show();
                    break;
                case R.id.pet_history:
                    Toast.makeText(this, "按下  history。", Toast.LENGTH_LONG).show();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

}
