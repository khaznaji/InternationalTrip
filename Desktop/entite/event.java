/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.sql.Date;

/**
 *
 * @author Gharb
 */
public class event {
     private int idEvmt;
    private String nom;
    private String type;
    private String image;

    private int nbr_place;
    private Date date;
     private int prix;

    public event() {
    }

    public event(String nom, String type, String image, int nbr_place, Date date, int prix) {
        this.nom = nom;
        this.type = type;
        this.image = image;
        this.nbr_place = nbr_place;
        this.date = date;
        this.prix = prix;
    }

    public event(int idEvmt, String nom, String type, String image, int nbr_place, Date date, int prix) {
        this.idEvmt = idEvmt;
        this.nom = nom;
        this.type = type;
        this.image = image;
        this.nbr_place = nbr_place;
        this.date = date;
        this.prix = prix;
    }

    public int getIdEvmt() {
        return idEvmt;
    }

    public void setIdEvmt(int idEvmt) {
        this.idEvmt = idEvmt;
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

    public int getNbr_place() {
        return nbr_place;
    }

    public void setNbr_place(int nbr_place) {
        this.nbr_place = nbr_place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "event{" + "idEvmt=" + idEvmt + ", nom=" + nom + ", type=" + type + ", image=" + image + ", nbr_place=" + nbr_place + ", date=" + date + ", prix=" + prix + '}';
    }
    
    

    
    

    
    
    
}
