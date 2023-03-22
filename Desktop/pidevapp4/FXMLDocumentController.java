package pidevapp4;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button selectDateButton;
    @FXML
    private DatePicker datePicker1;
    @FXML
    private Label outputLabel;
    @FXML
    private Button resetDateButton;
    @FXML
    private Button todayButton;
    @FXML
    private Button defaultButton;
    @FXML
    private DatePicker datePicker2;
    @FXML
    private Button compareDateButton;
    @FXML
    private Label compareLabel;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        //LocalDate localDate1 = 
    }    

    @FXML
    private void selectDateButtonHandler(ActionEvent event) 
    {
        LocalDate localDate1 = datePicker1.getValue();
        
        outputLabel.setText ("You selected " + localDate1);
    }

    @FXML
    private void resetDateButtonHandler(ActionEvent event) 
    {
        datePicker1.setValue (null);
    }

    @FXML
    private void todayButtonHandler(ActionEvent event) 
    {
        //LocalDate today = LocalDate.now();
        //datePicker1.setValue (today);
        datePicker1.setValue (LocalDate.now());
    }

    @FXML
    private void defaultButtonHandler(ActionEvent event) 
    {
        LocalDate defaultDate = LocalDate.of (2021, 8, 1);
        
        datePicker1.setValue (defaultDate);
    }

    @FXML
    private void compareDateButtonHandler(ActionEvent event) 
    {
        LocalDate localDate1 = datePicker1.getValue();
        LocalDate localDate2 = datePicker2.getValue();
        
        if (localDate1 == null)
            compareLabel.setText ("First Date is null");
        else if (localDate2 == null)
            compareLabel.setText ("Second Date is null");
        else
        {
            // Date are NOT null
            int result = localDate1.compareTo (localDate2);
            
            if (result < 0)
                compareLabel.setText ("First Date < Second Date");
            else if (result > 0)
                compareLabel.setText ("First Date > Second Date");
            else 
                compareLabel.setText ("First Date = Second Date");
        }
        
    }
     @FXML
    private void Retour(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidev/GestionDiv.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
}