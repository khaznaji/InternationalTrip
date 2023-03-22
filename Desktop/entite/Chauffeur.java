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
public class Chauffeur {
      public static String getChauff() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private int id;
    private String nom;
    private String prenom;
    private String sexe;
    private int num;
    private String disponibilite;

    public Chauffeur(int id, String nom, String prenom, String sexe, int num, String disponibilite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.num = num;
        this.disponibilite = disponibilite;
    }

    public Chauffeur(String nom, String prenom, String sexe, int num, String disponibilite) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.num = num;
        this.disponibilite = disponibilite;
    }

    public Chauffeur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    

    public Chauffeur() {
    }

  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    @Override
    public String toString() {
        return "Chauffeur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", sexe=" + sexe + ", num=" + num + ", disponibilite=" + disponibilite + '}';
    }
    
    
    
}
