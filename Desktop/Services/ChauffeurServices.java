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
import entite.Chauffeur;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class ChauffeurServices implements IChauffeurServices{
    
 Connection cnx = MyConnexion.getInstanceConnex().getConnection();
    @Override
    public void AjouterChauffeur(Chauffeur c) {
        try {
             String req = "INSERT INTO chauffeur (nom,prenom ,sexe, num,disponibilite ) VALUES ('" + c.getNom() + "','" + c.getPrenom() + "','" + c.getSexe() + "','" + c.getNum() + "','" + c.getDisponibilite() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("chauffeur ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    @Override
          public void SupprimerChauffeur(String nom){
       
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
       String requete = "delete from chauffeur where nom=?";
        try {
          PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1,nom);
            ps.executeUpdate();
            System.out.println("Suppression effectuée avec succès");
        } catch (SQLException ex) {
           Logger.getLogger(ChauffeurServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la suppression "+ex.getMessage());
        }
    }
    

@Override
   public void ModifierChauffeur(Chauffeur c, String nom) {
        	 
		 try{
            
        String req2 = "UPDATE `chauffeur` SET `prenom`=?,`sexe`=?,`num`=?,`disponibilite`=?  WHERE nom=? ";
                PreparedStatement st = cnx.prepareStatement(req2);
		
           
            st.setString(1, c.getPrenom());
            st.setString(2,c.getSexe());
             st.setInt(3,c.getNum());
             st.setString(4,c.getDisponibilite());

            st.setString(5,nom);
                st.executeUpdate();
                
                 System.out.println("chauffeur mis à jour avec succès !");
                 System.out.println(c.toString());
        }catch (SQLException ex) {
            System.out.println(c.toString());
            System.out.println("erreur lors de la modification " + ex.getMessage());
            Logger.getLogger(ChauffeurServices.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Chauffeur> afficher() {
        List<Chauffeur> list = new ArrayList<>();
        
        try {
            String req = "SELECT nom , prenom, sexe, num, disponibilite FROM chauffeur";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                list.add(new Chauffeur( rs.getString("nom"),rs.getString("prenom"),rs.getString("sexe"),rs.getInt("num"),rs.getString("disponibilite")));
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
   public int modifier (Chauffeur c){
String sq13="UPDATE `chauffeur` SET `nom`=?,`prenom`=?,`sexe`=?,`num`=?,`disponibilite`=?WHERE nom =?";
            
        try {
            PreparedStatement pst = cnx.prepareStatement(sq13);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
                        pst.setString(3, c.getSexe());

            pst.setInt(4, c.getNum());
                        pst.setString(5, c.getDisponibilite());

                        pst.setString(6, c.getNom());

            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ChauffeurServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }
   public List<Chauffeur> ListClasse1(String c ) {
        List<Chauffeur> Mylist = new ArrayList<>();
        try {
            String requete = "select nom,prenom,sexe,num,disponibilite from chauffeur where nom LIKE ? ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, c);
      ResultSet e = pst.executeQuery();
            while (e.next()) {
                Chauffeur pre = new Chauffeur();
              
            pre.setNom(e.getString("nom"));
            pre.setPrenom(e.getString("prenom"));
            pre.setSexe(e.getString("sexe"));
            pre.setNum(e.getInt("num"));
            pre.setDisponibilite(e.getString("disponibilite"));
            
                Mylist.add(pre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
    
}
