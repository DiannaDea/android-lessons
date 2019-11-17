package com.example.lab_1_diana_baburina_pzpi_16_1;

import com.google.gson.Gson;

public class Note {
    private String name;
    private String description;
    private String level;
    private String date;
    private String image;

    public Note(String name, String description, String level, String date, String image){
        this.name = name;
        this.description = description;
        this.level = level;
        this.date = date;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPriority() {
        return this.level;
    }

    public String getDate() {
        return this.date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
