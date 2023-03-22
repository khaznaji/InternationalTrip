/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import entite.Types;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class FronttypeController implements Initializable {
                         private final Connection myConnex = MyConnexion.getInstanceConnex().getConnection();


    @FXML
    private TableView<Types> tvresto;
    @FXML
    private TableColumn<Types, String> colnomr;
    @FXML
    private TableColumn<Types, String> colimg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public ObservableList<Types> setVisible()
    { 
       

           try {
               ObservableList<Types> obl =FXCollections.observableArrayList();
                  
                 PreparedStatement pt= myConnex.prepareStatement("select  types,image  from `types` ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                 Types t = new Types();
                // r.setIdrest(rs.getInt("idrest"));

                 t.setTypes(rs.getString("nom"));
                 t.setImage(rs.getString("image"));
               
             

                  
                  System.out.println("");
         obl.add(t);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
     public void affichelisteResto() {
        
           
                         
      
      //colidr.setCellValueFactory(new PropertyValueFactory<>("idrest"));
      colnomr.setCellValueFactory(new PropertyValueFactory<>("types"));
     
      colimg.setCellValueFactory(new PropertyValueFactory<>("image"));
      ObservableList<Types> obl =FXCollections.observableArrayList();
     
     obl=setVisible(); 
      tvresto.setItems(obl);
      System.out.println(""+obl);

                      
    }

    private void Afficherresto(MouseEvent event) {
         int index=-1; 
        index=tvresto.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
        
        //idr.setText(colidr.getCellData(index).toString());
        colnomr.getCellData(index);
        colimg.getCellData(index);
        //imgresto.setImage(new Image(colimg.getCellData(index)));
       // imgresto.setFitHeight(100);
        //imgresto.setFitWidth(200);
    }
}
