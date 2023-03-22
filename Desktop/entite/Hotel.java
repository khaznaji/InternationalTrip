/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author DELL
 */
public class Hotel {
    private int id_hotel ; 
    private String descrip  ;
        private String prix  ;
        private String nom  ;
        private String img  ;

    public Hotel() {
    }

    public Hotel(int id_hotel, String descrip, String prix, String nom, String img) {
        this.id_hotel = id_hotel;
        this.descrip = descrip;
        this.prix = prix;
        this.nom = nom;
        this.img = img;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public String getDescrip() {
        return descrip;
    }

    public String getPrix() {
        return prix;
    }

    public String getNom() {
        return nom;
    }

    public String getImg() {
        return img;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Hotel{" + "id_hotel=" + id_hotel + ", descrip=" + descrip + ", prix=" + prix + ", nom=" + nom + ", img=" + img + '}';
    }

            
}
