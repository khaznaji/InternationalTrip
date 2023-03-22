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
public class Pays {
     private int id ;
     String pays ;
 public static String getP() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Pays(Pays p, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Pays(int id, String pays) {
        this.id = id;
        this.pays = pays;
    }

    public Pays() {
    }

    @Override
    public String toString() {
        return "pays{" + "id=" + id + ", pays=" + pays + '}';
    }
     
}
