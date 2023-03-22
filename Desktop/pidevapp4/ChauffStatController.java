/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ChauffStatController implements Initializable {

    @FXML
    private PieChart pie;
    @FXML
    private Label nh_lab;
    @FXML
    private Label nf_lab;
    @FXML
    private Label ne_lab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           int nho=GestionChauffeurController.nh , nfe=GestionChauffeurController.nf, nen=GestionChauffeurController.ne ; 
        pie.setTitle("Stats");
        ObservableList <PieChart.Data> ol = FXCollections.observableArrayList(
        
        new PieChart.Data("Homme", nho),new PieChart.Data("Femme", nfe),new PieChart.Data("Enfant", nen)
                
                
           );
        nh_lab.setText(""+nho); nf_lab.setText(""+nfe); ne_lab.setText(""+nen);
        pie.setData(ol);
        // TODO
    }    

    @FXML
    private void back(ActionEvent event) {
    }

    @FXML
    private void GoMenu(ActionEvent event) 
         throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/Menu.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
