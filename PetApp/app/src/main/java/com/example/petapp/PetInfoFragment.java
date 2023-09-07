package com.example.petapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class PetInfoFragment extends Fragment {

    private TextView mNameTextView;
    private TextView mAgeTextView;
    private TextView mGenderTextView;
    private TextView mTypeTextView;
    private ImageView mImageView;
    private Pet pet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_info, container, false);
        setPet(pet);


        // 獲取介面元素
        mNameTextView = view.findViewById(R.id.tvName);
        mGenderTextView = view.findViewById(R.id.tvGender);
        mAgeTextView = view.findViewById(R.id.tvAge);
        mTypeTextView = view.findViewById(R.id.tvType);
        mImageView = view.findViewById(R.id.ivImage);



        // 從PetDetailActivity中獲取Pet對象
        //Bundle bundle = getArguments();
        //if (bundle != null) {
            //pet = (Pet) bundle.getSerializable("pet");
            mNameTextView.setText(pet.getName());
            mGenderTextView.setText(pet.getGender());
            mAgeTextView.setText(String.valueOf(pet.getAge()));
            mTypeTextView.setText(pet.getType());
            Picasso.get().load(pet.getImageUrl()).into(mImageView);
        //}




        // 將Pet資訊顯示在介面中
        //mNameTextView.setText(pet.getName());
        //mAgeTextView.setText(String.valueOf(pet.getAge()));
        //mGenderTextView.setText(pet.getGender());
        //mTypeTextView.setText(pet.getType());

        return view;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    @Override
    public void onResume() {
        super.onResume();

        if (pet != null) {
            mNameTextView.setText(pet.getName());
            mGenderTextView.setText(pet.getGender());
            mAgeTextView.setText(String.valueOf(pet.getAge()));
            mTypeTextView.setText(pet.getType());
            Picasso.get().load(pet.getImageUrl()).into(mImageView);
        }
    }

}
