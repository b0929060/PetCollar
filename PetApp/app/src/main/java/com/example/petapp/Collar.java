package com.example.petapp;

public class Collar {
    private int collarId;
    private String petId; // 寵物綁定的項圈，可以為空
    private String pet_name;
    private String imageUrl;

    public Collar(int collarId, String petId,String pet_name, String imageUrl) {
        this.collarId = collarId;
        this.petId = petId;
        this.pet_name = pet_name;
        this.imageUrl = imageUrl;
    }

    public int getCollarId() {
        return collarId;
    }

    public String getPetId() {
        return petId;
    }
    public String getPet_name() {
        return pet_name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }
    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}