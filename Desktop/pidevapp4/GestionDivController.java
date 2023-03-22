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
import entite.Types;
import entite.Div;
import Services.TypesServices;
import Services.DivServices;
import Services.FavServices;
import entite.Like;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionDivController implements Initializable {

    private TextField tftitre;
               private Stage primaryStage;

    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tftype;
    private TextField tfimg;
    @FXML
    private ComboBox<Types> affecter;
    private TextField tfchoix;
    private TableView<Div> tableheb;
    @FXML
    private TableColumn<Div, String> tabtype;
    @FXML
    private TableColumn<Div, Integer> tabprix;
    @FXML
    private TableColumn<Div, String> tabadresse;
    @FXML
    private TableColumn<Div, String> tabpaysa;
    private DatePicker tfdate;
    private Div c; 
    private String path;
    File selectedFile;
            
            ObservableList<Div>  list =  FXCollections.observableArrayList();
      int  index= -1; 
             Types p ;
                    int id=0;
  
           
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
                    final ObservableList<Types> options= FXCollections.observableArrayList();
    @FXML
    private TableView<Like> tvfav;
    @FXML
    private TableColumn<Like, String> nomfav;
    @FXML
    private TableColumn<Like, Integer> numfav;
    @FXML
    private TableView<Div> tabheb;
    @FXML
    private TextField rech;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try {
            fillcombo();
        } 
   catch (SQLException ex) {
        } 
         ObservableList<Div>  list =  FXCollections.observableArrayList();
          try { 
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("SELECT `types_id`, `nom`, `numtel`, `adresse` FROM `div` ");
            while(rs.next())
            {
                list.add(new Div(p,rs.getString(2), rs.getInt(3),rs.getString(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionDivController.class.getName()).log(Level.SEVERE, null, ex);
        }

      
  
        // TODO
 tableheb.setItems(list);
  tableheb.refresh();
   }  
    ObservableList combo = FXCollections.observableArrayList();
     public void fillcombo() throws SQLException {
       try {
            List<Types> list=new ArrayList<>();
             PreparedStatement pst;
        String query = "select * from types";
        pst = cnx.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
//                int i=rs.getInt("id_vehicule");  
//                String st=Integer.toString(i);
//                System.out.println(st);
//                options.add(st);
                p =new Types(rs.getInt(1),rs.getString(2),rs.getString(3));
                list.add(p);
               
            }
            options.addAll(list);
             affecter.getItems().setAll(list);

            System.out.println("aaa");
            System.out.println(options);
        } catch (SQLException ex) {
            Logger.getLogger(GestionHebergementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query1="select id from types where types=?";
                        PreparedStatement pst;

            pst=cnx.prepareStatement(query1);
            pst.setString(1,p.getTypes());
                   ResultSet rs = pst.executeQuery();

            rs=pst.executeQuery();
             while(rs.next())
            {

                id=rs.getInt(1);
            }}
       

    private void selected(MouseEvent event) {
 index=tableheb.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
              affecter.setValue(p);

       
                tftype.setText(tabtype.getCellData(index).toString());
                tfprix.setText(tabprix.getCellData(index).toString());
                //tfimg.setText(tabimg.getCellData(index).toString());
                 tfadresse.setText(tabadresse.getCellData(index).toString());
    }

    @FXML
    private void ajouterheb(ActionEvent event) {
 DivServices ts = new DivServices();
                                       Types p=affecter.getItems().get(affecter.getSelectionModel().getSelectedIndex());
  
                  TypesServices ts1 = new TypesServices();
                  
        DivServices sp = new DivServices();
        Div e = new Div();
        e.setP(affecter.getValue());
       
        e.setNom(tftype.getText()); 
        e.setNumtel(Integer.parseInt(tfprix.getText())); 
        e.setAdresse(tfadresse.getText());  
       
             
              //  e.setImage(path);
  //e.setImg(tfimage.getText());
//if (controlSaisieDescription()){
//    if(controlSaisieNom()){
     
 
        sp.AjouterDiv(e);        

       tableheb.refresh();
               clearFields();
       affiche1();

       
    }
    private void clearFields() {
        tfprix.clear();
        tftype.clear();
        tfadresse.clear();
       
//imghotel.clear();
         /*   imghotel.setImage(null);
 upload.setText("Upload");        
                imghotel.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));*/
        
        
    }

    @FXML
    private void modifierheb(ActionEvent event) {
          Types types =affecter.getValue();
   java.util.Date date = 
    java.util.Date.from(tfdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String nom=tftype.getText();

        String numtel=tfprix.getText();

        // String image=tfimg.getText();
        String adresse=tfadresse.getText();

       


     
        
        DivServices sp = new DivServices();
        Div c = new Div();
                        c.setP(types);

      
        c.setNom(nom);

        c.setNumtel(Integer.parseInt(numtel));
        c.setAdresse(adresse);
        
                      //  c.setImage(path);


                c.setNom(nom);

        
        sp.modifierH(c);
                        affiche1();
                               tableheb.refresh();
    }

    @FXML
    private void supprimerheb(ActionEvent event) {
         DivServices location = new DivServices();
          Div ls = new   Div();
        ls = tableheb.getSelectionModel().getSelectedItem();
                

        location.SupprimerDiv(ls.getNom());
                        affiche1();
    }
     public ObservableList<Div> show1()
    {  
       
 
           try {
                 
               ObservableList<Div> obl =FXCollections.observableArrayList();
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
 //exécution de la réquette et enregistrer le resultat dans le resultset
                PreparedStatement pt= cnx.prepareStatement("SELECT nom,numtel,adresse,types_id from div  ");

                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Div ls = new Div();
                 
                  ls.setNom(rs.getString("nom"));
                  ls.setNumtel(Integer.parseInt(rs.getString("numtel")));
                  //ls.setImage(rs.getString("image"));
                  ls.setAdresse(rs.getString("adresse"));
                  

         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
       public ObservableList<Like> show2()
    {  
       
 
           try {
                 
               ObservableList<Like> obl =FXCollections.observableArrayList();
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
 //exécution de la réquette et enregistrer le resultat dans le resultset
                PreparedStatement pt= cnx.prepareStatement("SELECT id,nom_id like  ");

                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Like ls = new Like();
                 
                  ls.setNom(rs.getString("nom_id"));
                  
                  

         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    public void affiche1() {
         
           
               
      tabtype.setCellValueFactory(new PropertyValueFactory<>("nom"));
      tabprix.setCellValueFactory(new PropertyValueFactory<>("numtel"));
     // tabimg.setCellValueFactory((new PropertyValueFactory<>("image")));
      tabadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
     
      ObservableList<Div> obl =FXCollections.observableArrayList();
      obl=show1(); 
      tableheb.setItems(obl);
      System.out.println(""+obl);

                      
    }


      @FXML
    private void Windowresto(MouseEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionResto.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
        
    }

    @FXML
    private void Rechercher(ActionEvent event) {
     /*   try {
                   ObservableList<Div> ofList = FXCollections.observableArrayList();
       colid.setCellValueFactory(new PropertyValueFactory<>("iddiv"));
      colnom.setCellValueFactory(new PropertyValueFactory<>("nomdiv"));
      coltype.setCellValueFactory(new PropertyValueFactory<>("typediv"));
      coladresse.setCellValueFactory(new PropertyValueFactory<>("adressediv"));
      colnum.setCellValueFactory(new PropertyValueFactory<>("numdiv")); 
        
        /////////////////////////////////////////////////////////////////////////////
        DivServices ds = new DivServices();
        List old = ds.AfficherUser(rech.getText());
        ofList.addAll(old);
        tvdiv.setItems(ofList);
        tvdiv.refresh();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/   
    }
     //affiche liste fav
      public void affichelistefav() {
        
           
                         
      
     
      nomfav.setCellValueFactory(new PropertyValueFactory<>("nomfav"));
      numfav.setCellValueFactory(new PropertyValueFactory<>("numfav"));
      ObservableList<Like> obl =FXCollections.observableArrayList();
     
     obl=show2(); 
      tvfav.setItems(obl);
      System.out.println(""+obl);

                      
    }

    @FXML
    private void Afficherfav(MouseEvent event) {
          int index=-1; 
        index=tvfav.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
                

        tftype.setText(nomfav.getCellData(index));
        
    }

    @FXML
    private void ajouterfav(MouseEvent event) {
         FavServices fs = new FavServices();
        Like f = new Like();
       
        f.setNom(nomfav.getText());
       
        f.setId(Integer.parseInt(numfav.getText()));
 
         

        fs.ajouterFav(f);
        affichelistefav();  
          
    }

    @FXML
    private void supprimerfav(MouseEvent event) {
          FavServices fs = new FavServices();
        Like f = new Like();
        f.setNom(nomfav.getText());
        fs.supprimerFav(f);
        affichelistefav();  
    }
      @FXML
    private void jouer(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/jeu.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void ladate(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/Date.fxml"));
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
    private void Afficher(MouseEvent event) {
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
