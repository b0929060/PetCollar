package com.example.petapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.petapp.ml.LstmModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;

public class ActivityMonitoringFragment extends Fragment {
    private Pet pet;
    private TextView ActState;
    String result = "現在動作";

    private RequestQueue requestQueue;
    private Handler handler;
    private Runnable runnable;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_monitoring, container, false);
        ActState = view.findViewById(R.id.tvActState);
        requestQueue = Volley.newRequestQueue(requireContext());
        handler = new Handler();

        // 初始化 LSTM 模型




        // 開始定時抓取資料
        startFetchingData();
        return view;

    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    private void startFetchingData() {
        // 創建一個定時任務，每秒抓取一次資料
        runnable = new Runnable() {
            @Override
            public void run() {
                fetchDataFromPhp();
                handler.postDelayed(this, 1000); // 1秒後再次執行
            }
        };

        handler.post(runnable); // 開始執行定時任務
    }


    private void fetchDataFromPhp() {
        String url = "http://"+GobalVal.ip+"/Pet_App/member/activity.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject JOB = array.getJSONObject(i);
                                Double ax = JOB.getDouble("ax");
                                Double ay = JOB.getDouble("ay");
                                Double az = JOB.getDouble("az");

                                Double D = Double.valueOf(ax);
                                Double D2 = Double.valueOf(ay);
                                Double D3 = Double.valueOf(az);

                                float ax2= D.floatValue();
                                float ay2= D2.floatValue();
                                float az2= D3.floatValue();
                                float[] actValue = new float[3];
                                actValue[0] = ax2;
                                actValue[1] = ay2;
                                actValue[2] = az2;

                                //modeltest(actValue);;//需要的class;
                                ActState.setText(result);//輸出的上面lstmodel的輸出結果

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    //adding our stringrequest to queue


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Log.e("Volley", "Request timeout");
                        } else {
                            Log.e("Volley", "Error: " + error.getMessage());
                        }
                    }
                });

        Volley.newRequestQueue(requireContext()).add(stringRequest);
    }

    private void modeltest(float[] actValue) {

        // 初始化 LSTM 模型
        LstmModel lstmModel = null;
        try {
            lstmModel = LstmModel.newInstance(requireContext());
        } catch (IOException e) {
            // TODO: 處理模型初始化錯誤
            e.printStackTrace();
        }


        // 創建輸入 TensorBuffer
        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1, 3}, DataType.FLOAT32);
        inputFeature0.loadArray(actValue);

        // 執行模型推論
        LstmModel.Outputs outputs = lstmModel.process(inputFeature0);
        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

        // 取得模型輸出結果（這裡假設輸出只有一個值）
        float result = outputFeature0.getFloatArray()[0];

        // 在這裡進行對模型輸出結果的處理，例如將結果顯示在 TextView 上
        ActState.setText("結果：" + result);

        lstmModel.close();
    }




}


