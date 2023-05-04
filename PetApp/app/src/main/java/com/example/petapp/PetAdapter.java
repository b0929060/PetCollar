package com.example.petapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    private ArrayList<Pet> pets;

    public PetAdapter(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public static class PetViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvGender;
        private TextView tvAge;
        private TextView tvType;
        private ImageView ivImage;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvType = itemView.findViewById(R.id.tvType);
            ivImage = itemView.findViewById(R.id.ivImage);
            };
        }
    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {

        Pet pet = pets.get(position);

        holder.tvName.setText(pet.getName());
        holder.tvGender.setText(pet.getGender());
        holder.tvAge.setText(String.valueOf(pet.getAge()));
        holder.tvType.setText(pet.getType());

        // 使用 Picasso 或 Glide 庫載入圖片
        Picasso.get().load(pet.getImageUrl()).into(holder.ivImage);


        // 設定點擊事件，跳轉到 PetDetailActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PetDetailActivity.class);
                intent.putExtra("pet", pet);
                v.getContext().startActivity(intent);
            }
        });
        }
        @Override
        public int getItemCount () {
            return pets.size();
        }
    }
