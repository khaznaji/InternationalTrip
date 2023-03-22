/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import entite.Trip;
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
public class GestionTripFrontController implements Initializable {
             private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();

    @FXML
    private TableView<Trip> tabtrip;
    @FXML
    private TableColumn<Trip, String> col_ville;
    @FXML
    private TableColumn<Trip, String> col_description;
    @FXML
    private TableColumn<Trip, String> col_offre;
    @FXML
    private TableColumn<Trip, String> col_periode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                  affichelistetrip();  

    }    
 public ObservableList<Trip> show1()
    { 
       

           try {
               ObservableList<Trip> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select  ville_dest,description,offre,periode from trip ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Trip ls = new Trip();

                 ls.setVille_dest(rs.getString("ville_dest"));
                 ls.setDescription(rs.getString("description"));
                 ls.setOffre(rs.getString("offre"));
                 ls.setPeriode(rs.getString("periode"));
               
             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    public void affichelistetrip() {
        
           
                         
      //col_id.setCellValueFactory(new PropertyValueFactory<>("id_trip"));
      col_ville.setCellValueFactory(new PropertyValueFactory<>("ville_dest"));
      col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
      col_offre.setCellValueFactory(new PropertyValueFactory<>("offre"));
      col_periode.setCellValueFactory(new PropertyValueFactory<>("periode"));
      ObservableList<Trip> obl =FXCollections.observableArrayList();
     obl=show1(); 
      tabtrip.setItems(obl);
      System.out.println(""+obl);

                      
    }
    @FXML
    private void Afficher(MouseEvent event) {
//        int index=-1; 
//        index=tabtrip.getSelectionModel().getSelectedIndex();
//        if (index<= -1)
//        {return; } 
//                txtid.setText(col_id.getCellData(index).toString());
//
//        txtVille.setText(col_ville.getCellData(index).toString());
//        cbhotel.setValue(col_description.getCellData(index).toString());
//        txtperiode.setText(col_periode.getCellData(index).toString());
//        txtOffre.setText(col_offre.getCellData(index).toString());
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
