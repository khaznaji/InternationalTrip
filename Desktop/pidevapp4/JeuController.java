/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class JeuController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Label winnerText;
     private int playerTurn = 0;

    ArrayList<Button> buttons;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         buttons = new ArrayList<>(Arrays.asList(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
        // TODO
    }    

    @FXML
    private void RestartGame(ActionEvent event) {
         buttons.forEach(this::resetButton);
        winnerText.setText("Tic-Tac-Toe");
    }
     private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setPlayerSymbol(button);
            button.setDisable(true);
            winningGame();
        });
    }
       public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    public void setPlayerSymbol(Button button){
        if(playerTurn % 2 == 0){
            button.setText("X");
            playerTurn = 1;
        } else{
            button.setText("O");
            playerTurn = 0;
        }
    }

    private void winningGame()
    {
        String b1 = btn1.getText();
        String b2 = btn2.getText();
        String b3 = btn3.getText();
       
        String b4 = btn4.getText();
        String b5 = btn5.getText();
        String b6 = btn6.getText();
        
        String b7 = btn7.getText();
        String b8 = btn8.getText();
        String b9 = btn9.getText();
        
        
        // PLAYER X CODING
        
        if(b1 == ("X") && b2 ==("X") && b3 == ("X"))
        {
            winnerText.setText("X won!");
           
            
           // btn1.setBackground(Color.ORANGE);
           // btn2.setBackground(Color.ORANGE);
           // btn3.setBackground(Color.ORANGE);
            
        }
        
        if(b4 == ("X") && b5 ==("X") && b6 == ("X"))
        {
             winnerText.setText("X won!");
           
            
                    
        }
         
        if(b7 == ("X") && b8 ==("X") && b9 == ("X"))
        {
               winnerText.setText("X won!");                     
        }
        
        if(b1 == ("X") && b4 ==("X") && b7 == ("X"))
        {
             winnerText.setText("X won!");         
        }
        
        if(b2 == ("X") && b5 ==("X") && b8 == ("X"))
        {
             winnerText.setText("X won!");          
        }
        if(b3 == ("X") && b6 ==("X") && b9 == ("X"))
        {
             winnerText.setText("X won!");          
        }
        
        
        if(b1 == ("X") && b5 ==("X") && b9 == ("X"))
        {
             winnerText.setText("X won!");          
        }
        
        if(b3 == ("X") && b5 ==("X") && b7 == ("X"))
        {
             winnerText.setText("X won!");           
        }
        
        
        
        // PLAYER O CODING
        
        
        if(b1 == ("O") && b2 ==("O") && b3 == ("O"))
        {
             winnerText.setText("O won!");
            
        }
        
        if(b4 == ("O") && b5 ==("O") && b6 == ("O"))
        {
             winnerText.setText("O won!");           
        }
         
        if(b7 == ("O") && b8 ==("O") && b9 == ("O"))
        {
             winnerText.setText("O won!");           
        }
        
        if(b1 == ("O") && b4 ==("O") && b7 == ("O"))
        {
             winnerText.setText("O won!");           
        }
        
        if(b2 == ("O") && b5 ==("O") && b8 == ("O"))
        {
             winnerText.setText("O won!");           
        }
        if(b3 == ("O") && b6 ==("O") && b9 == ("O"))
        {
             winnerText.setText("O won!");           
        }
        
        
        if(b1 == ("O") && b5 ==("O") && b9 == ("O"))
        {
             winnerText.setText("O won!");           
        }
        
        if(b3 == ("O") && b5 ==("O") && b7 == ("O"))
        {
             winnerText.setText("O won!");           
        }
        
        
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionDiv.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
