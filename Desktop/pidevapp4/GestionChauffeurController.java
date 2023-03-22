/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entite.Chauffeur;
import Services.ChauffeurServices;
import java.util.Optional;
import javafx.event.Event;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import utils.MyConnexion;
import static pidevapp4.PidevApp4.Userconnected;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionChauffeurController implements Initializable {
      public static int  nh = 0 ,nf=0 , ne = 0 ;  
            ObservableList<String> list = FXCollections.observableArrayList("Homme","Femme","Enfant");
                private Stage primaryStage;

    @FXML
    private TableView<Chauffeur> tablechauff;
    @FXML
    private TableColumn<Chauffeur, String> noma;
    @FXML
    private TableColumn<Chauffeur, String> prenoma;
    @FXML
    private TableColumn<Chauffeur, String> sexea;
    @FXML
    private TableColumn<Chauffeur, Integer> numtela;
    @FXML
    private TableColumn<Chauffeur, String> dispoa;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfsexe;
    @FXML
    private TextField tfnumtel;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnretour;
    @FXML
    private Button btnclose;
    @FXML
    private TextField tfdispo;
    @FXML
    private TextField recherche_text;
          int  index= -1; 
    @FXML
    private Label UserName;
    @FXML
    private Label label;
    @FXML
    private Label erreurdescrip;
    @FXML
    private Label villeerreur;
    @FXML
    private Label descriperreur;
    @FXML
    private Label periodeerreur;
    @FXML
    private Label villererreur;
    @FXML
    private Label offreerreur;
    @FXML
    private Button Logout;
    @FXML
    private Button btnclose1;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
         UserName.setText(Userconnected.getPrenom()+" "+Userconnected.getNom());
        // TODO
    }    
    private void GotoFXML(String vue, String title,Event aEvent) {
           try {
               Event event;
               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(vue + ".fxml"));
               Parent root1 = (Parent) fxmlLoader.load();
               Stage stage =(Stage)((Node) aEvent.getSource()).getScene().getWindow() ;
               stage.setTitle(title);
               stage.setScene(new Scene(root1));
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(GestionChauffeurController.class.getName()).log(Level.SEVERE, null, ex);
           }
       
         recherche();
        affiche();
        ObservableList<Chauffeur>  list =  FXCollections.observableArrayList();
          try { 
 Connection cnx = MyConnexion.getInstanceConnex().getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("SELECT nom , prenom, sexe, num,disponibilite FROM chauffeur");
            while(rs.next())
            {
                list.add(new Chauffeur(rs.getString(1),rs.getString(2), rs.getString(3), rs.getInt(4),rs.getString(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionChauffeurController.class.getName()).log(Level.SEVERE, null, ex);
        }

     
  
        // TODO
 tablechauff.setItems(list);
  tablechauff.refresh();
        // TODO
        // TODO
    }    

    @FXML
    private void Selected(MouseEvent event) {
         index=tablechauff.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
        tfnom.setText(noma.getCellData(index).toString());
                tfprenom.setText(prenoma.getCellData(index).toString());
                tfsexe.setText(sexea.getCellData(index).toString());
                tfnumtel.setText(numtela.getCellData(index).toString());
                tfdispo.setText(dispoa.getCellData(index).toString());
    }

    @FXML
    private void ajouterchauffeur(ActionEvent event) {
          if(verifUserChamps() ){ 
                        if ( controlSaisie()){



         ChauffeurServices ts = new ChauffeurServices();
         
       ts.AjouterChauffeur(new Chauffeur(tfnom.getText(),tfprenom.getText(),tfsexe.getText(),Integer.parseInt(tfnumtel.getText()),tfdispo.getText()));
        affiche();


       tablechauff.refresh();

       
                }}
    }

    @FXML
    private void supprimerchauffeur(ActionEvent event)  throws SQLException {

                  ChauffeurServices chauffeur = new ChauffeurServices();
        Chauffeur ls = new Chauffeur();
        ls = tablechauff.getSelectionModel().getSelectedItem();
                

        chauffeur.SupprimerChauffeur(ls.getNom());
                affiche();

    }

    @FXML
    private void modifierchauffeur(ActionEvent event) {
         String nom=tfnom.getText();
        String prenom=tfprenom.getText();
        String sexe=tfsexe.getText();

        String num=tfnumtel.getText();
        String disponibilite=tfdispo.getText();

        ChauffeurServices sp = new ChauffeurServices();
        Chauffeur c = new Chauffeur();
        c.setNom(nom);
 c.setPrenom(prenom);
        c.setSexe(sexe);

        c.setNum(Integer.parseInt(num));
                c.setDisponibilite(disponibilite);

                c.setNom(nom);

        
        sp.modifier(c);
                affiche();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
          Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionLocation.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    public boolean verifUserChamps() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

   
       
        tfnom.setStyle(styledefault);
        tfprenom.setStyle(styledefault);
        tfsexe.setStyle(styledefault);
        tfnumtel.setStyle(styledefault);
        tfdispo.setStyle(styledefault);

     
       
 

        if (tfnom.getText().equals("")) {
            tfnom.setStyle(style);
            verif = 1;
        }
       
        if ( tfprenom.getText().equals("")) {
             tfprenom.setStyle(style);
            verif = 1;
        }
         
        if (tfsexe.getText().equals("")) {
            tfsexe.setStyle(style);
            verif = 1;
        }
       
        if (tfnumtel.getText().equals("")) {
            tfnumtel.setStyle(style);
            verif = 1;
        }
        if (tfdispo.getText().equals("")) {
            tfdispo.setStyle(style);
            verif = 1;
        }
       
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
         

        if(checkIfStringContainsNumber(tfnom.getText())){
            alert.setContentText("Le nom ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
                if(checkIfStringContainsNumber(tfprenom.getText())){
            alert.setContentText("Le prenom ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
                  if(checkIfStringContainsNumber(tfsexe.getText())){
            alert.setContentText("Le prenom ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
                  if(checkIfStringContainsNumber2(tfnumtel.getText())){
            alert.setContentText("Le prenom ne doit pas contenir des caracteres");
            alert.showAndWait();
            return false;
        }
                  if(checkIfStringContainsNumber(tfdispo.getText())){
            alert.setContentText("Le prenom ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
             

        
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

    private void calcul(ActionEvent event) 
        throws IOException{
        ChauffeurServices ser= new ChauffeurServices();
        
        List<Chauffeur> li =ser.afficher(); 
        int i = 0; 
        
        for ( i=0 ; i<li.size();i++){
        if (li.get(i).getNom().equals("Homme"))
        
        {nh=nh+1;}  ;
        if (li.get(i).getNom().equals("Femme")){nf=nf+1 ; } 
        if (li.get(i).getNom().equals("Enfant")){ne=ne+1 ; }  }
        
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("ChauffStat.fxml"));
         Parent root = loader.load();
        tfnom.getScene().setRoot(root);
    }

    @FXML
    private void closeapp(ActionEvent event) {
         Stage stage = (Stage) btnclose.getScene().getWindow();
        stage.close();
    }
      private void recherche() {
        recherche_text.textProperty().addListener((observable, oldValue, newValue) -> {
    ChauffeurServices ser= new ChauffeurServices();
String rech = oldValue+newValue ;         
        List<Chauffeur> li =ser.ListClasse1(rech);
        ObservableList<Chauffeur> data = FXCollections.observableArrayList(li);
                  

        
       
 
         noma.setCellValueFactory(  
                new PropertyValueFactory<>("nom")) ; 
        prenoma.setCellValueFactory(
                new PropertyValueFactory<>("prenom"));
     
        
        sexea.setCellValueFactory(
                new PropertyValueFactory<>("sexe"));
        
        numtela.setCellValueFactory(
                new PropertyValueFactory<>("num"));
        dispoa.setCellValueFactory(
                new PropertyValueFactory<>("disponibilite"));
       
        tablechauff.setItems(data);
});
    }
      public ObservableList<Chauffeur> show1()
    { 
       

           try {
               ObservableList<Chauffeur> obl =FXCollections.observableArrayList();
 Connection cnx = MyConnexion.getInstanceConnex().getConnection();
 //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= cnx.prepareStatement("select nom,prenom,sexe,num,disponibilite from chauffeur ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Chauffeur ls = new Chauffeur();
                 ls.setNom(rs.getString("nom"));

                 ls.setPrenom(rs.getString("prenom"));
                 ls.setSexe(rs.getString("sexe"));
                 ls.setNum(rs.getInt("num"));
                 ls.setDisponibilite(rs.getString("disponibilite"));
               
             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
 public void affiche() {
        
           
                         
      noma.setCellValueFactory(new PropertyValueFactory<>("nom"));
      prenoma.setCellValueFactory(new PropertyValueFactory<>("prenom"));
      sexea.setCellValueFactory(new PropertyValueFactory<>("sexe"));
      numtela.setCellValueFactory(new PropertyValueFactory<>("num"));
      dispoa.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
      ObservableList<Chauffeur> obl =FXCollections.observableArrayList();
     obl=show1(); 
      tablechauff.setItems(obl);
      System.out.println(""+obl);

                      
    }


    @FXML
    private void Calcul(ActionEvent event)  throws IOException{
        ChauffeurServices ser= new ChauffeurServices();
        
        List<Chauffeur> li =ser.afficher(); 
        int i = 0; 
        
        for ( i=0 ; i<li.size();i++){
        if (li.get(i).getNom().equals("Homme"))
        
        {nh=nh+1;}  ;
        if (li.get(i).getNom().equals("Femme")){nf=nf+1 ; } 
        if (li.get(i).getNom().equals("Enfant")){ne=ne+1 ; }  }
        
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("ChauffStat.fxml"));
         Parent root = loader.load();
        tfnom.getScene().setRoot(root);
    }

    @FXML
    private void Logout(ActionEvent event) 
    throws IOException, Exception {
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
    }}}

    private void GotoGestionProfil(ActionEvent event) {
                GotoFXML("ProfilFXML", "InternationalTrip",event);

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
    private void testVille(KeyEvent event) {
    }

    @FXML
    private void testOffre(KeyEvent event) {
    }

    @FXML
    private void Afficher(MouseEvent event) {
    }

    @FXML
    private void testPeriode(KeyEvent event) {
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
