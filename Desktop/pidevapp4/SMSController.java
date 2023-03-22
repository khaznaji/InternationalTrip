/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class SMSController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfnum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Send(ActionEvent event) {
         try {
            // Construct data
            String apiKey = "apikey=" + "Mzk2MTVhNzQ1ODU4NDE2NDZhNGI0MjZmNDU2ZjM3MzU=	";
            Random rand = new Random();
             int OTP = rand.nextInt(999999);
             String name = tfnom.getText();
            String message = "&message=" + "Salut " +name+ "ton OTP est " +OTP;
            String sender = "&sender=" + "International Trip";
            String numbers = "&numbers=" + tfnum.getText();
 
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
             
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            System.out.println(stringBuffer.toString());
            rd.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Alert!!");
		alert.setHeaderText("Results:");
		alert.setContentText("SMS sent successfully");

		alert.showAndWait();
 
 
        } catch (Exception e) {
           e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Alert!!");
		alert.setHeaderText("Results:");
		alert.setContentText("SMS Error!!");

		alert.showAndWait();
           
        }
    }

    }
    

