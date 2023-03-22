package com.newpidev.entities;

public class Types {

    private int id;
    private String image;
    private String types;

    public Types() {
    }

    public Types(int id, String image, String types) {
        this.id = id;
        this.image = image;
        this.types = types;
    }

    public Types(String image, String types) {
        this.image = image;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }


}