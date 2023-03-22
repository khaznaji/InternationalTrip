package com.newpidev.entities;

public class Like {

    private int id;
    private Div div;

    public Like() {
    }

    public Like(int id, Div div) {
        this.id = id;
        this.div = div;
    }

    public Like(Div div) {
        this.div = div;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Div getDiv() {
        return div;
    }

    public void setDiv(Div div) {
        this.div = div;
    }


}