/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.sql.Date;

/**
 *
 * @author DELL
 */
public class Location {
      private int id;
    private String model;
    private  Date dateloc;
    private int duree;
      private Chauffeur chauff  ;

    public Location() {
    }

    public Location(int id, String model, Date dateloc, int duree, Chauffeur chauff) {
        this.id = id;
        this.model = model;
        this.dateloc = dateloc;
        this.duree = duree;
        this.chauff = chauff;
    }

    public Location(String model, Date dateloc, int duree, Chauffeur chauff) {
        this.model = model;
        this.dateloc = dateloc;
        this.duree = duree;
        this.chauff = chauff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Chauffeur getChauff() {
        return chauff;
    }

    public void setChauff(Chauffeur chauff) {
        this.chauff = chauff;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", model=" + model + ", dateloc=" + dateloc + ", duree=" + duree + ", chauff=" + chauff + '}';
    }

    
}
