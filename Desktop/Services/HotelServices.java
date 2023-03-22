/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Hotel;
import entite.Trip;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class HotelServices implements IHotelServices {
   Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public HotelServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
  @Override   
    public int ajouterHotel(Hotel p) {
        int x = 0;
        try {
           String sql ="INSERT INTO `hotel`( `descrip`, `prix`, `nom`, `img`) VALUES ('"+p.getDescrip()+"', '"+p.getPrix()+"', '"+p.getNom()+"', '"+p.getImg()+"');";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;
    }
         @Override
    public int supprimerHotel(Hotel p) {
        String sql ="Delete from `hotel` where nom= ? ";
         try {
           
            PreparedStatement pst = connx.prepareStatement(sql);
            pst.setString(1,p.getNom());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }


    @Override
    public int modifierHotel(Hotel p) {
        String sq13="UPDATE `hotel` SET `id_hotel`=?,`descrip`=?,`prix`=?,`nom`=?,`img`=? WHERE id_hotel =?";
            
        try {
            PreparedStatement pst = connx.prepareStatement(sq13);
            pst.setString(1, String.valueOf(p.getId_hotel()));
            pst.setString(2, p.getDescrip());
            pst.setString(3, p.getPrix());
                        pst.setString(4, p.getNom());

            pst.setString(5, p.getImg());
            pst.setString(6, String.valueOf(p.getId_hotel()));
            
            
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }
    @Override
    public ArrayList<Hotel> afficherHotel() {
        ArrayList<Hotel> Hotel = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `hotel`";
            ResultSet res = ste.executeQuery(sql1);
            
            Hotel p;
        while (res.next()) {
            
            p = new Hotel(  res.getInt("id_hotel"),res.getString("descrip"),res.getString("prix")
                    ,res.getString("nom"),res.getString("img"));
    Hotel.add(p);
    //
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connectee");
        for(Hotel p: Hotel)
       {
       	 System.out.println (p);
       }
return Hotel;
    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Hotel> getDataTeam() {
      
        ObservableList<Hotel> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from hotel");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Hotel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5))  );            
            }
        } catch (Exception e) {
        }
        return list;
    }
    @Override
    public ArrayList<Hotel> rechercherHotel(String V,String C) {
     ArrayList<Hotel> Hotel = new ArrayList<>();
     try {
         String sql1="select * from hotel where " + C + " =\""+V+"\"" ;
            
         //   String sql1="select * from evenement where titre = \""+V+"\"" ;
            System.out.println(sql1);
            
            ResultSet res = ste.executeQuery(sql1);
            Hotel p;
            
        while (res.next()) {
           p = new Hotel(  res.getInt(1),res.getString(2),res.getString(3)
                    ,res.getString(4),res.getString(5));
        // F = new Destination(res.getString("nom"),res.getString("gouvernorat"),res.getString("type"),res.getString("description"));
 
           Hotel.add(p);
            
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
     for(Hotel p: Hotel)
       {

       	 System.out.println (p);
         
      }
     
     return Hotel;
    }
      public List<Hotel> ListClasse1(String c ) {
        List<Hotel> Mylist = new ArrayList<>();
        try {
            String requete = "select * from hotel where nom LIKE ? ";
            PreparedStatement pst = connx.prepareStatement(requete);
            pst.setString(1, c);
      ResultSet e = pst.executeQuery();
            while (e.next()) {
                Hotel pre = new Hotel();
              
            pre.setId_hotel(e.getInt("id_hotel"));
            pre.setDescrip(e.getString("descrip"));
            pre.setPrix(e.getString("prix"));
            pre.setNom(e.getString("nom"));
            pre.setImg(e.getString("img"));
            
                Mylist.add(pre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
    
}
