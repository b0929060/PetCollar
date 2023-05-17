package com.example.petapp;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class HealthStatusFragment extends Fragment {
    private TextView heartrate;
    private LineChart lineChart;
    private Thread thread;
    private Timer timer;

    private String heartdata;
    private RequestQueue requestQueue;
    private Handler handler;
    private Runnable runnable;
    private LineDataSet dataSet;
    private LineData lineData;

    int heartRate;

    private static final String url = "http://192.168.124.195/Pet_App/member/Heart.php";

    List<Entry> entries = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_status, container, false);

        heartrate = view.findViewById(R.id.tvHeart);
        lineChart = view.findViewById(R.id.lineChart);
        requestQueue = Volley.newRequestQueue(requireContext());
        handler = new Handler();
        setupLineChart();
        // 開始定時抓取資料
        startFetchingData();
        return view;
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
        String url = "http://192.168.124.195/Pet_App/member/Heart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject JOB = array.getJSONObject(i);


                                int heartRate = JOB.getInt("heart_rate");

                                // 在畫面中顯示心率數值
                                heartrate.setText(String.valueOf(heartRate));
                                updateLineChart(heartRate);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 移除定時任務，避免內存洩漏
        handler.removeCallbacks(runnable);
    }
    private void setupLineChart() {
        // 配置 LineChart 的相關屬性
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



}
