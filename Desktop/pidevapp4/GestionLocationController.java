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
import java.time.ZoneId;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entite.Chauffeur;
import entite.Location;
import Services.ChauffeurServices;
import Services.LocationServices;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionLocationController implements Initializable {
                private Stage primaryStage;

    @FXML
    private TableView<Location> tableloc;
    @FXML
    private TableColumn<Location, String> modela;
    @FXML
    private TableColumn<Location, String> dateloca;
    @FXML
    private TableColumn<Location, String> dureea;
    @FXML
    private TextField tflmodel;
    @FXML
    private TextField tflduree;
    @FXML
    private ComboBox<Chauffeur> affecter;
    @FXML
    private DatePicker tfldateloc;
     private Location c; 
            
            ObservableList<Location>  list =  FXCollections.observableArrayList();
      int  index= -1; 
             Chauffeur chauff ;
                    int id=0;
 Connection cnx = MyConnexion.getInstanceConnex().getConnection();
                    final ObservableList<Chauffeur> options= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            fillcombo();
        } 
   catch (SQLException ex) {
        } 
         ObservableList<Location>  list =  FXCollections.observableArrayList();
          try { 
 Connection cnx = MyConnexion.getInstanceConnex().getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("SELECT model , dateloc, duree,chauffeur_id FROM locationc");
            while(rs.next())
            {
                list.add(new Location(rs.getString(1),rs.getDate(2), rs.getInt(3),chauff));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionLocationController.class.getName()).log(Level.SEVERE, null, ex);
        }

      
  
        // TODO
 tableloc.setItems(list);
  tableloc.refresh();
  affiche1();
        // TODO
    }    
    ObservableList combo = FXCollections.observableArrayList();
     public void fillcombo() throws SQLException {
       try {
            List<Chauffeur> list=new ArrayList<>();
             PreparedStatement pst;
        String query = "select * from chauffeur";
        pst = cnx.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
//                int i=rs.getInt("id_vehicule");  
//                String st=Integer.toString(i);
//                System.out.println(st);
//                options.add(st);
                chauff =new Chauffeur(rs.getInt(1),rs.getString(5));
                list.add(chauff);
               
            }
            options.addAll(list);
             affecter.getItems().setAll(list);

            System.out.println("aaa");
            System.out.println(options);
        } catch (SQLException ex) {
            Logger.getLogger(GestionLocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query1="select id from chauffeur where nom=?";
                        PreparedStatement pst;

            pst=cnx.prepareStatement(query1);
            pst.setString(1,chauff.getNom());
                   ResultSet rs = pst.executeQuery();

            rs=pst.executeQuery();
             while(rs.next())
            {

                id=rs.getInt(1);
            }
                
           
            

    }

    @FXML
    private void selectedl(MouseEvent event) {
              index=tableloc.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
              affecter.setValue(chauff);

        tflmodel.setText(modela.getCellData(index).toString());
                tflduree.setText(dureea.getCellData(index).toString());
                

    }

    @FXML
    private void ajouterloc(ActionEvent event) {
        
         if(verifUserChamps() ){ 
                        if ( controlSaisie()){
    java.util.Date date = 
    java.util.Date.from(tfldateloc.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
         LocationServices ts = new LocationServices();
                                       Chauffeur chauff=affecter.getItems().get(affecter.getSelectionModel().getSelectedIndex());

                  ChauffeurServices ts1 = new ChauffeurServices();
       ts.AjouterLocation(new Location(tflmodel.getText(),sqlDate,Integer.parseInt(tflduree.getText()),affecter.getValue()));
                affiche1();

       tableloc.refresh();
       
    }
         }}
    

    @FXML
    private void supprimerloc(ActionEvent event) {
          LocationServices location = new LocationServices();
        Location ls = new Location();
        ls = tableloc.getSelectionModel().getSelectedItem();
                

        location.SupprimerLocation(ls.getModel());
                        affiche1();
    }

    @FXML
    private void modifierloc(ActionEvent event) {
         java.util.Date date = 
    java.util.Date.from(tfldateloc.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
         String model=tflmodel.getText();
        Date dateloc=sqlDate;

        String duree=tflduree.getText();
                          Chauffeur Chauffeurs =affecter.getValue();

        LocationServices sp = new LocationServices();
        Location c = new Location();
        c.setModel(model);
        c.setDateloc(sqlDate);

        c.setDuree(Integer.parseInt(duree));
                c.setChauff(Chauffeurs);

                c.setModel(model);

        
        sp.modifierL(c);
                        affiche1();

    }
    public boolean verifUserChamps() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

   
       
        tflmodel.setStyle(styledefault);
        tfldateloc.setStyle(styledefault);
        tflduree.setStyle(styledefault);
              //  tflnomchauff.setStyle(styledefault);

     
       
 

        if (tflmodel.getText().equals("")) {
            tflmodel.setStyle(style);
            verif = 1;
        }
       
       
      
       
        if (tflduree.getText().equals("")) {
            tflduree.setStyle(style);
            verif = 1;
        }
//         if (tflnomchauff.getText().equals("")) {
//            tflnomchauff.setStyle(style);
//            verif = 1;
//        }
       
        if (verif == 0) {
            return true;
        }
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Alert");
        al.setContentText("Verifier les champs");
        al.setHeaderText(null);
        al.show() ; 
        
        return false;
    }
    public boolean controlSaisie(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
         

        if(checkIfStringContainsNumber(tflmodel.getText())){
            alert.setContentText("Le model ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
             
                
        
                  if(checkIfStringContainsNumber2(tflduree.getText())){
            alert.setContentText("la duree ne doit pas contenir des caracteres");
            alert.showAndWait();
            return false;
        }
//                   if(checkIfStringContainsNumber(tflnomchauff.getText())){
//            alert.setContentText("Le prenom ne doit pas contenir des chiffres");
//            alert.showAndWait();
//            return false;
//        }
             

        
        return true;
    }
    
    public boolean checkIfNumber(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
         

       return true;
    }
    
    public boolean checkIfStringContainsNumber(String str){
        for (int i=0; i<str.length();i++){
            if(str.contains("0") || str.contains("1") || str.contains("2") || str.contains("3") || str.contains("4") || str.contains("5") || str.contains("6") || str.contains("7") || str.contains("8") || str.contains("9")){
                return true;
            }
        }
        return false;
    }
    public boolean checkIfStringContainsNumber2(String str){
        for (int i=0; i<str.length();i++){
            if(str.contains("a") || str.contains("b") || str.contains("c") || str.contains("d") || str.contains("e") || str.contains("f") || str.contains("g") || str.contains("h") || str.contains("i") || str.contains("j")|| str.contains("k")|| str.contains("l")|| str.contains("m")|| str.contains("n")|| str.contains("o")|| str.contains("p")|| str.contains("q")|| str.contains("r")|| str.contains("s")|| str.contains("t")|| str.contains("u")|| str.contains("v")|| str.contains("w")|| str.contains("y")|| str.contains("z")){
                return true;
            }
        }
        return false;
    }
     public ObservableList<Location> show1()
    { 
          

           try {
               ObservableList<Location> obl =FXCollections.observableArrayList();
 Connection cnx = MyConnexion.getInstanceConnex().getConnection();
 //exécution de la réquette et enregistrer le resultat dans le resultset
                // PreparedStatement pt= cnx.prepareStatement("SELECT * from chauffeur a , locationc o where o.id=a.chauffeur_id  ");
                                  PreparedStatement pt= cnx.prepareStatement("SELECT * from locationc  ");

                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Location ls = new Location();
                                //  Chauffeur o = new Chauffeur(rs.getInt("o.id"),rs.getString("o.nom"));


                 ls.setModel(rs.getString("model"));

                 ls.setDateloc(rs.getDate("dateloc"));
                 ls.setDuree(Integer.parseInt(rs.getString("duree")));
                                                              //    ls.setChauff(o);

             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    public void affiche1() {
        
           
                         
      modela.setCellValueFactory(new PropertyValueFactory<>("model"));
      dateloca.setCellValueFactory(new PropertyValueFactory<>("dateloc"));
      dureea.setCellValueFactory((new PropertyValueFactory<>("duree")));
     //nomchauffa.setCellValueFactory(new PropertyValueFactory<>("chauff"));
      ObservableList<Location> obl =FXCollections.observableArrayList();
     obl=show1(); 
      tableloc.setItems(obl);
      System.out.println(""+obl);

                      
    }


    @FXML
    private void retour(ActionEvent event) throws IOException {
          Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionChauffeur.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
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
