package com.newpidev.entities;

import com.newpidev.entities.User;
import com.newpidev.utils.Statics;

public class Reservation implements Comparable<Reservation> {

    private int id;
    private Evenement evenement;
    private User user;
    private String typePaiement;
    private int nbrPlace;
    private float prix;
    private float prixI;

    public Reservation() {
    }

    public Reservation(int id, Evenement evenement, User user, String typePaiement, int nbrPlace, float prix, float prixI) {
        this.id = id;
        this.evenement = evenement;
        this.user = user;
        this.typePaiement = typePaiement;
        this.nbrPlace = nbrPlace;
        this.prix = prix;
        this.prixI = prixI;
    }

    public Reservation(Evenement evenement, User user, String typePaiement, int nbrPlace, float prix, float prixI) {
        this.evenement = evenement;
        this.user = user;
        this.typePaiement = typePaiement;
        this.nbrPlace = nbrPlace;
        this.prix = prix;
        this.prixI = prixI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrixI() {
        return prixI;
    }

    public void setPrixI(float prixI) {
        this.prixI = prixI;
    }


    @Override
    public int compareTo(Reservation reservation) {
        switch (Statics.compareVar) {
            case "Evenement":
                return this.getEvenement().getNom().compareTo(reservation.getEvenement().getNom());
            case "User":
                return this.getUser().getNom().compareTo(reservation.getUser().getNom());
            case "TypePaiement":
                return this.getTypePaiement().compareTo(reservation.getTypePaiement());
            case "NbrPlace":
                return Integer.compare(this.getNbrPlace(), reservation.getNbrPlace());
            case "Prix":
                return Float.compare(this.getPrix(), reservation.getPrix());
            case "PrixI":
                return Float.compare(this.getPrixI(), reservation.getPrixI());

            default:
                return 0;
        }
    }

}