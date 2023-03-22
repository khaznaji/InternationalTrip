/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entite.event;
import utils.MyConnexion;


/**
 *
 * @author chams
 */
public class EvenementServices {

    Connection connx ;
     Statement ste;
     private Statement st;
    public EvenementServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
    public void Ajouterevent(event e) {
         try {
            String req = "INSERT INTO evenement (nom,type,image,nbr_place,date,prix) VALUES (?,?,?,?,?,?)";

            PreparedStatement st = connx.prepareStatement(req);
           // st.setInt(1, e.getId_event());
            st.setString(1, e.getNom());
            //st.setString(6, u.getSalt());
            st.setString(2, e.getType());
            //st.setDate(8, u.getLast_login());
            //st.setDate(10, u.getPassword_requested_at());
            st.setString(3, e.getImage());
             st.setInt(4, e.getNbr_place());
            st.setDate(5, e.getDate());
            st.setInt(6, e.getPrix());
            
            

            st.executeUpdate();
            System.out.println("evenement ajouté !!");

        } catch (SQLException ex) {
            Logger.getLogger(EvenementServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ModiferEvenement(int id, event e) {
        try {
            System.out.println("-------"+e.getIdEvmt());
            String req = "UPDATE evenement SET idEvmt = ?, nom= ?, type=?, image=?,nbr_place=?,date=?,prix=?  " 
                    + " WHERE idEvmt = " + id + ";";
            PreparedStatement st = connx.prepareStatement(req);
            st.setInt(1, e.getIdEvmt());
            st.setString(2, e.getNom());
            st.setString(3, e.getType());
            st.setString(4, e.getImage());
            st.setInt(5, e.getNbr_place());
            st.setDate(6, e.getDate());
            st.setInt(7, e.getPrix());
           
            
            st.executeUpdate();
            System.out.println("evenement modilfé !!");

        } catch (SQLException ex) {
            Logger.getLogger(EvenementServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void SupprimerEvenement(int id) {
        try {
            String req = "DELETE FROM evenement WHERE evenement.`idEvmt` = ? ";
            PreparedStatement st = connx.prepareStatement(req);
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("evenement supprimé !!");

        } catch (SQLException ex) {
            Logger.getLogger(EvenementServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public List<event> listeventid() {
        List<event> eventList = new ArrayList<>();
        try {
            String requete = "select * from evenement";
             st = connx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                event a = new event();
                a.setIdEvmt(rs.getInt(1));
                a.setNom(rs.getString(2));
                a.setType(rs.getString(3));
                a.setImage(rs.getString(4));
                a.setNbr_place(rs.getInt(5));
                 a.setDate(rs.getDate(6));
                 a.setPrix(rs.getInt(7));
                
               

                eventList.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return eventList;
    }
   public event AfficherEvenement(String nom) {
        List<event> list = new ArrayList<>();
event e = new event(); 
        try {
            String requete = "Select * from evenement where evenement.`nom`='"+nom+"'";
            PreparedStatement pst = connx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) 
            {
                 e.setIdEvmt(rs.getInt("idEvmt"));
                        e.setNom(rs.getString("nom"));
                        e.setType(rs.getString("type"));
                        e.setImage(rs.getString("image"));
                        e.setNbr_place(rs.getInt("nbr_place"));
                        e.setDate(rs.getDate("date"));
                        e.setPrix(rs.getInt("prix"));
                     
                        
                        

                               
            }
        } catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
        }

        return e;
    }
   public List<event> trierevenementDate() {
        ArrayList<event> listAbonnementTypeX = new ArrayList<>();
        try {
          String req = "Select * from evenement";
            PreparedStatement st = connx.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                event a = new event();
                a.setNom(rs.getString(2));
                a.setType(rs.getString(3));
                a.setImage(rs.getString(4));
                a.setNbr_place(rs.getInt(5));
                a.setDate(rs.getDate(6));
                a.setPrix(rs.getInt(7));
               
                listAbonnementTypeX.add(a);
                Collections.sort(listAbonnementTypeX,evenementComparatorDate);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if  (listAbonnementTypeX.isEmpty()) {
            System.out.println("Liste evenement Vide");
        }
        return listAbonnementTypeX;
    }
public static Comparator<event> evenementComparatorDate = (event s1, event s2) -> {
        Date d1 = s1.getDate();
        Date d2 = s2.getDate();
        return d1.compareTo(d2);
    };
public List<event> AfficherUser(String nom) {
        List<event> listu = new ArrayList<>();
 
        try {
            String requete = "Select * from evenement where evenement.`nom`='"+nom+"'";
            PreparedStatement pst = connx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) 
            {
                 event u = new event(); 
                        u.setNom(rs.getString("nom"));
                        u.setType(rs.getString("type"));
                        u.setImage(rs.getString("image"));
                        u.setNbr_place(rs.getInt("nbr_place"));
                        u.setDate(rs.getDate("date"));
                        u.setPrix(rs.getInt("prix"));
                        
                        listu.add(u);
            }
        } catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
        }

        return listu;
    }

    
}




    

    

    
    
    
    
    

