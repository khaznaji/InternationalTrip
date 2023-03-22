package com.newpidev.entities;

import java.util.Date;

public class Evenement {

    private int id;
    private String nom;
    private String type;
    private String image;
    private int nbrPlace;
    private Date date;
    private float prix;

    public Evenement() {
    }

    public Evenement(int id, String nom, String type, String image, int nbrPlace, Date date, float prix) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.image = image;
        this.nbrPlace = nbrPlace;
        this.date = date;
        this.prix = prix;
    }

    public Evenement(String nom, String type, String image, int nbrPlace, Date date, float prix) {
        this.nom = nom;
        this.type = type;
        this.image = image;
        this.nbrPlace = nbrPlace;
        this.date = date;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }


}