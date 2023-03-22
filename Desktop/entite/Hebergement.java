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
public class Hebergement {
    private int id;
          private Pays p  ;
   private String titre ;
       private String type ;
          private int prix ;
   private String image ;
   private String adresse ;
   private String periode ;
   private String choix ;
      private Date date_h ;

    public Hebergement() {
    }

    public Hebergement(int id, Pays p, String titre, String type, int prix, String image, String adresse, String periode, String choix, Date date_h) {
        this.id = id;
        this.p = p;
        this.titre = titre;
        this.type = type;
        this.prix = prix;
        this.image = image;
        this.adresse = adresse;
        this.periode = periode;
        this.choix = choix;
        this.date_h = date_h;
    }

    public Hebergement(Pays p, String titre, String type, int prix, String image, String adresse, String periode, String choix, Date date_h) {
        this.p = p;
        this.titre = titre;
        this.type = type;
        this.prix = prix;
        this.image = image;
        this.adresse = adresse;
        this.periode = periode;
        this.choix = choix;
        this.date_h = date_h;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pays getP() {
        return p;
    }

    public void setP(Pays p) {
        this.p = p;
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

    public Date getDate_h() {
        return date_h;
    }

    public void setDate_h(Date date_h) {
        this.date_h = date_h;
    }

    @Override
    public String toString() {
        return "Hebergement{" + "id=" + id + ", p=" + p + ", titre=" + titre + ", type=" + type + ", prix=" + prix + ", image=" + image + ", adresse=" + adresse + ", periode=" + periode + ", choix=" + choix + ", date_h=" + date_h + '}';
    }

  

  
}
