/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Hotel;
import entite.Reclamation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class ReclamationServices implements IReclamationServices  {
    
      Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public ReclamationServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }

    @Override
    public int ajouterReclamation(Reclamation r) {
   int x = 0;
        try {
           String sql ="INSERT INTO `reclamation`( `id`, `numero_mobile`, `type`, `description`,`objet`, `nom`, `prenom`, `email`, `screenshot`, `id_trip`,`id_hotel`) VALUES ('"+r.getId()+"', '"+r.getNumero_mobile()+"', '"+r.getType()+"', '"+r.getDescription()+"','"+r.getObjet()+"', '"+r.getNom()+"', '"+r.getPrenom()+"', '"+r.getEmail()+"', '"+r.getScreenshot()+"','"+r.getId_trip()+"','"+r.getId_hotel()+"');";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;    }
    
        // LES var declaré dans le try ne sont vue que dans le try, et inversement pour en dhors du try
           
 public int supprimerReclamation(Reclamation p) {
        String sql ="Delete from `reclamation` where objet= ? ";
         try {
           
            PreparedStatement pst = connx.prepareStatement(sql);
            pst.setString(1,p.getObjet());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

   
    public int modifierReclamation(Reclamation p) {
 String sq13="UPDATE `reclamation` SET `id`=?,`numero_mobile`=?,`type`=?,`description`=?,`objet`=?,`nom`=?,`prenom`=?,`email`=?,`screenshot`=?,`id_trip`=?,`id_hotel`=? WHERE id =?";
            
        try {
            PreparedStatement pst = connx.prepareStatement(sq13);
            pst.setString(1, String.valueOf(p.getId()));
            pst.setString(2, p.getNumero_mobile());
            pst.setString(3, p.getType());
            pst.setString(4, p.getDescription());

            pst.setString(5, p.getObjet());
                        pst.setString(6, p.getNom());
            pst.setString(7, p.getPrenom());
            pst.setString(8, p.getEmail());
            pst.setString(9, p.getScreenshot());

            pst.setString(10, String.valueOf(p.getId_trip()));
                        pst.setString(11, String.valueOf(p.getId_hotel()));

                        pst.setString(12, String.valueOf(p.getId()));

            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;    }

    @Override
    public ArrayList<Reclamation> afficherReclamation() {
        ArrayList<Reclamation> Reclamation = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `reclamation`";
            ResultSet res = ste.executeQuery(sql1);
            
            Reclamation p;
        while (res.next()) {
            
            p = new Reclamation(res.getString("numero_mobile"),res.getString("type"),res.getString("description"),res.getString("objet") ,res.getString("nom"),res.getString("prenom"),res.getString("email"),res.getString("screenshot"),res.getInt("id_trip"),res.getInt("id_hotel"));
    Reclamation.add(p);
    //
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connectee");
        for(Reclamation p: Reclamation)
       {
       	 System.out.println (p);
       }
return Reclamation;
    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;   
     

   public List<Reclamation> listerReclamations() {
        List<Reclamation> myList = new ArrayList<Reclamation>();

        try { // LES var declaré dans le try ne sont vue que dans le try, et inversement pour en dhors du try
            String requete = "SELECT * from reclamation"; //MAJUSCULE NON OBLIGATOIRE 
            PreparedStatement ps;

            ps = MyConnexion.getInstanceConnex().getConnection().prepareStatement(requete);
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                Reclamation R = new Reclamation();
                R.setId(rs.getInt(1));
                R.setNumero_mobile(rs.getString(2));
                R.setType(rs.getString(3));
                R.setDescription(rs.getString(4));
                R.setObjet(rs.getString(5));
                R.setNom(rs.getString(6));
                R.setPrenom(rs.getString(7));
                R.setEmail(rs.getString(8));
                                R.setScreenshot(rs.getString(9));
                R.setId_trip(rs.getInt(10));
                R.setId_hotel(rs.getInt(11));

//             
//              R.setDate_traitement(rs.getDate(9));
              
              
//                        ImageView imagev =new ImageView(new Image("file:C:\\xampp\\htdocs\\TunisiaBonPlan2-Integration\\web\\uploads\\images\\"+rs.getString(12)));
//                        imagev.setFitHeight(100);
//                        imagev.setFitWidth(100);
               
              

//                if(rs.getDate(17)!=null) // test temps dans date
//                {System.out.println(new Date(((Timestamp)rs.getObject(17)).getTime()));
//                System.out.println((Timestamp)rs.getObject(17));
//      SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
//      System.out.println("Current Date: " + ft.format(new Date(((Timestamp)rs.getObject(17)).getTime())));
//      System.out.println("Current Date: " + ft.format(new Date(((Timestamp)rs.getObject(17)).getTime())));
//                }
//                R.setDate_disponibilite(rs.getDate(17));
                myList.add(R);

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;

    }}
