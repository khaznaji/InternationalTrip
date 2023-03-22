/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Trip;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import entite.Trip;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnexion;
/**
 *
 * @author IHEB
 */
public class TripServices implements ITripServices {
    Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public TripServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
  @Override   
    public int ajouterTrip(Trip p) {
        int x = 0;
        try {
           String sql ="INSERT INTO `offre`( `description`, `ville_dest`, `offre`, `periode`) VALUES ('"+p.getDescription()+"', '"+p.getVille_dest()+"', '"+p.getOffre()+"', '"+p.getPeriode()+"');";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TripServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;
    }
         @Override
    public int supprimerTrip(Trip p) {
        String sql ="Delete from `offre` where offre= ? ";
         try {
           
            PreparedStatement pst = connx.prepareStatement(sql);
            pst.setString(1,p.getOffre());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TripServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }


    @Override
    public int modifierTrip(Trip p) {
        String sq13="UPDATE `offre` SET `id_trip`=?,`description`=?,`ville_dest`=?,`offre`=?,`periode`=? WHERE id_trip =?";
            
        try {
            PreparedStatement pst = connx.prepareStatement(sq13);
            pst.setString(1, String.valueOf(p.getId_trip()));
            pst.setString(2, p.getVille_dest());
            pst.setString(3, p.getDescription());
                        pst.setString(4, p.getOffre());

            pst.setString(5, p.getPeriode());
            pst.setString(6, String.valueOf(p.getId_trip()));
            
            
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TripServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
// int pid_trip =p.getId_trip();
//   
//     
//    String req = "update trip SET id_trip=?,ville_dest=?,description=?,periode=?,offre=? WHERE id_trip=?";
//   
//      try {
//         
//            pst = connx.prepareStatement(req);
//                       pst.setInt(1,pid_trip);            
//
//            pst.setString(2, p.getVille_dest());            
//            pst.setString(3, p.getDescription());
//            pst.setString(4, p.getPeriode());
//            pst.setString(5, p.getOffre());
//
//          //  pst.setInt(5, r.getId_cat());
//            pst.setInt(6,pid_trip);            
//           //   pst.setInt(3,i);
//           int rowsDeleted = pst.executeUpdate();
//           
//           
//            if (rowsDeleted > 0) {
//            System.out.println("A user was updated successfully!");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(TripServices.class.getName()).log(Level.SEVERE, null, ex);
//        }
    
    }
    @Override
    public ArrayList<Trip> afficherTrip() {
        ArrayList<Trip> Trip = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `offre`";
            ResultSet res = ste.executeQuery(sql1);
            
            Trip p;
        while (res.next()) {
            
            p = new Trip(  res.getInt("id_trip"),res.getString("description"),res.getString("ville_dest")
                    ,res.getString("offre"),res.getString("periode"));
    Trip.add(p);
    //
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(TripServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tekhdem");
        for(Trip p: Trip)
       {
       	 System.out.println (p);
       }
return Trip;
    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Trip> getDataTeam() {
      
        ObservableList<Trip> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from offre");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Trip(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5))  );            
            }
        } catch (Exception e) {
        }
        return list;
    }
    @Override
    public ArrayList<Trip> rechercherTrip(String V,String C) {
     ArrayList<Trip> Trip = new ArrayList<>();
     try {
         String sql1="select * from offre where " + C + " =\""+V+"\"" ;
            
         //   String sql1="select * from evenement where titre = \""+V+"\"" ;
            System.out.println(sql1);
            
            ResultSet res = ste.executeQuery(sql1);
            Trip p;
            
        while (res.next()) {
           p = new Trip(  res.getInt(1),res.getString(2),res.getString(3)
                    ,res.getString(4),res.getString(5));
        // F = new Destination(res.getString("nom"),res.getString("gouvernorat"),res.getString("type"),res.getString("description"));
 
           Trip.add(p);
            
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(TripServices.class.getName()).log(Level.SEVERE, null, ex);
        }
     for(Trip p: Trip)
       {

       	 System.out.println (p);
         
      }
     
     return Trip;
    }
     public List<Trip> ListClasse() {
        List<Trip> Mylist = new ArrayList<>();
        try {
            String requete = "select * from offre";
            PreparedStatement pst = connx.prepareStatement(requete);
            ResultSet e = pst.executeQuery();
            while (e.next()) {
                Trip pre = new Trip();
              
             pre.setId_trip(e.getInt("id_trip"));
            pre.setVille_dest(e.getString("ville_dest"));
            pre.setDescription(e.getString("description"));
            pre.setPeriode(e.getString("periode"));
            pre.setOffre(e.getString("offre"));
            
                Mylist.add(pre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
      public List<Trip> ListClasse1(String c ) {
        List<Trip> Mylist = new ArrayList<>();
        try {
            String requete = "select * from offre where ville_dest LIKE ? ";
            PreparedStatement pst = connx.prepareStatement(requete);
            pst.setString(1, c);
      ResultSet e = pst.executeQuery();
            while (e.next()) {
                Trip pre = new Trip();
              
            pre.setId_trip(e.getInt("id_trip"));
            pre.setVille_dest(e.getString("ville_dest"));
            pre.setDescription(e.getString("description"));
            pre.setPeriode(e.getString("periode"));
            pre.setOffre(e.getString("offre"));
            
                Mylist.add(pre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
}
