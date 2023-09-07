package com.example.petapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class HealthStatusFragment extends Fragment {
    private Pet pet;

    private ImageView UVimage;
    private ImageView TPimage;
    private TextView UVvalue;
    private TextView Tvalue;
    private View temperatureBar;
    private View circlePart;
    //private LineChart lineChart;
    private Thread thread;
    private Timer timer;

    private String heartdata;
    private RequestQueue requestQueue;
    private Handler handler;
    private Runnable runnable;
    //private LineDataSet dataSet;
    //private LineData lineData;

    private double currentTemperature = 25; // 初始溫度值為25°C
    private final double maxTemperature = 61; // 最大溫度值為60°C
    private final double minTemperature = -43; // 最小溫度值為-40°C

    //int heartRate;

    List<Entry> entries = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather, container, false);
        UVvalue = view.findViewById(R.id.tvUV);
        Tvalue = view.findViewById(R.id.tvTEMP);
        UVimage= view.findViewById(R.id.tvImageUV);
        TPimage= view.findViewById(R.id.tvImageTemp);
        temperatureBar = view.findViewById(R.id.temperatureBar);
        circlePart = view.findViewById(R.id.circlePart);
        // 確保 getArguments() 不為空
        setPet(pet);

        //lineChart = view.findViewById(R.id.lineChart);
        requestQueue = Volley.newRequestQueue(requireContext());
        handler = new Handler();
        //setupLineChart();
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
                handler.postDelayed(this, 5000); // 5秒後再次執行
            }
        };

        handler.post(runnable); // 開始執行定時任務
    }
    // 從PetDetailActivity中獲取Pet對象


    private void fetchDataFromPhp() {
        String url = "http://"+GobalVal.ip+"/Pet_App/member/UVsensor.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject JOB = array.getJSONObject(i);
                                double UV = JOB.getDouble("UVindex");
                                double temperature = JOB.getDouble("tempture");
                                updateTemperatureDisplay(temperature);
                                updateUVDisplay(UV);
                                // 在畫面中顯示心率數值
                                //UVvalue.setText(String.valueOf(UV));
                                //Tvalue.setText(String.valueOf(tempture));
                                //updateLineChart(heartRate);
                                uvImageChange(UV);
                                tempImageChange(temperature);

                                //newTemperature = Math.max(minTemperature, Math.min(maxTemperature, newTemperature));
                                // 更新長條圖的高度和顏色

                                updateTemperatureBar(temperature);
                                // 更新顯示的溫度值

                                // 更新當前溫度
                                currentTemperature = temperature;



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
                }){
            protected Map<String, String> getParams() throws AuthFailureError {
                // 在這裡返回帶有用戶 id 的請求參數
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(GobalVal.userId));
                params.put("pet_id", String.valueOf(pet.getPet_ID()));
                return params;
            }
        };
        Volley.newRequestQueue(requireContext()).add(stringRequest);
    }

    private void updateTemperatureDisplay(double temperature) {
        Tvalue.setText("Temperature: " + temperature + "°C");
    }
    private void updateUVDisplay(double UV) {
        UVvalue.setText("UV Index: " + UV);
    }

    private void updateTemperatureBar(double temperature) {
        // 計算新的長條圖高度
        double newHeight = (double) (getProgressBarHeight(temperature) * getResources().getDisplayMetrics().density);

        // 更新長條圖的高度
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) temperatureBar.getLayoutParams();
        params.height = (int)(newHeight);
        temperatureBar.setLayoutParams(params);

        temperatureBar.setBackgroundColor(getTemperatureColor(temperature));

        // 更新圓形部分的位置和顏色
        RelativeLayout.LayoutParams circleParams = (RelativeLayout.LayoutParams) circlePart.getLayoutParams();
        circleParams.topMargin = (int)(newHeight - circlePart.getHeight());
        circlePart.setLayoutParams(circleParams);

        // 更新圓形部分的顏色
        circlePart.setBackgroundColor(getTemperatureColor(temperature));
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // 移除定時任務，避免內存洩漏
        handler.removeCallbacks(runnable);
    }
    /*
    private void setupLineChart() {
         配置 LineChart 的相關屬性
        lineChart.setDrawGridBackground(false);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        YAxis y = lineChart.getAxisLeft();
        y.setTextColor(Color.BLACK);
        y.setDrawGridLines(true);
        lineChart.getAxisRight().setEnabled(false);//右邊Y軸不可視

        // 創建一條空的折線圖資料集
        dataSet = new LineDataSet(null, "Heart Rate");
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);


        lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
    private void updateLineChart(int heartRate) {
        LineData lineData = lineChart.getLineData();
        LineDataSet dataSet = (LineDataSet) lineData.getDataSetByIndex(0);
        Entry newEntry = new Entry(dataSet.getEntryCount(), heartRate);
        lineData.addEntry(newEntry, 0);
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.setVisibleXRange(0,30);//設置可見範圍
        lineChart.moveViewToX(lineData.getEntryCount());//將可視焦點放在最新一個數據，使圖表可移動
        lineChart.invalidate();
    }
     */
    private void uvImageChange(double UV) {
        if (UV >= 11) {
            UVimage.setImageResource(R.drawable.uv5);
        } else if (UV >= 8) {
            UVimage.setImageResource(R.drawable.uv4);
        }
        else if(UV >= 6){
            UVimage.setImageResource(R.drawable.uv3);
        }
        else if(UV >= 3){
            UVimage.setImageResource(R.drawable.uv2);
        }
        else if(UV >= 0){
            UVimage.setImageResource(R.drawable.uv1);
        }
    }

    private void tempImageChange(double TEMP){
        double suitable = 23;
        double warm = 29;
        double hot = 34;
        double cool = 18;
        //float cold = 13;
        if (TEMP >= hot) {
            TPimage.setImageResource(R.drawable.t1);
        }
        else if (TEMP >= warm) {
            TPimage.setImageResource(R.drawable.t2);
        }
        else if (TEMP >= suitable) {
            TPimage.setImageResource(R.drawable.t3);
        }
        else if (TEMP >= cool) {
            TPimage.setImageResource(R.drawable.t4);
        }
        else{
            TPimage.setImageResource(R.drawable.t5);
        }

    }

    // 計算長條圖的高度，這裡固定長條圖底部，高度根據溫度值變化
    private double getProgressBarHeight(double temperature) {
        double minHeight = 0; // 最小高度（對應最低溫度）
        double maxHeight = 300; // 最大高度（對應最高溫度）
        double deltaTemperature = maxTemperature - minTemperature;
        double FixableParameter = 17.4;
        return FixableParameter + minHeight + (maxHeight - minHeight) * (temperature - minTemperature) / deltaTemperature;
    }

    // 根據溫度值計算長條圖顏色
    private int getTemperatureColor(double temperature) {
        int startColor = getResources().getColor(R.color.colorRed); // 紅色 (40°C)
        int middleColor = getResources().getColor(R.color.colorOrange); // 橘色 (35°C)
        int middleColor2 = getResources().getColor(R.color.colorYellow); // 黃色 (30°C)
        int middleColor3 = getResources().getColor(R.color.colorGreen); // 綠色 (25°C)
        int middleColor4 = getResources().getColor(R.color.colorBlue); // 淺藍色 (20°C)
        int endColor = getResources().getColor(R.color.colorEnd); // 藍色 (15°C)
        int grayColor = getResources().getColor(R.color.colorGray); // 灰色 (10°C)
        int whiteColor = getResources().getColor(R.color.colorWhite); // 白色 (0°C)
        int blackColor = getResources().getColor(R.color.black);

        int red, green, blue;
        if (temperature >= 40) {
            // 紅色到橘色之間的色調變化

            red = (int) (Color.red(startColor));
            green = (int) (Color.green(startColor));
            blue = (int) (Color.blue(startColor));
        }
        else if (temperature >= 35) {
            // 紅色到橘色之間的色調變化
            double scale = (double) (temperature - 40) / -5; // (40°C~35°C)
            red = (int) (Color.red(startColor) * (1 - scale) + Color.red(middleColor) * scale);
            green = (int) (Color.green(startColor) * (1 - scale) + Color.green(middleColor) * scale);
            blue = (int) (Color.blue(startColor) * (1 - scale) + Color.blue(middleColor) * scale);
        } else if (temperature >= 30) {
            // 橘色到黃色之間的色調變化
            double scale = (double) (temperature - 35) / -5; // (35°C~30°C)
            red = (int) (Color.red(middleColor) * (1 - scale) + Color.red(middleColor2) * scale);
            green = (int) (Color.green(middleColor) * (1 - scale) + Color.green(middleColor2) * scale);
            blue = (int) (Color.blue(middleColor) * (1 - scale) + Color.blue(middleColor2) * scale);
        } else if (temperature >= 25) {
            // 黃色到綠色之間的色調變化
            double scale = (double) (temperature - 30) / -5; // (30°C~25°C)
            red = (int) (Color.red(middleColor2) * (1 - scale) + Color.red(middleColor3) * scale);
            green = (int) (Color.green(middleColor2) * (1 - scale) + Color.green(middleColor3) * scale);
            blue = (int) (Color.blue(middleColor2) * (1 - scale) + Color.blue(middleColor3) * scale);
        } else if (temperature >= 20) {
            // 綠色到淺藍色之間的色調變化
            double scale = (double) (temperature - 25) / -5; // (25°C~20°C)
            red = (int) (Color.red(middleColor3) * (1 - scale) + Color.red(middleColor4) * scale);
            green = (int) (Color.green(middleColor3) * (1 - scale) + Color.green(middleColor4) * scale);
            blue = (int) (Color.blue(middleColor3) * (1 - scale) + Color.blue(middleColor4) * scale);
        } else if (temperature >= 15) {
            // 淺藍色到藍色之間的色調變化
            double scale = (double) (temperature - 20) / -5; // (20°C~15°C)
            red = (int) (Color.red(middleColor4) * (1 - scale) + Color.red(endColor) * scale);
            green = (int) (Color.green(middleColor4) * (1 - scale) + Color.green(endColor) * scale);
            blue = (int) (Color.blue(middleColor4) * (1 - scale) + Color.blue(endColor) * scale);
        } else if (temperature >= 10){
            // 藍色到灰色之間的色調變化
            double scale = (double) (temperature - 15) / -5; // (15°C~10°C)
            red = (int) (Color.red(endColor) * (1 - scale) + Color.red(grayColor) * scale);
            green = (int) (Color.green(endColor) * (1 - scale) + Color.green(grayColor) * scale);
            blue = (int) (Color.blue(endColor) * (1 - scale) + Color.blue(grayColor) * scale);
        }
        else if (temperature >= 0){
            // 灰色到白之間的色調變化
            double scale = (double) (temperature - 10) / -10; // (10°C~0°C)
            red = (int) (Color.red(grayColor) * (1 - scale) + Color.red(whiteColor) * scale);
            green = (int) (Color.green(grayColor) * (1 - scale) + Color.green(whiteColor) * scale);
            blue = (int) (Color.blue(grayColor) * (1 - scale) + Color.blue(whiteColor) * scale);
        }
        else{
            red = (int) (Color.red(blackColor));
            green = (int) (Color.green(blackColor));
            blue = (int) (Color.blue(blackColor));
        }

        // 將RGB值組合成ARGB顏色
        return Color.argb(255, red, green, blue);
    }



}
