package com.newpidev.gui.front.locationc;


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

    

    Locationc currentLocationc;

    TextField modelTF;TextField dureeTF;
    Label modelLabel;Label dureeLabel;
    PickerComponent datelocTF;
    
    ArrayList<Chauffeur> listChauffeurs;
    PickerComponent chauffeurPC;
    Chauffeur selectedChauffeur = null;
    
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentLocationc == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentLocationc = ShowAll.currentLocationc;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        
        String[] chauffeurStrings;
        int chauffeurIndex;
        chauffeurPC = PickerComponent.createStrings("").label("Chauffeur");
        listChauffeurs = ChauffeurService.getInstance().getAll();
        chauffeurStrings = new String[listChauffeurs.size()];
        chauffeurIndex = 0;
        for (Chauffeur chauffeur : listChauffeurs) {
            chauffeurStrings[chauffeurIndex] = chauffeur.getNom();
            chauffeurIndex++;
        }
        if(listChauffeurs.size() > 0) {
            chauffeurPC.getPicker().setStrings(chauffeurStrings);
            chauffeurPC.getPicker().addActionListener(l -> selectedChauffeur = listChauffeurs.get(chauffeurPC.getPicker().getSelectedStringIndex()));
        } else {
            chauffeurPC.getPicker().setStrings("");
        }
        

        
        
        
        
        modelLabel = new Label("Model : ");
        modelLabel.setUIID("labelDefault");
        modelTF = new TextField();
        modelTF.setHint("Tapez le model");
        
        
        datelocTF = PickerComponent.createDate(null).label("Dateloc");
        
        
        
        dureeLabel = new Label("Duree : ");
        dureeLabel.setUIID("labelDefault");
        dureeTF = new TextField();
        dureeTF.setHint("Tapez le duree");
        
        
        

        if (currentLocationc == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            
            modelTF.setText(currentLocationc.getModel());
            datelocTF.getPicker().setDate(currentLocationc.getDateloc());
            dureeTF.setText(String.valueOf(currentLocationc.getDuree()));
            
            chauffeurPC.getPicker().setSelectedString(currentLocationc.getChauffeur().getNom());
            selectedChauffeur = currentLocationc.getChauffeur();
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            
            modelLabel, modelTF,
            datelocTF,
            dureeLabel, dureeTF,
            chauffeurPC,
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentLocationc == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = LocationcService.getInstance().add(
                            new Locationc(
                                    
                                    
                                    selectedChauffeur,
                                    modelTF.getText(),
                                    datelocTF.getPicker().getDate(),
                                    (int) Float.parseFloat(dureeTF.getText())
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Locationc ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de locationc. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = LocationcService.getInstance().edit(
                            new Locationc(
                                    currentLocationc.getId(),
                                    
                                    
                                    selectedChauffeur,
                                    modelTF.getText(),
                                    datelocTF.getPicker().getDate(),
                                    (int) Float.parseFloat(dureeTF.getText())

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Locationc modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de locationc. Code d'erreur : " + responseCode, new Command("Ok"));
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

        
        
        
        
        
        if (modelTF.getText().equals("")) {
            Dialog.show("Avertissement", "Model vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        if (datelocTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateloc", new Command("Ok"));
            return false;
        }
        
        
        if (dureeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Duree vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(dureeTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", dureeTF.getText() + " n'est pas un nombre valide (duree)", new Command("Ok"));
            return false;
        }
        
        
        

        
        if (selectedChauffeur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un chauffeur", new Command("Ok"));
            return false;
        }
        

        
             
        return true;
    }
}