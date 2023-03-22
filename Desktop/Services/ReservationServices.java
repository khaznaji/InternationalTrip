/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Services.EvenementServices;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import entite.event;
import entite.reservation;
import utils.MyConnexion;

/**
 *
 * @author Gharb
 */
public class ReservationServices {
      Connection connx ;
     Statement ste;
     private Statement st;
    public ReservationServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
    public void Ajouterreservation(reservation e) {
        try {
            String req = "INSERT INTO reservation (type_paiement,nbr_place,prix) VALUES (?,?,?)";

            PreparedStatement st = connx.prepareStatement(req);
            st.setString(1, e.getType_paiement());
            st.setInt(2, e.getNbr_place());
             st.setInt(3, e.getPrix());
           
           

            st.executeUpdate();
            System.out.println("reservation ajouté !!");

        } catch (SQLException ex) {
            Logger.getLogger(EvenementServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public void Supprimerreservation(int id) {
        try {
            String req = "DELETE FROM reservation WHERE reservation.`idReservation` = ? ";
            PreparedStatement st = connx.prepareStatement(req);
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("reservation supprimé !!");

        } catch (SQLException ex) {
            Logger.getLogger(EvenementServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 public void ModiferReservation(int id, reservation e) {
        try {
            System.out.println("-------"+e.getIdReservation());
            String req = "UPDATE reservation SET  type_paiement= ?,nbr_place=?,prix=?  " 
                    + " WHERE idReservation = " + id + ";";
            PreparedStatement st = connx.prepareStatement(req);
            st.setString(1, e.getType_paiement());
            st.setInt(2, e.getNbr_place());
           
            st.setInt(3, e.getPrix());
            
            
            st.executeUpdate();
            System.out.println("reservation modilfé !!");

        } catch (SQLException ex) {
            Logger.getLogger(ReservationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 public List<reservation> listparid() {
        List<reservation> eventList = new ArrayList<>();
        try {
            String requete = "select * from reservation";
             st = connx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                reservation a = new reservation();
                a.setType_paiement(rs.getString(1));
                a.setNbr_place(rs.getInt(2));
                
                 a.setPrix(rs.getInt(3));
                
               

                eventList.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return eventList;
    }
    
}
