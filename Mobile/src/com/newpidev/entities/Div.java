package com.newpidev.entities;

public class Div {

    private int id;
    private Types types;
    private String nom;
    private int numtel;
    private String adresse;

    public Div() {
    }

    public Div(int id, Types types, String nom, int numtel, String adresse) {
        this.id = id;
        this.types = types;
        this.nom = nom;
        this.numtel = numtel;
        this.adresse = adresse;
    }

    public Div(Types types, String nom, int numtel, String adresse) {
        this.types = types;
        this.nom = nom;
        this.numtel = numtel;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


}