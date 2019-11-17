package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.content.Context;

import com.google.gson.Gson;

public class Note {
    private String name;
    private String description;
    private int level;
    private String date;
    private String image;

    public Note(String name, String description, int level, String date, String image){
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

    public int getPriority() {
        return this.level;
    }

    public String getDate() {
        return this.date;
    }

    public String getImagePath() {
        return this.image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(int level) {
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

    static String transformPriorityToString(int priority, Context ctx) {
        switch (priority) {
            case 0:
                return ctx.getResources().getString(R.string.highPriority);
            case 1:
                return ctx.getResources().getString(R.string.mediumPriority);
            case 2:
                return ctx.getResources().getString(R.string.lowPriority);
        }
        return "LOW";
    }

    static int transformPriorityToInt(String priority) {
        switch (priority) {
            case "HIGH":
                return 0;
            case "MEDIUM":
                return 1;
            case "LOW":
                return 2;
        }
        return 3;
    }
}
