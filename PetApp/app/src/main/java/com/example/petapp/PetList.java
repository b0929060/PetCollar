package com.example.petapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetList extends AppCompatActivity {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = "http://"+GobalVal.ip+"/Pet_App/member/petlist.php";

    //a list to store all the products
    List<Pet> pets;

    //the recyclerview
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petlist);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pets = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadlist();
    }

    private void loadlist() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GobalVal.url+"petlist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject JOB = array.getJSONObject(i);

                                //adding the product to product list
                                pets.add(new Pet(
                                        JOB.getString("pet_id"),
                                        JOB.getString("pet_name"),
                                        JOB.getString("gender"),
                                        JOB.getInt("age"),
                                        JOB.getString("type"),
                                        JOB.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            PetAdapter petadapter = new PetAdapter((ArrayList<Pet>) pets);
                            recyclerView.setAdapter(petadapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            protected Map<String, String> getParams() throws AuthFailureError {
                // 在這裡返回帶有用戶 id 的請求參數
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(GobalVal.userId));
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}