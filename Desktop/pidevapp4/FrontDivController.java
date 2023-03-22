/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.FavServices;
import entite.Div;
import entite.Like;
import entite.Types;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class FrontDivController implements Initializable {
    private final Connection myConnex = MyConnexion.getInstanceConnex().getConnection();

    @FXML
    private TableView<Div> tvdiv;
    @FXML
    private TableColumn<Div, String> colnom;
    @FXML
    private TableColumn<Div, String> coltype;
    @FXML
    private TableColumn<Div, String> coladresse;
    @FXML
    private TableColumn<Div, Integer> colnum;
    @FXML
    private TableView<Like> tvfav;
    @FXML
    private TableColumn<Like, String> nomfav;
    @FXML
    private TableColumn<Like, Integer> numfav;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
     public ObservableList<Div> setVisible()
    { 
       

           try {
               ObservableList<Div> obl =FXCollections.observableArrayList();
                 PreparedStatement pt= myConnex.prepareStatement("select  types_id,nom,numtel,adresse from `div` ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                         Types o = new Types(rs.getInt("o.id"),rs.getString("o.types"),rs.getString("o.image"));

                


                  
                 Div d = new Div();
                 //d.setIddiv(rs.getInt("iddiv"));
                                                              d.setP(o);

                 d.setNom(rs.getString("nom"));
                 d.setAdresse(rs.getString("adresse"));
                 d.setNumtel(rs.getInt("numtel"));
               
             

                  
                  System.out.println("");
         obl.add(d);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    }
      public void afficheliste() {
        
           
                         
      
      //colid.setCellValueFactory(new PropertyValueFactory<>("iddiv"));
      coltype.setCellValueFactory(new PropertyValueFactory<>("types_id"));
      colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
      
      colnum.setCellValueFactory(new PropertyValueFactory<>("numtel"));
      coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
      
      ObservableList<Div> obl =FXCollections.observableArrayList();
     
     obl=setVisible(); 
      tvdiv.setItems(obl);
      System.out.println(""+obl);

                      
    }

    @FXML
    private void Afficher(MouseEvent event) {
       int index=-1; 
        index=tvdiv.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
    }
     public ObservableList<Like> setVisible1()
    { 
       

           try {
               ObservableList<Like> obl =FXCollections.observableArrayList();
                 PreparedStatement pt= myConnex.prepareStatement("select nom,id from `div` ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  
                 Like l = new Like();
                
                  l.setId(rs.getInt("id"));
                 l.setNom(rs.getString("nom"));
                
                
               
             

                  
                  System.out.println("");
         obl.add(l);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    }
     //affiche liste fav
      public void affichelistefav() {
        
           
                         
      
     
      nomfav.setCellValueFactory(new PropertyValueFactory<>("nom"));
      numfav.setCellValueFactory(new PropertyValueFactory<>("id"));
      ObservableList<Like> obl =FXCollections.observableArrayList();
     
     obl=setVisible1(); 
      tvfav.setItems(obl);
      System.out.println(""+obl);

                      
    }

    @FXML
    private void Afficherfav(MouseEvent event) {
         int index=-1; 
        index=tvfav.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
    }

    @FXML
    private void ajouterfav(MouseEvent event) {
          FavServices fs = new FavServices();
        Like l = new Like();
        int index=-1; 
        index=tvdiv.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
       
        l.setNom(colnom.getCellData(index));
       
        l.setId(colnum.getCellData(index));
 
         

        fs.ajouterFav(l);
        affichelistefav();  
    }

    @FXML
    private void supprimerfav(MouseEvent event) {
         FavServices fs = new FavServices();
        Like l = new Like();
        l.setNom(nomfav.getText());
        fs.supprimerFav(l);
        affichelistefav(); 
    }

    @FXML
    private void GoVoyage(ActionEvent event) {
    }

    @FXML
    private void GoHotel(ActionEvent event) {
    }

    @FXML
    private void GoDivertissement(ActionEvent event) {
    }

    @FXML
    private void GoResto(ActionEvent event) {
    }

    @FXML
    private void GoLocation(ActionEvent event) {
    }

    @FXML
    private void GoChauffeur(ActionEvent event) {
    }

    @FXML
    private void GoEvent(ActionEvent event) {
    }
    
}
