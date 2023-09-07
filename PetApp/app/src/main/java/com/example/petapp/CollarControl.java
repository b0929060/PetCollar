package com.example.petapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollarControl extends AppCompatActivity {

    private RecyclerView recyclerViewPaired;
    private CollarAdapter pairedAdapter;
    private List<Collar> pairedCollars = new ArrayList<>();
    private List<Pet> pets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collar_control);

        recyclerViewPaired = findViewById(R.id.recyclerView_collar);
        recyclerViewPaired.setHasFixedSize(true);
        recyclerViewPaired.setLayoutManager(new LinearLayoutManager(this));
        loadPetList();
        collarlist();


    }




    private void collarlist() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GobalVal.url + "collarlist.php",
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
                                int a = JOB.getInt("collar_id");
                                int b = JOB.getInt("pet_id");
                                if (b!=-1){
                                    pairedCollars.add(new Collar(
                                            JOB.getInt("collar_id"),
                                            JOB.getString("pet_id"),
                                            JOB.getString("pet_name"),
                                            JOB.getString("image")
                                    ));
                                }else if(a!=-1){
                                    pairedCollars.add(new Collar(
                                            JOB.getInt("collar_id"),
                                            "unpaired",
                                            "UNPAIRED",
                                            "https://i.imgur.com/xYwEa2a.png"
                                    ));
                                }
                                else{

                                }

                            }
                            //creating adapter object and setting it to recyclerview
                            pairedAdapter = new CollarAdapter((ArrayList<Collar>) pairedCollars);
                            recyclerViewPaired.setAdapter(pairedAdapter);
                                // 設置取消配對按鈕的點擊事件
                                pairedAdapter.setOnUnpairClickListener(new CollarAdapter.OnUnpairClickListener() {
                                    @Override
                                    public void onUnpairClick(int position) {
                                        Collar collar = pairedCollars.get(position);
                                        int collarId = collar.getCollarId();
                                        // 取消配對
                                        unpairCollar(collarId);


                                        // 更新 RecyclerView
                                        //refreshPage();
                                        //pairedCollars.remove(position);
                                        //pairedAdapter.notifyItemRemoved(position);
                                    }
                                });

                            // 設置配對按鈕的點擊事件
                            pairedAdapter.setOnPairClickListener(new CollarAdapter.OnPairClickListener() {
                                @Override
                                public void onPairClick(int position) {
                                    Collar collar = pairedCollars.get(position);
                                    int collarId = collar.getCollarId();
                                    // 弹出选择宠物的对话框
                                    showPetSelectionDialog(pets,collarId);
                                }
                            });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
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


    //取消配對
    private void unpairCollar(int collarId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GobalVal.url + "unpair_collar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("collar_id", String.valueOf(collarId));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

        refreshPage();
    }

    //選擇寵物視窗
    private void loadPetList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GobalVal.url + "petlist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Converting the string to a JSON array object
                            JSONArray array = new JSONArray(response);

                            // Clear the existing list of pets
                            pets.clear();

                            // Traversing through all the objects
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject JOB = array.getJSONObject(i);

                                // Adding the pet to the list
                                pets.add(new Pet(
                                        JOB.getString("pet_id"),
                                        JOB.getString("pet_name"),
                                        JOB.getString("gender"),
                                        JOB.getInt("age"),
                                        JOB.getString("type"),
                                        JOB.getString("image")
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                // Add user id to the request parameters
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(GobalVal.userId));
                return params;
            }
        };
        // Add the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    //資料庫新增配對
    private void pairCollarWithPet(int collarId, String petId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GobalVal.url + "pair_collar_with_pet.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("collar_id", String.valueOf(collarId));
                params.put("pet_id", petId);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

        //重整
        refreshPage();
    }

    private void showPetSelectionDialog(List<Pet> petList, int collarId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("選擇寵物");

        // 創adapter配對寵物資訊
        ArrayAdapter<Pet> adapter = new ArrayAdapter<Pet>(
                this,
                android.R.layout.simple_list_item_1,
                petList
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.pet_select, parent, false);
                }


                Pet pet = getItem(position);

                // 設置cardview
                TextView tvPetName = convertView.findViewById(R.id.tvPetName);
                ImageView ivPetImage = convertView.findViewById(R.id.ivImage);

                tvPetName.setText(pet.getName());
                Picasso.get().load(pet.getImageUrl()).into((ImageView) convertView.findViewById(R.id.ivImage));


                return convertView;
            }
        };

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 選擇的寵物
                Pet selectedPet = petList.get(which);
                String petId = selectedPet.getPet_ID();
                // 更新後端
                pairCollarWithPet(collarId, petId);
                // 刷新界面
                //adapter.notifyDataSetChanged();

            }

        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void refreshPage() {
        LoadingManager loadingManager = new LoadingManager(this);

        //加載動畫
        loadingManager.showLoading(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 重載
                // 刷新
                recyclerViewPaired.setAdapter(null);
                pairedCollars.clear();
                collarlist();
                //隱藏動畫
                loadingManager.hideLoading();
            }
        }, 2000); // 延遲2秒

    }









}



