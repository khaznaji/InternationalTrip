/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import entite.Mailing;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class RepondreReclamationController implements Initializable {

    @FXML
    private TextField sujetTextField;
    @FXML
    private TextArea messageTextField;
    @FXML
    private TextField emailTXFLD;
    @FXML
    private Button envoyerMessageButton;
    @FXML
    private Button envoyerMessageButton1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         sujetTextField.setText("Reponse Reclamation");
        messageTextField.setText("Bonjour Mr/Mme ,votre réclamation sera traitée on vous contacteras ultérieurement , Merci de nous avoir contactés ,");
        
    }    

    @FXML
    private void envoyerMessage(ActionEvent event) {
          String to;
     if (emailTXFLD.getText().equals(""))
          to = AffichageReclamationController.selectionedReclamation.getEmail().trim();
        else
            to=emailTXFLD.getText();
           
         
            String subject = sujetTextField.getText();
            String message = messageTextField.getText();
            String usermail = "oumaymalyna.khaznaji@esprit.tn";
            String passmail = "191JFT2725";

            Mailing.send(to, subject, message, usermail, passmail);
            
            Notifications n = Notifications.create()
                    .title("succès")
                    .text("Email envoyé avec succès")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(5));
            n.showWarning();
    }

    @FXML
    private void gorep(ActionEvent event)  throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/AffichageReclamation.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
