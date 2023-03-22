/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entite.Pays;
import entite.Hebergement;
import Services.PaysServices;
import Services.HebergementServices;
import Services.HotelServices;
import entite.Hotel;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import static pidevapp4.PidevApp4.Userconnected;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionHebergementController implements Initializable {
                private Stage primaryStage;

    @FXML
    private TextField tftitre;
    @FXML
    private TextField tfperiode;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tftype;
    @FXML
    private TextField tfimg;
    @FXML
    private ComboBox<Pays> affecter;
    @FXML
    private TextField tfchoix;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TableView<Hebergement> tableheb;
    @FXML
    private TableColumn<Hebergement, String> tabtitre;
    @FXML
    private TableColumn<Hebergement, String> tabtype;
    @FXML
    private TableColumn<Hebergement, Integer> tabprix;
    @FXML
    private TableColumn<Hebergement, String> tabimg;
    @FXML
    private TableColumn<Hebergement, String> tabadresse;
    @FXML
    private TableColumn<Hebergement, String> tabperiode;
    @FXML
    private TableColumn<Hebergement, String> tabchoix;
    @FXML
    private TableColumn<Hebergement, String> tabdate;
   private Hebergement c; 
    private String path;
    File selectedFile;
            
            ObservableList<Hebergement>  list =  FXCollections.observableArrayList();
      int  index= -1; 
             Pays p ;
                    int id=0;
  
           
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
                    final ObservableList<Pays> options= FXCollections.observableArrayList();
    @FXML
    private TableColumn<Hebergement, String> tabpaysa;
    @FXML
    private ImageView imghotel;
    @FXML
    private Button upload;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

         try {
            fillcombo();
        } 
   catch (SQLException ex) {
        } 
         ObservableList<Hebergement>  list =  FXCollections.observableArrayList();
          try { 
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("SELECT pays_id , titre ,type,prix,image,adresse,periode,choix,date_h  FROM hebergement");
            while(rs.next())
            {
                list.add(new Hebergement(p,rs.getString(2),rs.getString(3), rs.getInt(4),rs.getString(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getDate(9)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionHebergementController.class.getName()).log(Level.SEVERE, null, ex);
        }

      
  
        // TODO
 tableheb.setItems(list);
  tableheb.refresh();
        // TODO
    }   
    ObservableList combo = FXCollections.observableArrayList();
     public void fillcombo() throws SQLException {
       try {
            List<Pays> list=new ArrayList<>();
             PreparedStatement pst;
        String query = "select * from pays";
        pst = cnx.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
//                int i=rs.getInt("id_vehicule");  
//                String st=Integer.toString(i);
//                System.out.println(st);
//                options.add(st);
                p =new Pays(rs.getInt(1),rs.getString(2));
                list.add(p);
               
            }
            options.addAll(list);
             affecter.getItems().setAll(list);

            System.out.println("aaa");
            System.out.println(options);
        } catch (SQLException ex) {
            Logger.getLogger(GestionHebergementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query1="select id from pays where pays=?";
                        PreparedStatement pst;

            pst=cnx.prepareStatement(query1);
            pst.setString(1,p.getPays());
                   ResultSet rs = pst.executeQuery();

            rs=pst.executeQuery();
             while(rs.next())
            {

                id=rs.getInt(1);
            }}

    @FXML
    private void selected(MouseEvent event) {
         index=tableheb.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
              affecter.setValue(p);

        tftitre.setText(tabtitre.getCellData(index).toString());
                tftype.setText(tabtype.getCellData(index).toString());
                tfprix.setText(tabprix.getCellData(index).toString());
                tfimg.setText(tabimg.getCellData(index).toString());
                 tfadresse.setText(tabadresse.getCellData(index).toString());
                tfperiode.setText(tabperiode.getCellData(index).toString());
                tfchoix.setText(tabchoix.getCellData(index).toString());


    }

    @FXML
    private void ajouterheb(ActionEvent event) {
        HebergementServices ts = new HebergementServices();
                                       Pays p=affecter.getItems().get(affecter.getSelectionModel().getSelectedIndex());
   java.util.Date date = 
    java.util.Date.from(tfdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                  PaysServices ts1 = new PaysServices();
                  
        HebergementServices sp = new HebergementServices();
        Hebergement e = new Hebergement();
        e.setP(affecter.getValue());
        e.setTitre(tftitre.getText());
        e.setType(tftype.getText()); 
        e.setPrix(Integer.parseInt(tfprix.getText())); 
        e.setAdresse(tfadresse.getText());  
        e.setPeriode(tfperiode.getText()); 
        e.setChoix(tfchoix.getText());      
                e.setDate_h(sqlDate);   
             
                e.setImage(path);
  //e.setImg(tfimage.getText());
//if (controlSaisieDescription()){
//    if(controlSaisieNom()){
     
 
        sp.AjouterHebergement(e);        

       tableheb.refresh();
               clearFields();
       affiche1();

       
    }
    private void clearFields() {
        tftitre.clear();
        tfprix.clear();
        tftype.clear();
                tfperiode.clear();
        tfadresse.clear();
        tfperiode.clear();
        tfchoix.clear();
        tfimg.clear();
//imghotel.clear();
            imghotel.setImage(null);
 upload.setText("Upload");        
                imghotel.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));
        
        
        
    }

    @FXML
    private void modifierheb(ActionEvent event) {
                                  Pays pays =affecter.getValue();
   java.util.Date date = 
    java.util.Date.from(tfdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
         String titre=tftitre.getText();
        String type=tftype.getText();

        String prix=tfprix.getText();

         String image=tfimg.getText();
        String adresse=tfadresse.getText();

        String periode=tfperiode.getText();
                String choix=tfchoix.getText();
                   Date date_h=sqlDate;


     
        
        HebergementServices sp = new HebergementServices();
        Hebergement c = new Hebergement();
                        c.setP(pays);

        c.setTitre(titre);
        c.setType(type);

        c.setPrix(Integer.parseInt(prix));
        c.setAdresse(adresse);
         c.setPeriode(periode);
        c.setChoix(choix);
                c.setDate_h(sqlDate);

                        c.setImage(path);


                c.setTitre(titre);

        
        sp.modifierH(c);
                        affiche1();
                               tableheb.refresh();

    }

    @FXML
    private void supprimerheb(ActionEvent event) {
         HebergementServices location = new HebergementServices();
          Hebergement ls = new   Hebergement();
        ls = tableheb.getSelectionModel().getSelectedItem();
                

        location.SupprimerHebergement(ls.getTitre());
                        affiche1();
    }
    
    
    
     public ObservableList<Hebergement> show1()
    {  
       
 
           try {
                 java.util.Date date = 
    java.util.Date.from(tfdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
               ObservableList<Hebergement> obl =FXCollections.observableArrayList();
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
 //exécution de la réquette et enregistrer le resultat dans le resultset
                PreparedStatement pt= cnx.prepareStatement("SELECT titre,type,prix,image,adresse,periode,choix,date_h,pays_id from hebergement  ");

                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Hebergement ls = new Hebergement();
                 
                  ls.setTitre(rs.getString("titre"));
                  ls.setType(rs.getString("type"));
                  ls.setPrix(Integer.parseInt(rs.getString("prix")));
                  ls.setImage(rs.getString("image"));
                  ls.setAdresse(rs.getString("adresse"));
                  ls.setPeriode(rs.getString("periode"));
                  ls.setChoix(rs.getString("choix"));
                  ls.setDate_h(sqlDate);

         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    public void affiche1() {
          java.util.Date date = 
    java.util.Date.from(tfdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
           
               
      tabtitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
      tabtype.setCellValueFactory(new PropertyValueFactory<>("type"));
      tabprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
      tabimg.setCellValueFactory((new PropertyValueFactory<>("image")));
      tabadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
      tabperiode.setCellValueFactory(new PropertyValueFactory<>("periode"));
      tabchoix.setCellValueFactory(new PropertyValueFactory<>("choix"));
      tabdate.setCellValueFactory((new PropertyValueFactory<>("date_h")));
      ObservableList<Hebergement> obl =FXCollections.observableArrayList();
      obl=show1(); 
      tableheb.setItems(obl);
      System.out.println(""+obl);

                      
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
    private void GoMenu(ActionEvent event)  throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/Menu.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
