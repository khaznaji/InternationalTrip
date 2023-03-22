package com.newpidev.gui.back.pays;


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

    

    Pays currentPays;

    TextField paysTF;
    Label paysLabel;
    
    
    
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentPays == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentPays = ShowAll.currentPays;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        

        
        
        paysLabel = new Label("Pays : ");
        paysLabel.setUIID("labelDefault");
        paysTF = new TextField();
        paysTF.setHint("Tapez le pays");
        
        
        

        if (currentPays == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            paysTF.setText(currentPays.getPays());
            
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            paysLabel, paysTF,
            
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentPays == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = PaysService.getInstance().add(
                            new Pays(
                                    
                                    
                                    paysTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                         Dialog.show("Succés", "Pays ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de pays. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = PaysService.getInstance().edit(
                            new Pays(
                                    currentPays.getId(),
                                    
                                    
                                    paysTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                         Dialog.show("Succés", "Pays modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de pays. Code d'erreur : " + responseCode, new Command("Ok"));
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

        
        
        if (paysTF.getText().equals("")) {
            Dialog.show("Avertissement", "Pays vide", new Command("Ok"));
            return false;
        }
        
        
        

        

        
             
        return true;
    }
}