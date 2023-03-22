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
public class reservation {
   private int id_event;
     private int id_user;
    private int idReservation;
    private String type_paiement;
   private int nbr_place;
   private int prix;
   private int prix_i;

    public reservation(int id_event, int id_user, int idReservation, String type_paiement, int nbr_place, int prix, int prix_i) {
        this.id_event = id_event;
        this.id_user = id_user;
        this.idReservation = idReservation;
        this.type_paiement = type_paiement;
        this.nbr_place = nbr_place;
        this.prix = prix;
        this.prix_i = prix_i;
    }

    public reservation() {
    }

    public reservation(String type_paiement, int nbr_place, int prix) {
        this.type_paiement = type_paiement;
        this.nbr_place = nbr_place;
        this.prix = prix;
    }

    public reservation(int idReservation, String type_paiement, int nbr_place, int prix) {
        this.idReservation = idReservation;
        this.type_paiement = type_paiement;
        this.nbr_place = nbr_place;
        this.prix = prix;
    }

    

    

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public String getType_paiement() {
        return type_paiement;
    }

    public void setType_paiement(String type_paiement) {
        this.type_paiement = type_paiement;
    }

    public int getNbr_place() {
        return nbr_place;
    }

    public void setNbr_place(int nbr_place) {
        this.nbr_place = nbr_place;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrix_i() {
        return prix_i;
    }

    public void setPrix_i(int prix_i) {
        this.prix_i = prix_i;
    }

    @Override
    public String toString() {
        return "reservation{" + "id_event=" + id_event + ", id_user=" + id_user + ", idReservation=" + idReservation + ", type_paiement=" + type_paiement + ", nbr_place=" + nbr_place + ", prix=" + prix + ", prix_i=" + prix_i + '}';
    }
    
   

  
    
    
    
}
