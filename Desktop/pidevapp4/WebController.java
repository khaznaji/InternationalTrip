/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class WebController implements Initializable {

    


    WebEngine engine;
    @FXML
    private WebView webView;
    @FXML
    private Button textField;
private double WebZoom ;
private WebHistory history ; 
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        engine.load("https://mail.google.com/mail/u/0/#inbox");
        WebZoom=1;
        loadPage();
     
    }  

    private void loadPage() {
        engine.reload();
    }

    @FXML
    private void ZoomIn(ActionEvent event) {
    WebZoom+=0.25;
        webView.setZoom(1.25);
    }

    @FXML
    private void ZoomOut(ActionEvent event) {
            WebZoom-=0.25;

                webView.setZoom(0.75);

    }

    @FXML
    private void History(ActionEvent event) {
        history = engine.getHistory();
ObservableList<WebHistory.Entry> entries = history.getEntries();
        
for (WebHistory.Entry entry : entries) {
                  
                    System.out.println(entry);
    
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
