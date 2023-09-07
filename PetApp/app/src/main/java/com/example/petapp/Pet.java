package com.example.petapp;

import java.io.Serializable;

public class Pet implements Serializable {
    private String pet_id;
    private String name;
    private String gender;
    private int age;
    private String type;
    private String imageUrl;

    public Pet(String pet_id,String name, String gender, int age, String type, String imageUrl) {
        this.pet_id = pet_id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.type = type;
        this.imageUrl = imageUrl;
    }
    public String getPet_ID() {
        return pet_id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
