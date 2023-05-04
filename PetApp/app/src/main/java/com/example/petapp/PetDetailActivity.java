package com.example.petapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PetDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PetDetailPagerAdapter pagerAdapter;

    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detail);
        Intent intent = getIntent();
        Pet pet = (Pet) getIntent().getSerializableExtra("pet");



        String tvName = intent.getStringExtra("pet_name");
        String tvGender = intent.getStringExtra("gender");
        String tvAge = intent.getStringExtra("age");
        String tvType = intent.getStringExtra("type");
        String ivImage = intent.getStringExtra("image");

        // 取得 ViewPager
        viewPager = findViewById(R.id.viewPager);

        // 建立三個 Fragment
        PetInfoFragment petInfoFragment = new PetInfoFragment();
        HealthStatusFragment healthStatusFragment = new HealthStatusFragment();
        ActivityMonitoringFragment activityMonitoringFragment = new ActivityMonitoringFragment();

        // 將 Fragment 加入 List 中
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(petInfoFragment);
        fragmentList.add(healthStatusFragment);
        fragmentList.add(activityMonitoringFragment);

        // 建立 PetDetailPagerAdapter 並設定給 ViewPager
        pagerAdapter = new PetDetailPagerAdapter(getSupportFragmentManager(), pet);
        viewPager.setAdapter(pagerAdapter);

        // 取得 TabLayout 並與 ViewPager 連動
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

}