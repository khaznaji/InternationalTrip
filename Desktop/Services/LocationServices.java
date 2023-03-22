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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import entite.Chauffeur;
import entite.Location;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class LocationServices implements ILocationServices{
    
 Connection cnx = MyConnexion.getInstanceConnex().getConnection();
    @Override
    public void AjouterLocation(Location c) {
        try {
             String req = "INSERT INTO locationc (model ,dateloc, duree,chauffeur_id ) VALUES ('" + c.getModel() + "','" + c.getDateloc() + "','" + c.getDuree() + "','" + c.getChauff().getId()+ "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("location ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    @Override
          public void SupprimerLocation(String model){
       
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
       String requete = "delete from locationc where model=?";
        try {
          PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1,model);
            ps.executeUpdate();
            System.out.println("Suppression effectuée avec succès");
        } catch (SQLException ex) {
           Logger.getLogger(LocationServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la suppression "+ex.getMessage());
        }
    }
    

@Override
   public void ModifierLocation(Location c, String model) {
        	 
		 try{
            
        String req2 = "UPDATE `locationc` SET ``dateloc`=?,`duree`=?,`chauffeur_id`=? WHERE model=? ";
                PreparedStatement st = cnx.prepareStatement(req2);
		
                       st.setDate(1, new java.sql.Date(c.getDateloc().getTime()));

             st.setInt(2,c.getDuree());
                          st.setInt(3,c.getChauff().getId());



            st.setString(4,model);
            
                st.executeUpdate();
                
                 System.out.println("location mis à jour avec succès !");
                 System.out.println(c.toString());
        }catch (SQLException ex) {
            System.out.println(c.toString());
            System.out.println("erreur lors de la modification " + ex.getMessage());
            Logger.getLogger(LocationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public List<Location> afficher1() {
        List<Location> list = new ArrayList<>();
        
        try {
           //String req = "SELECT * from chauffeur a , locationc o where o.id=a.chauffeur_id";
                      String req = "SELECT * from locationc";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
 Location ls = new Location();
              //   Chauffeur o = new Chauffeur(rs.getInt("o.id"),rs.getString("o.nom"));
                 
                 
                 
               
                 
                 ls.setId(rs.getInt("id"));
               
                 
                 //ls.setCategorie_id(rs.getInt("categorie_id"));
                 
                 ls.setModel(rs.getString("model"));
                             ls.setDateloc(rs.getDate("dateloc"));

                                  ls.setDuree(rs.getInt("duree"));
 
                                 

                                                   ///ls.setChauff(o);



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
   public int modifierL (Location c){
String sq13="UPDATE `locationc`SET `model`=?,`dateloc`=?,`duree`=?,`chauffeur_id`=? WHERE model =?";

            
        try {
            PreparedStatement pst = cnx.prepareStatement(sq13);
            pst.setString(1, c.getModel());
            pst.setDate(2, new java.sql.Date(c.getDateloc().getTime()));

            pst.setInt(3, c.getDuree());
                        pst.setInt(4, c.getChauff().getId());

                        pst.setString(5, c.getModel());

            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(LocationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }
    
}
