/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.HotelServices;
import Services.PaysServices;
import entite.Hotel;
import entite.Pays;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionPaysController implements Initializable {
                    private Stage primaryStage;

private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();
    Notifications n;
    @FXML
    private TextField tfpays;
    @FXML
    private TableColumn<Pays, Integer> tabid;
    @FXML
    private TableColumn<Pays, String> tabpays;
    @FXML
    private TableView<Pays> tabp;
    String erreur ; 
    @FXML
    private TextField tfid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
      affichelistePays(); 
           
  
         
    }
 public void affichelistePays() {
        
           
                         
      //ajouter les valeurs au tableview
      tabid.setCellValueFactory(new PropertyValueFactory<>("id"));
      tabpays.setCellValueFactory(new PropertyValueFactory<>("pays"));
  
      ObservableList<Pays> obl =FXCollections.observableArrayList();
     // tableview.setItems(null);
     
     obl=show1(); 
      tabp.setItems(obl);
      System.out.println(""+obl);
      
                 
    }
     public ObservableList<Pays> show1()
    { 
       

           try {
               ObservableList<Pays> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select id, pays  from pays ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Pays ls = new Pays();
                 ls.setId(rs.getInt("id"));

                 ls.setPays(rs.getString("pays"));
                
               
             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    


    @FXML
    private void AjouterPays(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider l'ajout de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {         
        PaysServices sp = new PaysServices();
        Pays e = new Pays();
        
       

        e.setPays(tfpays.getText());
  //e.setImg(tfimage.getText());
//if (controlSaisieDescription()){
//    if(controlSaisieNom()){
     
 
        sp.ajouterPays(e);
        
        clearFields();
                    Notifications n = Notifications.create()
                                .title("Succés")
                                .text("L'hotel a été ajouté")
                                .graphic(null)
                                .position(Pos.TOP_CENTER)
                                .hideAfter(Duration.seconds(3));
                        n.showInformation();
                    affichelistePays();       }else {
              Notifications n = Notifications.create()
                                .title("Erreur")
                                .text("L'hotel n'a pas été ajouté")
                                .graphic(null)
                                .position(Pos.TOP_CENTER)
                                .hideAfter(Duration.seconds(3));
                        n.showInformation();}

      }
    private Boolean testSaisie() {
        erreur = "";
       
       
      
        if (!testNom()) {
            erreur = erreur + ("Veuillez verifier votre Nom: seulement des caractères et de nombre >= 3 \n");
        }
          return testNom();
    }
    
 
      public boolean verifUserChamps() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

   
     
        tfpays.setStyle(styledefault);
            
       
 

       
       
       
         
        if (tfpays.getText().equals("")) {
            tfpays.setStyle(style);
            verif = 1;
        }
       
         else {
        }
       
        if (verif == 0) {
            return true;
        }
        Notifications n = Notifications.create()
                                .title("Erreur")
                                .text("Vérifier les champs")
                                .graphic(null)
                                .position(Pos.TOP_CENTER)
                                .hideAfter(Duration.seconds(3));
                        n.showInformation();
        
        return false;
    }
      
private void clearFields() {
        tfid.clear();
        tfpays.clear();
        
        
        
        
    }
    private Boolean testNom() {
               int nbNonChar = 0;
        for (int i = 1; i < tfpays.getText().trim().length(); i++) {
            char ch = tfpays.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && tfpays.getText().trim().length() >= 3) {
          //   nomcheckmark.setImage(new Image("C:\\Users\\DELL\\Desktop\\ImgPidev"));
            return true;
        } else {
        //    nomCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
            return false;

        }
    }

    

    @FXML
    private void ModifierPays(ActionEvent event) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la modification de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {    
        String id=tfid.getText();
        String pays=tfpays.getText();
       

        PaysServices sp = new PaysServices();
        Pays e = new Pays();
        e.setId(Integer.parseInt(id));
        e.setPays(pays);

        e.setId(Integer.parseInt(id));
        sp.modifierPays(e);
        clearFields();
                     Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de le modification!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("L'hôtel a été bien modifié");
                    alert2.show();
                    affichelistePays();       }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("L'hôtel n'a pas été modifié");
            alert2.show();}
    }

    @FXML
    private void SupprimerPays(ActionEvent event) {
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la suppression de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {
        String tit = tfpays.getText().toString();
        PaysServices sp = new PaysServices();
        Pays e = new Pays();
        e.setPays(tit);
        sp.supprimerPays(e);
        clearFields();
                     
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de le suppression!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("L'hôtel a été bien supprimé");
                    alert2.show();
                    affichelistePays();       }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("L'hôtel n'a pas été supprimé");
            alert2.show();
    }
    }
      @FXML
    private void AfficherPays(MouseEvent event) {
         int index=-1; 
        index=tabp.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
                tfid.setText(tabid.getCellData(index).toString());

        tfpays.setText(tabpays.getCellData(index).toString());
      
//        imghotel.setImage(new Image(tabimg.getCellData(index)));
//     imghotel.setFitHeight(175);
//     imghotel.setFitWidth(320);
      // imghotel.setImage(new Image(tabimg.getCellData(index).toString()));
        
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
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("GestionHebergement.fxml"));
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
    private void GoMenu(ActionEvent event)  throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/Menu.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
