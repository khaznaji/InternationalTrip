/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static pidevapp4.PidevApp4.Userconnected;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class MenuController implements Initializable {

    @FXML
    private Label username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                 username.setText(Userconnected.getPrenom()+" "+Userconnected.getNom());

    }    

    @FXML
    private void GoUser(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/UserFXML.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoPays(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionPays.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoHotel(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionHotel.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoHeb(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionHebergement.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoChauff(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionChauffeur.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoLocation(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionLocation.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoDiv(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionDiv.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoEvent(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/evenement.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoOffre(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionTrip.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoTypes(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionTypes.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoReclamation(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/AffichageReclamation.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void GoReservation(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/reservation.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void logout(MouseEvent event) throws IOException, Exception {
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
    }}
    
}
