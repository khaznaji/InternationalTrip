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
import entite.Div;
import entite.Types;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class DivServices implements IDivServices{
    Connection cnx = MyConnexion.getInstanceConnex().getConnection();

    @Override
    public void AjouterDiv(Div c) {
        try {
             String req = "INSERT INTO div (types_id,nom , numtel,adresse) VALUES ('" + c.getP().getId() + "','" + c.getNom() + "','" + c.getNumtel() + "','"  + c.getAdresse() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Divertissement ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    @Override
          public void SupprimerDiv(String nom){
       

       String requete = "delete from div where nom=?";
        try {
          PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1,nom);
            ps.executeUpdate();
            System.out.println("Suppression effectuée avec succès");
        } catch (SQLException ex) {
           Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Div> afficher1() {
        List<Div> list = new ArrayList<>();
        
        try {
            String req = "SELECT * from types a , div o where o.id=a.types_id";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                
                 Div ls = new Div();
                 Types o = new Types(rs.getInt("o.id"),rs.getString("o.types"),rs.getString("o.image"));
                 
                 
                 
               
                 
                 ls.setP(o);
                 ls.setId(rs.getInt("id"));
               
                 
                 //ls.setCategorie_id(rs.getInt("categorie_id"));
                 
                             ls.setNom(rs.getString("nom"));

                                  ls.setNumtel(rs.getInt("numtel"));
                                //  ls.setImage(rs.getString("image"));
                             ls.setAdresse(rs.getString("adresse"));

                                 


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
   public int modifierH (Div c){
String sq13="UPDATE `div`SET `types_id`=?,`nom`=?,`numtel`=?,`adresse`=?  WHERE nom =?";

            
        try {
            PreparedStatement pst = cnx.prepareStatement(sq13);
             pst.setInt(1, c.getP().getId());
             pst.setString(2, c.getNom());
             

           pst.setInt(3, c.getNumtel());
                                  // pst.setString(5, c.getImage());


                        pst.setString(4, c.getAdresse());
                       
                          pst.setString(5, c.getNom());



            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }

  
   
}
