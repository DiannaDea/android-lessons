package com.example.lab_1_diana_baburina_pzpi_16_1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

enum Level {
    LOW,
    MEDIUM,
    HIGH
}

public class Note {
    private String name;
    private String description;
    private Level level;
    private Date date;
    private String image;

    public Note(String name, String description, String level, String date, String image){
        this.name = name;
        this.description = description;
        this.level = this.getLevel(level);
        this.date = this.getDate(date);
        this.image = image;
    }

    private Level getLevel(String level) {
        switch (level){
            case "1":
                return Level.HIGH;
            case "2":
                return Level.MEDIUM;
            case "3":
                return Level.LOW;
        }
        return Level.LOW;
    }

    private Date getDate(String date){
        String pattern = "dd.MM.yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date noteDate = null;

        try {
            noteDate = simpleDateFormat.parse(date);
        } catch (ParseException ex) {

        }
        return noteDate;
    }
}
