package com.example.petapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PetDetailPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 3;
    Pet pet;

    public PetDetailPagerAdapter(@NonNull FragmentManager fm, Pet pet) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.pet = pet;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                PetInfoFragment petInfoFragment = new PetInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("pet", pet);
                petInfoFragment.setArguments(bundle);
                return petInfoFragment;
            case 1:
                HealthStatusFragment heartStatusFragment = new HealthStatusFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("pet", pet);
                heartStatusFragment.setArguments(bundle1);
                return new HealthStatusFragment();
            case 2:
                ActivityMonitoringFragment activityMonitoringFragment = new ActivityMonitoringFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("pet", pet);
                activityMonitoringFragment.setArguments(bundle2);
                return new ActivityMonitoringFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Pet Info";
            case 1:
                return "Health Status";
            case 2:
                return "Activity Monitoring";
            default:
                return null;
        }
    }
}
