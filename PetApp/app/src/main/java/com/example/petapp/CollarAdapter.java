package com.example.petapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CollarAdapter extends RecyclerView.Adapter<CollarAdapter.CollarViewHolder> {

    private List<Collar> collars;
    private OnPairClickListener pairClickListener;
    private OnUnpairClickListener unpairClickListener;

    public CollarAdapter(List<Collar> collars) {
        this.collars = collars;
    }

    public void setOnPairClickListener(OnPairClickListener listener) {
        this.pairClickListener = listener;
    }

    public void setOnUnpairClickListener(OnUnpairClickListener listener) {
        this.unpairClickListener = listener;
    }

    @NonNull
    @Override
    public CollarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collar, parent, false);
        return new CollarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Collar collar = collars.get(position);

        holder.tvCollarId.setText(String.valueOf(collar.getCollarId()));
        holder.tvPetName.setText(collar.getPet_name());

        Glide.with(holder.itemView.getContext())
                .load(collar.getImageUrl())
                .centerCrop()
                .into(holder.ivImage);

        if (collar.getPetId().equals("unpaired")) {
            holder.buttonPair.setVisibility(View.VISIBLE);
            holder.buttonUnpair.setVisibility(View.GONE);
            holder.buttonPair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pairClickListener != null) {
                        pairClickListener.onPairClick(position);
                    }
                }
            });
        } else {
            holder.buttonPair.setVisibility(View.GONE);
            holder.buttonUnpair.setVisibility(View.VISIBLE);
            holder.buttonUnpair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (unpairClickListener != null) {
                        unpairClickListener.onUnpairClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return collars.size();
    }

    static class CollarViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvCollarId, tvPetName;
        Button buttonPair, buttonUnpair;

        public CollarViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvCollarId = itemView.findViewById(R.id.tvCollar_id);
            tvPetName = itemView.findViewById(R.id.tvPet_name);
            buttonPair = itemView.findViewById(R.id.button1);
            buttonUnpair = itemView.findViewById(R.id.button2);
        }
    }

    public interface OnPairClickListener {
        void onPairClick(int position);
    }

    public interface OnUnpairClickListener {
        void onUnpairClick(int position);
    }
}



