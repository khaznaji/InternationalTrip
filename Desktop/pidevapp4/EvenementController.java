/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.EvenementServices;
import entite.event;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class EvenementController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button btajouter;
    @FXML
    private Button btsupprimer;
    @FXML
    private Button btmodifier;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tftype;
    @FXML
    private TableView<event> tab;
    @FXML
    private TableColumn<event, String> colnom;
    @FXML
    private TableColumn<event, String> coltype;
    @FXML
    private TableColumn<event, String> colimage;
    @FXML
    private TableColumn<event, Integer> colnbr_place;
    @FXML
    private TableColumn<event, Date> coldate;
    @FXML
    private TableColumn<event, Integer> colprix;
    @FXML
    private TableColumn<?, ?> col_periode2;
    @FXML
    private TextField tfnbr_place;
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
    private TextField tfrecherche;
    @FXML
    private Button btbrowse;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfprix;
    @FXML
    private Button btntridate;
    @FXML
    private Button btprint;
    @FXML
    private Button btnexcel;
    @FXML
    private ImageView importimage;
    @FXML
    private Button btvalider;
    @FXML
    private Button btprint1;
     String img="";
    List<String> type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          loadDate();
        type =new ArrayList();
        type.add("*.jpg");
         type.add("*.png");
    }  
    private void loadDate(){
    ObservableList<event> abList = FXCollections.observableArrayList();
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        colnbr_place.setCellValueFactory(new PropertyValueFactory<>("nbr_place"));

        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
                colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        
        

        EvenementServices rt = new EvenementServices();
        List old = rt.listeventid();
        abList.addAll(old);
        tab.setItems(abList);
        tab.refresh();
    }
    

    @FXML
    private void ajouter(ActionEvent event) {
        if(tfnom.getText().isEmpty() | tftype.getText().isEmpty() | tfnbr_place.getText().isEmpty()| tfprix.getText().isEmpty()){
            //JOptionPane.showMessageDialog(null, "Remplir les champs vides");
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText(null);
            al.setContentText("Remplir les champs vides");
            al.showAndWait();
        }else{
        EvenementServices rt = new EvenementServices();
        rt.Ajouterevent(new event(tfnom.getText(), tftype.getText(),img,Integer.parseInt(this.tfnbr_place.getText()),Date.valueOf(tfdate.getValue()),Integer.parseInt(this.tfprix.getText())));
        JOptionPane.showMessageDialog(null, "evenement Ajouté");
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
        event test = (event) tab.getSelectionModel().getSelectedItem();
        EvenementServices rt = new EvenementServices();
        rt.SupprimerEvenement(test.getIdEvmt());
        JOptionPane.showMessageDialog(null, "evenement supprimé");
        Notifications notificationBuilder = Notifications.create().title("notification").text("vous avez supprimé un evenement")
                .graphic(null).hideAfter(Duration.seconds(5)).position(Pos.TOP_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.showConfirm();
        loadDate();
    }

    @FXML
    private void modifier(ActionEvent event) {
        event test = (event) tab.getSelectionModel().getSelectedItem();
        EvenementServices rt = new EvenementServices();
        rt.ModiferEvenement(test.getIdEvmt(),new event(test.getIdEvmt(),tfnom.getText(),tftype.getText(),img, Integer.parseInt(this.tfnbr_place.getText()),Date.valueOf(tfdate.getValue()),Integer.parseInt(this.tfprix.getText())));
        JOptionPane.showMessageDialog(null, "evenement modifié");
        
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
         event test = (event) tab.getSelectionModel().getSelectedItem();

        int index = tab.getSelectionModel().getSelectedIndex();
    if (index <= -1){
    
        return;
    }
    tfnom.setText(colnom.getCellData(index).toString());
        tftype.setText(coltype.getCellData(index).toString());
        tfnbr_place.setText(colnbr_place.getCellData(index).toString());
        

    String d1= test.getDate().toString();
    LocalDate ss = LocalDate.parse(d1);
    tfdate.setValue(ss);
    tfprix.setText(colprix.getCellData(index).toString());
        
    }

    @FXML
    private void Afficher(MouseEvent event) {
    }

    @FXML
    private void testPeriode(KeyEvent event) {
    }

    @FXML
    private void recaptcha(MouseEvent event) {
    }

    @FXML
    private void importimage(ActionEvent event) {
        FileChooser f=new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png files", type));
        File fc=f.showOpenDialog(null);
        
        if(fc != null)
        {   
            System.out.println(fc.getName());
            img=fc.getName();
           FileSystem fileSys = FileSystems.getDefault();
           Path srcPath= fc.toPath();
           Path destPath= fileSys.getPath("C:\\xampp\\img"+fc.getName());
            try {
                Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(srcPath.toString());
            Image i = new Image(fc.getAbsoluteFile().toURI().toString());
            importimage.setImage(i);
    }
    }

    @FXML
    private void trierDate(ActionEvent event) {
    }

    @FXML
    private void print(ActionEvent event) {
    }

    @FXML
    private void excel(ActionEvent event) {
    }

    @FXML
    private void recherchety(ActionEvent event) {
    }

    @FXML
    private void GoMenu(ActionEvent event) 
         throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/Menu.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
