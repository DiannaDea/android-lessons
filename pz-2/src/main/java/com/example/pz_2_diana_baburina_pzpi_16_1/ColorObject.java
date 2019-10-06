package com.example.pz_2_diana_baburina_pzpi_16_1;

public class ColorObject {
    private int red;
    private int green;
    private int blue;

    private static final ColorObject colorObject = new ColorObject();

    public ColorObject() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public void setColor(String color, int value) {
        switch (color) {
            case "red":
                this.red = value;
                break;
            case "green":
                this.green = value;
                break;
            case "blue":
                this.blue = value;
                break;
        }
    }

    public int[] getColor() {
        return new int[]{this.red, this.green, this.blue};
    }

    public static ColorObject getInstance() {return colorObject;}
}
