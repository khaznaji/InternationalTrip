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
public class Reclamation {
 int id,id_trip,id_hotel;
        String description,objet,nom,prenom,email,screenshot,type,numero_mobile;

    public Reclamation() {
    }

    public Reclamation(int id, String numero_mobile, String type, String description, String objet , String nom, String prenom, String email, String screenshot, int id_trip) {
        this.id = id;
        this.description = description;
        this.objet = objet;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.screenshot = screenshot;
        this.type = type;
        this.numero_mobile = numero_mobile;
        this.id_trip = id_trip;
    }

    public Reclamation( String numero_mobile, String type, String description, String objet , String nom, String prenom, String email, String screenshot, int id_trip) {
        this.description = description;
        this.objet = objet;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.screenshot = screenshot;
        this.type = type;
        this.numero_mobile = numero_mobile;
        this.id_trip = id_trip;
    }

    public Reclamation(int id_hotel, String description, String objet, String nom, String prenom, String email, String screenshot, String type, String numero_mobile) {
        this.id_hotel = id_hotel;
        this.description = description;
        this.objet = objet;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.screenshot = screenshot;
        this.type = type;
        this.numero_mobile = numero_mobile;
    }

    public Reclamation( String description, String objet, String nom, String prenom, String email, String screenshot, String type, String numero_mobile,int id_trip, int id_hotel) {
        
        this.description = description;
        this.objet = objet;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.screenshot = screenshot;
        this.type = type;
        this.numero_mobile = numero_mobile;
        this.id_trip = id_trip;
        this.id_hotel = id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public int getId_trip() {
        return id_trip;
    }

    public void setId_trip(int id_trip) {
        this.id_trip = id_trip;
    }

 

    public void setId(int id) {
        this.id = id;
    }

    public void setNumero_mobile(String numero_mobile) {
        this.numero_mobile = numero_mobile;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getNumero_mobile() {
        return numero_mobile;
    }

    public String getDescription() {
        return description;
    }

    public String getObjet() {
        return objet;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", id_trip=" + id_trip + ", description=" + description + ", objet=" + objet + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", screenshot=" + screenshot + ", type=" + type + ", numero_mobile=" + numero_mobile + '}';
    }
  

   
  
   

}
