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
public class Div {
    private int id;
          private Types p  ;
   
       private String nom ;
          private int numtel ;
  // private String image ;
   private String adresse ;
  

    public Div() {
    }

    public Div(int id, Types p, String nom, int numtel, String adresse) {
        this.id = id;
        this.p = p;
       
        this.nom = nom;
        this.numtel = numtel;
        //this.image = image;
        this.adresse = adresse;
       
    }

    public Div(Types p, String nom, int numtel, String adresse) {
         this.p = p;
       
        this.nom = nom;
        this.numtel = numtel;
        //this.image = image;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Types getP() {
        return p;
    }

    public void setP(Types p) {
        this.p = p;
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

    @Override
    public String toString() {
        return "Div{" + "id=" + id + ", p=" + p + ", nom=" + nom + ", numtel=" + numtel + ", adresse=" + adresse + '}';
    }

    
  

  
}
