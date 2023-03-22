/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Pays;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class PaysServices implements IPaysServices{
  Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public PaysServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
    @Override
    public int ajouterPays(Pays p) {
   int x = 0;
        try {
           String sql ="INSERT INTO `pays`( `pays`) VALUES ('"+p.getPays()+"');";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PaysServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;
    }
      

    @Override
    public int supprimerPays(Pays p) {
  String sql ="Delete from `pays` where pays= ? ";
         try {
           
            PreparedStatement pst = connx.prepareStatement(sql);
            pst.setString(1,p.getPays());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PaysServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;    }

    @Override
    public int modifierPays(Pays p) {
String sq13="UPDATE `pays` SET `id`=?,`pays`=? WHERE id =?";
            
        try {
            PreparedStatement pst = connx.prepareStatement(sq13);
            pst.setString(1, String.valueOf(p.getId()));
            pst.setString(2, p.getPays());

            pst.setString(3, String.valueOf(p.getId()));
            
            
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(PaysServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;    }

    @Override
    public ArrayList<Pays> afficherPays() {
  ArrayList<Pays> Pays = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `pays`";
            ResultSet res = ste.executeQuery(sql1);
            
            Pays p;
        while (res.next()) {
            
            p = new Pays(  res.getInt("id"),res.getString("pays"));
    Pays.add(p);
    //
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(PaysServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tekhdem");
        for(Pays p: Pays)
       {
       	 System.out.println (p);
       }
return Pays;    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Pays> getDataTeam() {
ObservableList<Pays> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from pays");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Pays(rs.getInt(1),rs.getString(2))  );            
            }
        } catch (Exception e) {
        }
        return list;    }

    @Override
    public ArrayList<Pays> rechercherPays(String V, String C) {
 ArrayList<Pays> Pays = new ArrayList<>();
     try {
         String sql1="select * from pays where " + C + " =\""+V+"\"" ;
            
         //   String sql1="select * from evenement where titre = \""+V+"\"" ;
            System.out.println(sql1);
            
            ResultSet res = ste.executeQuery(sql1);
            Pays p;
            
        while (res.next()) {
           p = new Pays(  res.getInt(1),res.getString(2));
        // F = new Destination(res.getString("nom"),res.getString("gouvernorat"),res.getString("type"),res.getString("description"));
 
           Pays.add(p);
            
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(PaysServices.class.getName()).log(Level.SEVERE, null, ex);
        }
     for(Pays p: Pays)
       {

       	 System.out.println (p);
         
      }
     
     return Pays;    }
    
}
