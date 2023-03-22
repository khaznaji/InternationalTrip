/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.EvenementServices;
import Services.ReservationServices;
import entite.event;
import entite.reservation;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ReservationController implements Initializable {

    private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();
    @FXML
    private Label label;
    @FXML
    private Button btajouter;
    @FXML
    private Button btsupprimer;
    @FXML
    private Button btmodifier;
    @FXML
    private TextField tfnbr_place;
    @FXML
    private TextField tftype;
    @FXML
    private TableView<reservation> tab;
    @FXML
    private TableColumn<reservation, String> coltype;
    @FXML
    private TableColumn<reservation, Integer> colnbr_place;
    @FXML
    private TableColumn<reservation, Integer> colprix;
    @FXML
    private Label erreurdescrip;
    @FXML
    private Label villeerreur;
    @FXML
    private Label descriperreur;
    @FXML
    private Label periodeerreur;
    @FXML
    private ImageView recaptchaCheckMark;
    @FXML
    private Label villererreur;
    @FXML
    private Label offreerreur;
    
    @FXML
    private TextField tfprix;
    @FXML
    private Button btsupprimer1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate(); 
    }    
     private void loadDate(){
     coltype.setCellValueFactory(new PropertyValueFactory<>("type_paiement"));
      colnbr_place.setCellValueFactory(new PropertyValueFactory<>("nbr_place"));
      colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
  
      ObservableList<reservation> obl =FXCollections.observableArrayList();
     // tableview.setItems(null);
     
     obl=show1(); 
      tab.setItems(obl);
      System.out.println(""+obl);
    }
      public ObservableList<reservation> show1()
    { 
       

           try {
               ObservableList<reservation> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select type_paiement,nbr_place, prix  from reservation ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 reservation ls = new reservation();
                 

                 ls.setType_paiement(rs.getString("type_paiement"));
                 ls.setNbr_place(rs.getInt("nbr_place"));
                 ls.setPrix(rs.getInt("prix"));
                  
                
               
             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null ;
                   }


    @FXML
    private void ajouter(ActionEvent event) {
         if(tftype.getText().isEmpty() | tfnbr_place.getText().isEmpty() | tfprix.getText().isEmpty()){
            //JOptionPane.showMessageDialog(null, "Remplir les champs vides");
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText(null);
            al.setContentText("Remplir les champs vides");
            al.showAndWait();
        }else{
        ReservationServices rt = new ReservationServices();
        rt.Ajouterreservation(new reservation(tftype.getText(),Integer.parseInt(this.tfnbr_place.getText()),Integer.parseInt(this.tfprix.getText())));
               tab.refresh();

        JOptionPane.showMessageDialog(null, "reservation Ajouté");
//         numTelephone =tftelephone.getText();
//              sms s = new sms();
//              s.send("félicitaion bien incsrit",numTelephone);
//        tfname.clear();
//        tfmail.clear();
//        tfpassword.clear();
//        tfrole.clear();
//        tftelephone.clear();
        tab.refresh();
        loadDate();
         }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        reservation test = (reservation) tab.getSelectionModel().getSelectedItem();
        ReservationServices rt = new ReservationServices();
        rt.Supprimerreservation(test.getIdReservation());
        JOptionPane.showMessageDialog(null, "reservation supprimé");
        Notifications notificationBuilder = Notifications.create().title("notification").text("vous avez supprimé une reservation")
                .graphic(null).hideAfter(Duration.seconds(5)).position(Pos.TOP_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.showConfirm();
        loadDate();
    }

    @FXML
    private void modifier(ActionEvent event) {
         reservation test = (reservation) tab.getSelectionModel().getSelectedItem();
        ReservationServices rt = new ReservationServices();
        rt.ModiferReservation(test.getIdReservation(),new reservation(test.getIdReservation(),tftype.getText(), Integer.parseInt(this.tfnbr_place.getText()),Integer.parseInt(this.tfprix.getText())));
        JOptionPane.showMessageDialog(null, "reservation modifié");
        
        loadDate();
    }

    @FXML
    private void testVille(KeyEvent event) {
    }

    @FXML
    private void testOffre(KeyEvent event) {
    }

    @FXML
    private void getselected(MouseEvent event) {
         reservation test = (reservation) tab.getSelectionModel().getSelectedItem();

        int index = tab.getSelectionModel().getSelectedIndex();
    if (index <= -1){
    
        return;
    }
    tftype.setText(coltype.getCellData(index).toString());
        tfnbr_place.setText(colnbr_place.getCellData(index).toString());
        tfprix.setText(colprix.getCellData(index).toString());
        

    
    
    }

    @FXML
    private void Afficher(MouseEvent event) {
    }

    @FXML
    private void recaptcha(MouseEvent event) {
    }

    @FXML
    private void GoMenu(ActionEvent event) {
    }
    
}

