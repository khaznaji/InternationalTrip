/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.TypesServices;
import entite.Types;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

//import org.controlsfx.control.Notifications;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionTypesController implements Initializable {
    private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();
                private Stage primaryStage;

    @FXML
    private TextField tfpays;
    @FXML
    private TableView<Types> tabp;
    @FXML
    private TableColumn<Types, Integer> tabid;
    @FXML
    private TableColumn<Types, String> tabpays;
    @FXML
    private TableColumn<Types, String> tabimg;
    @FXML
    private TextField tfid;
    @FXML
    private ImageView imghotel;
    @FXML
    private Button upload;
    @FXML
    private TextField tfimg;
     String erreur ;
        private String path;
         File selectedFile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imghotel.setOnDragOver(new EventHandler<DragEvent>() {
           @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
       imghotel.setOnDragDropped(new EventHandler<DragEvent>() {
           @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    path = null;
                    for (File file : db.getFiles()) {
                        path = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath()="C:\Users\X\Desktop\ScreenShot.6.png"
                        imghotel.setImage(new Image("file:" + file.getAbsolutePath()));
//                        screenshotView.setFitHeight(150);
//                        screenshotView.setFitWidth(250);
                        upload.setText(path);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        imghotel.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));
        
      affichelistePays(); 
           
    }   
    public void affichelistePays() {
        
           
                         
      //ajouter les valeurs au tableview
      tabid.setCellValueFactory(new PropertyValueFactory<>("id"));
      tabpays.setCellValueFactory(new PropertyValueFactory<>("types"));
      tabimg.setCellValueFactory((new PropertyValueFactory<>("image")));
      
  
      ObservableList<Types> obl =FXCollections.observableArrayList();
     // tableview.setItems(null);
     
     obl=show1(); 
      tabp.setItems(obl);
      System.out.println(""+obl);
      
                 
    }
     public ObservableList<Types> show1()
    { 
       

           try {
               ObservableList<Types> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select id, types,image  from types ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Types ls = new Types();
                 ls.setId(rs.getInt("id"));

                 ls.setTypes(rs.getString("types"));
                 ls.setImage(rs.getString("image"));
                
               
             

                  
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
        TypesServices sp = new TypesServices();
        Types e = new Types();
        
       

        e.setTypes(tfpays.getText());
         e.setImage(path);
  //e.setImg(tfimage.getText());
//if (controlSaisieDescription()){
//    if(controlSaisieNom()){
     
 
        sp.ajouterTypes(e);
        
        clearFields();
        }}
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
      /*  Notifications n = Notifications.create()
                                .title("Erreur")
                                .text("Vérifier les champs")
                                .graphic(null)
                                .position(Pos.TOP_CENTER)
                                .hideAfter(Duration.seconds(3));
                        n.showInformation();*/
        
        return false;
    }
      
private void clearFields() {
        tfid.clear();
        tfpays.clear();
        tfimg.clear();
        
           imghotel.setImage(null);
 upload.setText("Upload");        
                imghotel.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));
        
        
        
        
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
       // alert.setHeaderText("Voulez vous valider la modification de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {    
        String id=tfid.getText();
        String types=tfpays.getText();
        String image=tfimg.getText();
       

        TypesServices sp = new TypesServices();
        Types e = new Types();
        e.setId(Integer.parseInt(id));
        e.setTypes(types);
        e.setImage(path);

        e.setId(Integer.parseInt(id));
        sp.modifierT(e);
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
        TypesServices sp = new TypesServices();
        Types e = new Types();
        e.setTypes(tit);
        sp.supprimerTypes(e);
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
        tfimg.setText(tabimg.getCellData(index).toString());
    }
    

    @FXML
    private void UploadImage(ActionEvent event) throws MalformedURLException {
 FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            path = selectedFile.getName();
//                path = selectedFile.toURI().toURL().toExternalForm();
            imghotel.setImage(new Image(selectedFile.toURI().toURL().toString()));
            imghotel.setFitHeight(150);
            imghotel.setFitWidth(250);
            upload.setText(path);

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
