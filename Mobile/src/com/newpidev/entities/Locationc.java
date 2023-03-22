package com.newpidev.entities;

import java.util.Date;
import com.newpidev.utils.*;

public class Locationc {
    
    private int id;
    private Chauffeur chauffeur;
     private String model;
     private Date dateloc;
     private int duree;
    
    public Locationc() {}

    public Locationc(int id, Chauffeur chauffeur, String model, Date dateloc, int duree) {
        this.id = id;
        this.chauffeur = chauffeur;
        this.model = model;
        this.dateloc = dateloc;
        this.duree = duree;
    }

    public Locationc(Chauffeur chauffeur, String model, Date dateloc, int duree) {
        this.chauffeur = chauffeur;
        this.model = model;
        this.dateloc = dateloc;
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }
    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public Date getDateloc() {
        return dateloc;
    }

    public void setDateloc(Date dateloc) {
        this.dateloc = dateloc;
    }
    
    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
    
    
    
}