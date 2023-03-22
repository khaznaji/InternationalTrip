package com.newpidev.entities;

import java.util.Date;
import com.newpidev.utils.*;

public class Hebergement implements Comparable<Hebergement> {
    
    private int id;
    private Pays pays;
     private String titre;
     private String type;
     private int prix;
     private String image;
     private String adresse;
     private String periode;
     private String choix;
     private Date dateH;
    
    public Hebergement() {}

    public Hebergement(int id, Pays pays, String titre, String type, int prix, String image, String adresse, String periode, String choix, Date dateH) {
        this.id = id;
        this.pays = pays;
        this.titre = titre;
        this.type = type;
        this.prix = prix;
        this.image = image;
        this.adresse = adresse;
        this.periode = periode;
        this.choix = choix;
        this.dateH = dateH;
    }

    public Hebergement(Pays pays, String titre, String type, int prix, String image, String adresse, String periode, String choix, Date dateH) {
        this.pays = pays;
        this.titre = titre;
        this.type = type;
        this.prix = prix;
        this.image = image;
        this.adresse = adresse;
        this.periode = periode;
        this.choix = choix;
        this.dateH = dateH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }
    
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
    
    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }
    
    public Date getDateH() {
        return dateH;
    }

    public void setDateH(Date dateH) {
        this.dateH = dateH;
    }
    
    
    @Override
    public String toString() {
        return "Hebergement : " +
                "id=" + id
                 + ", Pays=" + pays
                 + ", Titre=" + titre
                 + ", Type=" + type
                 + ", Prix=" + prix
                 + ", Image=" + image
                 + ", Adresse=" + adresse
                 + ", Periode=" + periode
                 + ", Choix=" + choix
                 + ", DateH=" + dateH
                ;
    }
    
    @Override
    public int compareTo(Hebergement hebergement) {
        switch (Statics.compareVar) {
            case "Pays":
                return this.getPays().getPays().compareTo(hebergement.getPays().getPays());
            case "Titre":
                 return this.getTitre().compareTo(hebergement.getTitre());
            case "Type":
                 return this.getType().compareTo(hebergement.getType());
            case "Prix":
                return Integer.compare(this.getPrix(), hebergement.getPrix());
            case "Image":
                 return this.getImage().compareTo(hebergement.getImage());
            case "Adresse":
                 return this.getAdresse().compareTo(hebergement.getAdresse());
            case "Periode":
                 return this.getPeriode().compareTo(hebergement.getPeriode());
            case "Choix":
                 return this.getChoix().compareTo(hebergement.getChoix());
            case "DateH":
                DateUtils.compareDates(this.getDateH(), hebergement.getDateH());
            
            default:
                return 0;
        }
    }
    
}