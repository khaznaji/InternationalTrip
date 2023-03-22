/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.HotelServices;
import Services.ReclamationServices;
import entite.Hotel;
import entite.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import static pidevapp4.PidevApp4.Userconnected;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AffichageReclamationController implements Initializable {
             private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();
            private Stage primaryStage;

    @FXML
    private TableView<Reclamation> TableViewReclamation;
    @FXML
    private Button btnSupprimer;
    @FXML
    private TableColumn<Reclamation, String> col_num;
    @FXML
    private TableColumn<Reclamation, String> col_type;
    @FXML
    private TableColumn<Reclamation, String> col_description;
    @FXML
    private TableColumn<Reclamation, String> col_objet;
    @FXML
    private TableColumn<Reclamation, String> col_nom;
    @FXML
    private TableColumn<Reclamation, String> col_prenom;
    @FXML
    private TableColumn<Reclamation, String> col_email;
    @FXML
    private TableColumn<Reclamation, String> col_screenshot;
    @FXML
    private TableColumn<Reclamation, Integer> col_id_trip;
    @FXML
    private TableColumn<Reclamation, Integer> col_id_hotel;
    @FXML
    private TextField tfid;
        static Reclamation selectionedReclamation;
    @FXML
    private Button rep;
    @FXML
    private Button btnSupprimer1;
    @FXML
    private Button btnSupprimer2;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                  affichelisteReclamation(); 

                   
           
              
      }
    public ObservableList<Reclamation> show1()
    {
           try {
               ObservableList<Reclamation> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select numero_mobile, type,description,objet,nom,prenom,email,screenshot,id_trip,id_hotel  from reclamation ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Reclamation ls = new Reclamation();
                 ls.setId_hotel(rs.getInt("id_hotel"));
                 ls.setId_trip(rs.getInt("id_trip"));

                 ls.setNumero_mobile(rs.getString("numero_mobile"));
                                  ls.setType(rs.getString("type"));
                                                   ls.setDescription(rs.getString("description"));

                                                   ls.setObjet(rs.getString("objet"));

                 ls.setNom(rs.getString("nom"));
                                  ls.setPrenom(rs.getString("prenom"));

                 ls.setEmail(rs.getString("email"));
                                ls.setScreenshot(rs.getString("screenshot"));

             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
          
    } 
      public void affichelisteReclamation() {
        
           
                         
      //ajouter les valeurs au tableview
      col_num.setCellValueFactory(new PropertyValueFactory<>("numero_mobile"));
      col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
      col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
      col_objet.setCellValueFactory(new PropertyValueFactory<>("objet"));
      col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                  col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
                  col_screenshot.setCellValueFactory(new PropertyValueFactory<>("screenshot"));
                   
                  col_id_trip.setCellValueFactory(new PropertyValueFactory<>("id_trip"));
                  col_id_hotel.setCellValueFactory(new PropertyValueFactory<>("id_hotel"));

      ObservableList<Reclamation> obl =FXCollections.observableArrayList();
     // tableview.setItems(null);
     obl=show1(); 
      TableViewReclamation.setItems(obl);
      System.out.println(""+obl);

                      
    }
    @FXML
    private void SupprimerReclamation(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la suppression de cet Reclamation ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {
        String tit = tfid.getText().toString();
        ReclamationServices sp = new ReclamationServices();
        Reclamation e = new Reclamation();
        e.setObjet(tit);
        sp.supprimerReclamation(e);
        
                     
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de le suppression!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("La Reclamation a été bien supprimée");
                    alert2.show();
                    affichelisteReclamation();       }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("La Reclamation n'a pas été supprimée");
            alert2.show();
    }
    
    
}

    private void GoUser(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("UserFXML.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoTrip(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionTrip.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoHotel(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionHotel.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoChauff(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionChauffeur.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoLocation(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionLocation.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoDiv(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionDiv.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoEvent(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("evenement.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoPack(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionHotel.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoResto(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionTypes.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoReclamation(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("RepondreReclamation.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void GoReservation(ActionEvent event) throws IOException {
          Stage window = primaryStage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("reservation.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    @FXML
    private void AfficherReclamation(MouseEvent event) throws IOException {
             int index=-1; 
        index=TableViewReclamation.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
                tfid.setText(col_objet.getCellData(index).toString());
    }

    @FXML
    private void Repondre(ActionEvent event) throws IOException {
             Parent root;
    try {
        Stage stage = (Stage) rep.getScene().getWindow();
        stage.close();

        root = FXMLLoader.load(getClass().getResource("RepondreReclamation.fxml"));
        stage = new Stage();
        stage.setTitle("TuneUs");
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {e.printStackTrace();}}

    @FXML
    private void logout(MouseEvent event) {
           Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Deconnexion");
        alert.setContentText("Voulez-vous vraiment Déconnecter?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        Userconnected.setId(0);
        Userconnected.setPrenom("");
        Userconnected.setNom("");
        Userconnected.setEmail("");
        Userconnected.setPassword("");
      
        FXMLLoader LOADER = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
        try {
            Parent root = LOADER.load();
            Scene sc = new Scene(root);
            LoginFXMLController cntr = LOADER.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(sc);
            window.show();
        } catch (IOException ex) {

        }
    }
    }

    @FXML
    private void GoMenu(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/Menu.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoRep(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/RepondreReclamation.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    }

