/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.HotelServices;
import Services.ReclamationServices;
import Services.TripServices;
import entite.Hotel;
import entite.Reclamation;
import entite.Trip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionReclamationFrontController implements Initializable {
    Notifications n;
String erreur;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfnumero;
    @FXML
    private TextField tfobjet;
    @FXML
    private TextArea tfdescription;
    @FXML
    private ImageView screenshot;
    @FXML
    private Button upload;
    @FXML
    private DatePicker tfdate;
    @FXML
    private ComboBox<String> tftype;
     ObservableList<String> listType = FXCollections.observableArrayList("Offre", "Hotel", "Restaurant", "Evenement","Compte", "Location Voiture", "Chauffeur", "Divertissement", "Autres");
 private String path;
    File selectedFile;
    int selectedCompteID;
        int selectedHotel;

                           int selectedBonplanID=0;
                           java.sql.Timestamp timestamp = null;
    @FXML
    private TextField tfid;
//int etatrecaptcha = 0;
//    Stage window;
//    WebView webView2;
//    WebEngine webEngine;
    @FXML
    private ImageView recaptchaCheckMark;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//         window = new Stage();
//        webView2 = new WebView();
//        webEngine = webView2.getEngine();
//        window.setOnCloseRequest(e -> {
//            if (webEngine != null && webEngine.getTitle().contains("success")) {
//                etatrecaptcha = 1;
//                recaptchaCheckMark.setImage(new Image("Images/checkMark.png"));
//            }
//            System.out.println("etat recaptcha=" + etatrecaptcha);
//        });
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setMinWidth(250);
        /////////////
        tftype.setItems(listType);
         screenshot.setOnDragOver(new EventHandler<DragEvent>() {
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
        screenshot.setOnDragDropped(new EventHandler<DragEvent>() {
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
                        screenshot.setImage(new Image("file:" + file.getAbsolutePath()));
//                        screenshotView.setFitHeight(150);
//                        screenshotView.setFitWidth(250);
                        upload.setText(path);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        screenshot.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));
   
    }    

    @FXML
    private void AjouterReclamation(ActionEvent event) throws SQLException {
//        System.out.println(tfdate.getValue());
        //        Time a = java.sql.Time.valueOf(TempsDispoTimePicker.getValue());
        ReclamationServices sp = new ReclamationServices();
        Reclamation e = new Reclamation();
                        e.setNumero_mobile(tfnumero.getText());

        e.setType(tftype.getValue());

        e.setDescription(tfdescription.getText());
        e.setObjet(tfobjet.getText());
        e.setNom(tfnom.getText());
         e.setPrenom(tfprenom.getText());
          e.setEmail(tfemail.getText());
e.setScreenshot(path);
e.setId_trip(selectedBonplanID);
e.setId_hotel(selectedCompteID);
if(testSaisie())
{
        sp.ajouterReclamation(e);
 n = Notifications.create()
                    .title("Succes")
                    .text("Reclamation envoyé avec succes")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(3));
            n.showInformation();}
    }
     public void listCompte() {
        ArrayList arrayList = null;
        TripServices produitService = new TripServices();
//        if (rechercheBonPlansTFL.getText().equals("")) {
//            arrayList = (ArrayList) produitService.listerBonplan();
//        } else {
            arrayList = (ArrayList) produitService.ListClasse1(rechercheTrip.getText());
//        }

        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        listeTrip.setItems(observableList);
    }
      public void listHotel() {
        ArrayList arrayList = null;
        HotelServices produitService = new HotelServices();
//        if (rechercheBonPlansTFL.getText().equals("")) {
//            arrayList = (ArrayList) produitService.listerBonplan();
//        } else {
            arrayList = (ArrayList) produitService.ListClasse1(rechercheHotel.getText());
//        }

        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        listeHotel.setItems(observableList);
    }
 TextField rechercheTrip = new TextField();
  TextField rechercheHotel = new TextField();

     TableView<Trip> listeTrip = new TableView<>();
          TableView<Hotel> listeHotel = new TableView<>();


    @FXML
    private void SelectReclamation(ActionEvent event) {
          if (tftype.getValue().toString() == "Voyage") {
            Stage window = new Stage();
    rechercheTrip.setOnKeyReleased((KeyEvent a) -> {listCompte();});
            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Veuillez choisir un bon plan");
//            window.setMinWidth(350);

//            TableColumn<BonplanAdnene, Integer> id = new TableColumn<>("ID");
////            id.setMinWidth(100);
//            id.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Trip, String> ville_dest = new TableColumn<>("ville_dest");
//            libelle.setMinWidth(100);
            ville_dest.setCellValueFactory(new PropertyValueFactory<>("ville_dest"));
            
            
            

        TripServices produitService = new TripServices();

            ArrayList arrayList;

            if (rechercheTrip.getText().equals("")) {
                arrayList = (ArrayList) produitService.afficherTrip();
            } else {
                arrayList = (ArrayList) produitService.ListClasse1(rechercheTrip.getText());
            }

            ObservableList observableList = FXCollections.observableArrayList(arrayList);

            listeTrip.setEditable(true);
            listeTrip.setItems(observableList);
            listeTrip.getColumns().setAll( ville_dest);
//            listeBonplan.getColumns().setAll(id, libelle,description);

            listeTrip.setOnMouseClicked((MouseEvent event2)
                    -> {
                if (event2.getClickCount() >= 2) {
                    if (listeTrip.getSelectionModel().getSelectedItem() != null) {
                        selectedBonplanID = listeTrip.getSelectionModel().getSelectedItem().getId_trip();
                        selectedCompteID=0;
//        nameTextField.setText(selectedPerson.getName());
//        addressTextField.setText(selectedPerson.getAddress());
                        System.out.println("id du bon voyage selectioné=" + selectedBonplanID);
                        window.close();

                        Notifications n = Notifications.create()
                                .title("Succès")
                                .text("Voyage selectionné avec succès")
                                .graphic(null)
                                .position(Pos.TOP_CENTER)
                                .hideAfter(Duration.seconds(3));
                        n.showInformation();
                     //   checkMarkImage.setImage(new Image("Images/checkMark.png"));
                        //typeReclamationCBX.setValue(listeBonplan.getSelectionModel().getSelectedItem().getLibelle().toString());

                    }
                }
            });

//            Label label = new Label();
//            label.setText("aaaaa");
            Button closeButton = new Button("Fermer");

            closeButton.setOnAction(e -> window.close());

            VBox layout = new VBox(10);
            layout.getChildren().setAll(rechercheTrip, listeTrip, closeButton);
            layout.setAlignment(Pos.CENTER);

            //Display window and wait for it to be closed before returning
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   if (tftype.getValue().toString() == "Hotel") {
            Stage windowCompte = new Stage();
           rechercheHotel.setOnKeyReleased((KeyEvent lool) -> 
           {
               if (rechercheHotel.getText().trim().length()>=3)
               listHotel();
               else if (rechercheTrip.getText().trim().length()<3)
                   listeHotel.setItems(null);
           });
           
           
           
            //Block events to other windows
            windowCompte.initModality(Modality.APPLICATION_MODAL);
            windowCompte.setTitle("Veuillez choisir un hotel");
            windowCompte.setMinWidth(400);

//            TableColumn<User, Integer> id = new TableColumn<>("ID");
//            id.setCellValueFactory(new PropertyValueFactory<>("id"));

        

            TableColumn<Hotel, String> nom = new TableColumn<>("nom");
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));

            

            HotelServices produitService = new HotelServices();
//            ArrayList arrayList = (ArrayList) produitService.afficherlisteUtilisateurs();
//            ObservableList observableList = FXCollections.observableArrayList(arrayList);
//
//            
//            listeCompte.setEditable(true);
//            listeCompte.setItems(observableList);
          ArrayList arrayList;
 if (rechercheHotel.getText().equals("")) {
                arrayList = (ArrayList) produitService.afficherHotel();
            } else {
                arrayList = (ArrayList) produitService.ListClasse1(rechercheHotel.getText());
            }

            ObservableList observableList = FXCollections.observableArrayList(arrayList);

            listeHotel.setEditable(true);
            listeHotel.setItems(observableList);
            listeHotel.getColumns().setAll( nom);
//            listeCompte.getColumns().setAll(id, username, nom, prenom);

            listeHotel.setOnMouseClicked((MouseEvent event3)
                    -> {
                if (event3.getClickCount() >= 2) {
                    if (listeHotel.getSelectionModel().getSelectedItem() != null) {
                        selectedCompteID = listeHotel.getSelectionModel().getSelectedItem().getId_hotel();
                        selectedBonplanID=0;
//        nameTextField.setText(selectedPerson.getName());
//        addressTextField.setText(selectedPerson.getAddress());
                        System.out.println("id du compte selectioné=" + selectedCompteID);
                        windowCompte.close();

                        Notifications n = Notifications.create()
                                .title("Succès")
                                .text("Compte selectionné avec succès")
                                .graphic(null)
                                .position(Pos.TOP_CENTER)
                                .hideAfter(Duration.seconds(3));
                        n.showInformation();
                       // checkMarkImage.setImage(new Image("Images/checkMark.png"));
                        //typeReclamationCBX.setValue(listeBonplan.getSelectionModel().getSelectedItem().getLibelle().toString());

                    }
                }
            });

//            Label label = new Label();
//            label.setText("aaaaa");
            Button closeButton = new Button("Fermer");
            closeButton.setOnAction(e -> windowCompte.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(rechercheHotel,listeHotel, closeButton);
            layout.setAlignment(Pos.CENTER);

            //Display window and wait for it to be closed before returning
            Scene scene = new Scene(layout);
            windowCompte.setScene(scene);
            windowCompte.showAndWait();

        }
                }

    @FXML
    private void UploadImage(ActionEvent event) 
        throws MalformedURLException {
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
            screenshot.setImage(new Image(selectedFile.toURI().toURL().toString()));
            screenshot.setFitHeight(150);
            screenshot.setFitWidth(250);
            upload.setText(path);

        }
    }

    @FXML
    private void ModifierReclamation(ActionEvent event) {
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la modification de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {    
       String id=tfid.getText();
        String numero_mobile=tfnumero.getText();
        String nom=tfnom.getText();
                String prenom=tfprenom.getText();
                String email=tfemail.getText();
                String type=tftype.getValue();
                String description=tfdescription.getText();
                String objet=tfobjet.getText();
                String id_trip =String.valueOf(selectedBonplanID);
                String id_hotel =String.valueOf(selectedCompteID);


        String screenshot=path;

        ReclamationServices sp = new ReclamationServices();
        Reclamation e = new Reclamation();
        e.setId(Integer.parseInt(id));
        e.setNumero_mobile(numero_mobile);
        e.setNom(nom);
                e.setPrenom(prenom);
        e.setEmail(email);
                e.setDescription(description);
        e.setType(type);
        e.setObjet(objet);
        e.setEmail(email);
         e.setScreenshot(path);
e.setId_trip(selectedBonplanID);
e.setId_hotel(selectedCompteID);

       
        e.setId(Integer.parseInt(id));


sp.modifierReclamation(e);
                     Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de le modification!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("L'hôtel a été bien modifié");
                    alert2.show();
                   // afficherlisteReclamation();     
        }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("L'hôtel n'a pas été modifié");
            alert2.show();}
    }

    @FXML
    private void recaptcha(MouseEvent event) {
        
    }

    @FXML
    private Boolean testNom() {
            int nbNonChar = 0;
        for (int i = 1; i < tfnom.getText().trim().length(); i++) {
            char ch = tfnom.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && tfnom.getText().trim().length() >= 3) {
          //   nomcheckmark.setImage(new Image("C:\\Users\\DELL\\Desktop\\ImgPidev"));
            return true;
        } else {
        //    nomCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
            return false;

        }
    }

    @FXML
    private Boolean testPrenom() {
                int nbNonChar = 0;
        for (int i = 1; i < tfprenom.getText().trim().length(); i++) {
            char ch = tfprenom.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && tfprenom.getText().trim().length() >= 3) {
        //    prenomCheckMark.setImage(new Image("Images/checkMark.png"));
            return true;
        } else {
          //  prenomCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
//                erreur = erreur + ("Pas de caractere permit dans le telephone\n");
            return false;

        }
    }

    @FXML
    private Boolean testEmail() {
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (tfemail.getText() == null) {
            return false;
        }

        if (pat.matcher(tfemail.getText()).matches() == false) {
           // emailCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
//                erreur = erreur + ("Veuillez verifier votre adresse email\n");
            return false;
//            

        } else {
           // emailCheckMark.setImage(new Image("Images/checkMark.png"));
        }
        return true;
    }

    @FXML
    private Boolean testNum() {
               if (tfnumero.getText().trim().length() == 8) {
            int nbChar = 0;
            for (int i = 1; i < tfnumero.getText().trim().length(); i++) {
                char ch = tfnumero.getText().charAt(i);
                if (Character.isLetter(ch)) {
                    nbChar++;
                }
            }

            if (nbChar == 0) {
               // telCheckMark.setImage(new Image("Images/checkMark.png"));
                return true;
            } else {
             //   telCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
//                erreur = erreur + ("Pas de caractere permit dans le telephone\n");
                return false;

            }
        } else if (tfnumero.getText().trim().length() != 8) {
//            erreur = erreur + ("Il faut saisir 8 chiffres dans le numéro de telephone\n");
            //telCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
            return false;
        }

        return true;
    }
 private Boolean testSaisie() {
        erreur = "";
        if (!testEmail()) {
            erreur = erreur + ("Veuillez verifier que votre adresse email est de la forme : ***@***.** \n");
        }
        if (!testNum()) {
            erreur = erreur + ("Telephone doit avoir 8 chiffres et ne doit pas contenir des caracteres \n");
        }
        
      
        if (!testNom()) {
            erreur = erreur + ("Veuillez verifier votre Nom: seulement des caractères et de nombre >= 3 \n");
        }
        if (!testPrenom()) {
            erreur = erreur + ("Veuillez verifier votre Prenom: seulement des caractères et de nombre >= 3");
        }
         if (!testObjet()) {
            erreur = erreur + ("Veuillez verifier votre Objet: seulement des caractères et de nombre >= 3");
        }
 if (!testdescription()) {
            erreur = erreur + ("Veuillez verifier votre Objet: seulement des caractères et de nombre >= 10");
        }
        if (!testEmail() || !testNum() ||  !testNom() || !testPrenom()||!testObjet()||!testdescription()) {
            n = Notifications.create()
                    .title("Erreur de format")
                    .text(erreur)
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(6));
            n.showInformation();
           
        }

        return testEmail() && testNum()  && testNom() && testPrenom();
    }
    @FXML
    private Boolean testObjet() {
               int nbNonChar = 0;
        for (int i = 1; i < tfobjet.getText().trim().length(); i++) {
            char ch = tfobjet.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && tfobjet.getText().trim().length() >= 3) {
        //    prenomCheckMark.setImage(new Image("Images/checkMark.png"));
            return true;
        } else {
          //  prenomCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
//                erreur = erreur + ("Pas de caractere permit dans le telephone\n");
            return false;

        }
    }

    @FXML
    private Boolean testdescription() {
                int nbNonChar = 0;
        for (int i = 1; i < tfdescription.getText().trim().length(); i++) {
            char ch = tfdescription.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && tfdescription.getText().trim().length() >= 10) {
        //    prenomCheckMark.setImage(new Image("Images/checkMark.png"));
            return true;
        } else {
          //  prenomCheckMark.setImage(new Image("Images/erreurcheckMark.png"));
//                erreur = erreur + ("Pas de caractere permit dans le telephone\n");
            return false;

        }
    }
          
    }
    

