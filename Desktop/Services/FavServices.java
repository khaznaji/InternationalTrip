/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Like;
import utils.MyConnexion;
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

import java.util.List;


/**
 *
 * @author HP
 */
public class FavServices implements IFavServices {
     Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public FavServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
     @Override   
    public int ajouterFav(Like l) {
        int x = 0;
        try {
           String sql ="INSERT INTO `like`( `id`, `nom`) VALUES ("+l.getId()+" ,'"+l.getNom()+"' );";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;
    }
    @Override
    public int supprimerFav(Like l) {
        String sql ="Delete from `like` where `nom`= ? ";
         try {
           
            PreparedStatement ps = connx.prepareStatement(sql);
            ps.setString(1,l.getNom());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
     @Override
    public ArrayList<Like> afficherFav() {
        ArrayList<Like> Like = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `like`";
            ResultSet res = ste.executeQuery(sql1);
            
            Like l;
        while (res.next()) {
            
            l = new Like( res.getInt("id"),res.getString("`nom`"));
    Like.add(l);
    
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("affiché");
        Like.forEach((l) -> {
            System.out.println (l);
        });
return Like;
    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Like> getDataTeam() {
      
        ObservableList<Like> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from `like`");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Like(rs.getInt(1),rs.getString(2))  );            
            }
        } catch (Exception e) {
        }
        return list;
    }
     public List<Like> ListClasse() {
        List<Like> Mylist = new ArrayList<>();
        try {
            String requete = "select * from `like`";
            PreparedStatement ps = connx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Like l = new Like();
              
            l.setId(rs.getInt("id")); 
            l.setNom(rs.getString("nom"));
            
            
                Mylist.add(l);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
      public List<Like> ListClasse1(String s ) {
        List<Like> list = new ArrayList<>();
        try {
            String requete = "select * from `like` where `nom` =? ";
            PreparedStatement ps = connx.prepareStatement(requete);
            ps.setString(1, s);
      ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Like l = new Like();
              
            l.setId(rs.getInt("id"));
            l.setNom(rs.getString("nom"));
           
            
            
                list.add(l);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    
    
}
