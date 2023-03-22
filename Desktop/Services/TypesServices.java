/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Types;
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
public class TypesServices implements ITypesServices{
  Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public TypesServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
    @Override
    public void ajouterTypes(Types p) {
  int x = 0;
        try {
           String req ="INSERT INTO `types`( `types`,`image`) VALUES ('"+p.getTypes()+"', '"+p.getImage()+"');";
               Statement st = connx.createStatement();
            st.executeUpdate(req);
            System.out.println("Types ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
      

    @Override
    public int supprimerTypes(Types p) {
  String sql ="Delete from `types` where types= ? ";
         try {
           
            PreparedStatement pst = connx.prepareStatement(sql);
            pst.setString(1,p.getTypes());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TypesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;    }

    @Override
    public int modifierT(Types p) {
String sq13="UPDATE `types` SET `id`=?,`types`=?,`image`=? WHERE id =?";
            
        try {
            PreparedStatement pst = connx.prepareStatement(sq13);
            pst.setString(1, String.valueOf(p.getId()));
            pst.setString(2, p.getTypes());
            pst.setString(3, p.getImage());

            pst.setString(4, String.valueOf(p.getId()));
            
            
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TypesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;    }

    @Override
    public ArrayList<Types> afficherTypes() {
  ArrayList<Types> Types = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `types`";
            ResultSet res = ste.executeQuery(sql1);
            
            Types p;
        while (res.next()) {
            
            p = new Types(  res.getInt("id"),res.getString("types"),res.getString("image"));
    Types.add(p);
    //
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(TypesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tekhdem");
        for(Types p: Types)
       {
       	 System.out.println (p);
       }
return Types;    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Types> getDataTeam() {
ObservableList<Types> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from types");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Types(rs.getInt(1),rs.getString(2),rs.getString(3))  );            
            }
        } catch (Exception e) {
        }
        return list;    }

    @Override
    public ArrayList<Types> rechercherTypes(String V, String C) {
 ArrayList<Types> Types = new ArrayList<>();
     try {
         String sql1="select * from types where " + C + " =\""+V+"\"" ;
            
         //   String sql1="select * from evenement where titre = \""+V+"\"" ;
            System.out.println(sql1);
            
            ResultSet res = ste.executeQuery(sql1);
            Types p;
            
        while (res.next()) {
           p = new Types(  res.getInt(1),res.getString(2),res.getString(3));
        // F = new Destination(res.getString("nom"),res.getString("gouvernorat"),res.getString("type"),res.getString("description"));
 
           Types.add(p);
            
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(TypesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
     for(Types p: Types)
       {

       	 System.out.println (p);
         
      }
     
     return Types;    }
    
}
