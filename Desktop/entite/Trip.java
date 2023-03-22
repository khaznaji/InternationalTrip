/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.sql.Blob;

/**
 *
 * @author IHEB
 */
public class Trip {
  private int id_trip;
    private String ville_dest;
    private String description;
    private String offre;
    private String periode;

    public Trip(int id_trip, String ville_dest,  String description, String offre, String periode) {
        this.id_trip = id_trip;
        this.ville_dest = ville_dest;
        this.description = description;
        this.offre = offre;
        this.periode = periode;
    }
public Trip(String offre){
       
        this.offre=offre;
       
 
    }
   

    public Trip() {
    }

    public int getId_trip() {
        return id_trip;
    }

    public String getVille_dest() {
        return ville_dest;
    }

   
    public String getDescription() {
        return description;
    }

    public String getOffre() {
        return offre;
    }

    public String getPeriode() {
        return periode;
    }

    public void setId_trip(int id_trip) {
        this.id_trip = id_trip;
    }

    public void setVille_dest(String ville_dest) {
        this.ville_dest = ville_dest;
    }

    

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    @Override
    public String toString() {
        return "Trip{" + "id_trip=" + id_trip + ", ville_dest=" + ville_dest + ",  description=" + description + ", offre=" + offre + ", periode=" + periode + '}';
    }

 
    
}
