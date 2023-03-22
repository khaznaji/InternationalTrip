package com.newpidev.gui.back.chauffeur;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.entities.*;
import com.newpidev.services.*;
import com.newpidev.utils.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Manage extends Form {

    

    Chauffeur currentChauffeur;

    TextField nomTF;TextField prenomTF;TextField sexeTF;TextField numTF;TextField disponibiliteTF;
    Label nomLabel;Label prenomLabel;Label sexeLabel;Label numLabel;Label disponibiliteLabel;
    
    
    
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentChauffeur == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentChauffeur = ShowAll.currentChauffeur;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        

        
        
        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");
        
        
        
        prenomLabel = new Label("Prenom : ");
        prenomLabel.setUIID("labelDefault");
        prenomTF = new TextField();
        prenomTF.setHint("Tapez le prenom");
        
        
        
        sexeLabel = new Label("Sexe : ");
        sexeLabel.setUIID("labelDefault");
        sexeTF = new TextField();
        sexeTF.setHint("Tapez le sexe");
        
        
        
        numLabel = new Label("Num : ");
        numLabel.setUIID("labelDefault");
        numTF = new TextField();
        numTF.setHint("Tapez le num");
        
        
        
        disponibiliteLabel = new Label("Disponibilite : ");
        disponibiliteLabel.setUIID("labelDefault");
        disponibiliteTF = new TextField();
        disponibiliteTF.setHint("Tapez le disponibilite");
        
        
        

        if (currentChauffeur == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            nomTF.setText(currentChauffeur.getNom());
            prenomTF.setText(currentChauffeur.getPrenom());
            sexeTF.setText(currentChauffeur.getSexe());
            numTF.setText(String.valueOf(currentChauffeur.getNum()));
            disponibiliteTF.setText(currentChauffeur.getDisponibilite());
            
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            nomLabel, nomTF,
            prenomLabel, prenomTF,
            sexeLabel, sexeTF,
            numLabel, numTF,
            disponibiliteLabel, disponibiliteTF,
            
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentChauffeur == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ChauffeurService.getInstance().add(
                            new Chauffeur(
                                    
                                    
                                    nomTF.getText(),
                                    prenomTF.getText(),
                                    sexeTF.getText(),
                                    (int) Float.parseFloat(numTF.getText()),
                                    disponibiliteTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Chauffeur ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de chauffeur. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ChauffeurService.getInstance().edit(
                            new Chauffeur(
                                    currentChauffeur.getId(),
                                    
                                    
                                    nomTF.getText(),
                                    prenomTF.getText(),
                                    sexeTF.getText(),
                                    (int) Float.parseFloat(numTF.getText()),
                                    disponibiliteTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Chauffeur modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de chauffeur. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh(){
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        
        
        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (prenomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Prenom vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (sexeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Sexe vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (numTF.getText().equals("")) {
            Dialog.show("Avertissement", "Num vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(numTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", numTF.getText() + " n'est pas un nombre valide (num)", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (disponibiliteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Disponibilite vide", new Command("Ok"));
            return false;
        }
        
        
        

        

        
             
        return true;
    }
}