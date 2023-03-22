/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import entite.Hebergement;
import entite.Pays;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class HebergementServices implements IHebergementServices{
    Connection cnx = MyConnexion.getInstanceConnex().getConnection();

    @Override
    public void AjouterHebergement(Hebergement c) {
        try {
             String req = "INSERT INTO hebergement (pays_id,titre ,type, prix,image,adresse,periode,choix,date_h ) VALUES ('" + c.getP().getId() + "','" + c.getTitre() + "','" + c.getType() + "','" + c.getPrix() + "','" + c.getImage() + "','" + c.getAdresse() + "','" + c.getPeriode() + "','" + c.getChoix() + "','" + c.getDate_h() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Hebergement ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    @Override
          public void SupprimerHebergement(String titre){
       
//           try {
//            PreparedStatement statement = cnx.prepareStatement(
//    "DELETE FROM trip WHERE offre = ?"
//);
//   statement.setString(1, p.getOffre());
//    statement.executeUpdate();
////            
//          
//            System.out.println("Trip supprimé !");
//        } catch (SQLException ex) {
//           System.out.println(ex.getMessage());
// //      }
       String requete = "delete from hebergement where titre=?";
        try {
          PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1,titre);
            ps.executeUpdate();
            System.out.println("Suppression effectuée avec succès");
        } catch (SQLException ex) {
           Logger.getLogger(HebergementServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la suppression "+ex.getMessage());
        }
        afficher1();
    }
    


	
//        try {
//            String req = "UPDATE trip SET  ville_dest =? img=? ,description=? ,periode=? WHERE offre=?" ;
//           PreparedStatement pst = cnx.prepareStatement(req);
////           
//                       pst.setString(1, p.getVille_dest());
//
//            pst.setString(2, p.getImg());
//            pst.setString(3,p.getDescription());
//             pst.setString(4, p.getOffre());
//                       pst.setString(5, p.getPeriode());
//
//            pst.executeUpdate();
//            System.out.println("Trip modifié !");
//        } catch (SQLException ex) {
//           System.out.println(ex.getMessage());
//        }
//String sql = "UPDATE trip SET `ville_dest`=?,`img`=?,`description`=? ,`periode`=?  WHERE offre=" + p.getOffre();
//        PreparedStatement ste;
//        try {
//            ste = cnx.prepareStatement(sql);
//                        ste.setInt(1,p.getId_trip());
//
//            ste.setString(2,p.getVille_dest());
//            ste.setString(3, p.getImg());
//            ste.setString(4, p.getDescription());
//
//            ste.setString(5, p.getPeriode());
//
//
//            ste.executeUpdate();
//            int rowsUpdated = ste.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("La modification de la classe a été éffectuée avec succès ");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(TripServices.class.getName()).log(Level.SEVERE, null, ex);
//        }

    
        
    

    @Override
    public List<Hebergement> afficher1() {
        List<Hebergement> list = new ArrayList<>();
        
        try {
            String req = "SELECT * from pays a , hebergement o where o.id=a.pays_id";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                
                 Hebergement ls = new Hebergement();
                 Pays o = new Pays(rs.getInt("o.id"),rs.getString("o.pays"));
                 
                 
                 
               
                 
                 ls.setP(o);
                 ls.setId(rs.getInt("id"));
               
                 
                 //ls.setCategorie_id(rs.getInt("categorie_id"));
                 
                 ls.setTitre(rs.getString("titre"));
                             ls.setType(rs.getString("type"));

                                  ls.setPrix(rs.getInt("prix"));
                                  ls.setImage(rs.getString("image"));
                             ls.setAdresse(rs.getString("adresse"));

                                  ls.setPeriode(rs.getString("periode"));
                                  ls.setChoix(rs.getString("choix"));
                             ls.setDate_h(rs.getDate("date_h"));


            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
 

//    public void rechercher(String index){
//List<Trip> result = afficher().stream().filter(line -> index.equals(line.getOffre())).collect(Collectors.toList());
//                    System.out.println("----------");
//                    result.forEach(System.out::println);
////
//}
//   public void TrierParId (){
// TripServices sa = new TripServices();
//List<Trip> TrierParId = sa.afficher().stream().sorted(Comparator.comparing(Trip::getId_trip)).collect(Collectors.toList());
////                            TrierParId.forEach(System.out::println);
//}
@Override
   public int modifierH (Hebergement c){
String sq13="UPDATE `hebergement`SET `pays_id`=?,`titre`=?,`type`=?,`prix`=?,`image`=?,`adresse`=?,`periode`=?,`choix`=? ,`date_h`=?  WHERE titre =?";

            
        try {
            PreparedStatement pst = cnx.prepareStatement(sq13);
             pst.setInt(1, c.getP().getId());
             pst.setString(2, c.getTitre());
             pst.setString(3, c.getType());

           pst.setInt(4, c.getPrix());
                                   pst.setString(5, c.getImage());


                        pst.setString(6, c.getAdresse());
                         pst.setString(7, c.getPeriode());
                        pst.setString(8, c.getChoix());

                        pst.setDate(9, c.getDate_h());
                          pst.setString(10, c.getTitre());



            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(HebergementServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }

  
   
}
