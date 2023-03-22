package com.newpidev.gui.front.hebergement;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;

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

    
    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;
    

    Hebergement currentHebergement;

    TextField titreTF;TextField typeTF;TextField prixTF;TextField imageTF;TextField adresseTF;TextField periodeTF;TextField choixTF;
    Label titreLabel;Label typeLabel;Label prixLabel;Label imageLabel;Label adresseLabel;Label periodeLabel;Label choixLabel;
    PickerComponent dateHTF;
    
    ArrayList<Pays> listPayss;
    PickerComponent paysPC;
    Pays selectedPays = null;
    
    
    ImageViewer imageIV;
    Button selectImageButton;
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentHebergement == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentHebergement = ShowAll.currentHebergement;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        
        String[] paysStrings;
        int paysIndex;
        paysPC = PickerComponent.createStrings("").label("Pays");
        listPayss = PaysService.getInstance().getAll();
        paysStrings = new String[listPayss.size()];
        paysIndex = 0;
        for (Pays pays : listPayss) {
            paysStrings[paysIndex] = pays.getPays();
            paysIndex++;
        }
        if(listPayss.size() > 0) {
            paysPC.getPicker().setStrings(paysStrings);
            paysPC.getPicker().addActionListener(l -> selectedPays = listPayss.get(paysPC.getPicker().getSelectedStringIndex()));
        } else {
            paysPC.getPicker().setStrings("");
        }
        

        
        
        
        
        titreLabel = new Label("Titre : ");
        titreLabel.setUIID("labelDefault");
        titreTF = new TextField();
        titreTF.setHint("Tapez le titre");
        
        
        
        typeLabel = new Label("Type : ");
        typeLabel.setUIID("labelDefault");
        typeTF = new TextField();
        typeTF.setHint("Tapez le type");
        
        
        
        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");
        
        
        
        
        
        
        
        adresseLabel = new Label("Adresse : ");
        adresseLabel.setUIID("labelDefault");
        adresseTF = new TextField();
        adresseTF.setHint("Tapez le adresse");
        
        
        
        periodeLabel = new Label("Periode : ");
        periodeLabel.setUIID("labelDefault");
        periodeTF = new TextField();
        periodeTF.setHint("Tapez le periode");
        
        
        
        choixLabel = new Label("Choix : ");
        choixLabel.setUIID("labelDefault");
        choixTF = new TextField();
        choixTF.setHint("Tapez le choix");
        
        
        dateHTF = PickerComponent.createDate(null).label("DateH");
        
        
        
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentHebergement == null) {
            
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            
            
            manageButton = new Button("Ajouter");
        } else {
            
            titreTF.setText(currentHebergement.getTitre());
            typeTF.setText(currentHebergement.getType());
            prixTF.setText(String.valueOf(currentHebergement.getPrix()));
            
            adresseTF.setText(currentHebergement.getAdresse());
            periodeTF.setText(currentHebergement.getPeriode());
            choixTF.setText(currentHebergement.getChoix());
            dateHTF.getPicker().setDate(currentHebergement.getDateH());
            
            paysPC.getPicker().setSelectedString(currentHebergement.getPays().getPays());
            selectedPays = currentHebergement.getPays();
            
            
            if (currentHebergement.getImage() != null) {
                selectedImage = currentHebergement.getImage();
                String url = Statics.HEBERGEMENT_IMAGE_URL + currentHebergement.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            imageLabel, imageIV,
            selectImageButton,
            
            titreLabel, titreTF,
            typeLabel, typeTF,
            prixLabel, prixTF,
            
            adresseLabel, adresseTF,
            periodeLabel, periodeTF,
            choixLabel, choixTF,
            dateHTF,
            paysPC,
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });
        
        if (currentHebergement == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = HebergementService.getInstance().add(
                            new Hebergement(
                                    
                                    
                                    selectedPays,
                                    titreTF.getText(),
                                    typeTF.getText(),
                                    (int) Float.parseFloat(prixTF.getText()),
                                    selectedImage,
                                    adresseTF.getText(),
                                    periodeTF.getText(),
                                    choixTF.getText(),
                                    dateHTF.getPicker().getDate()
                            )
                    );
                    if (responseCode == 200) {
                         Dialog.show("Succés", "Hebergement ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de hebergement. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = HebergementService.getInstance().edit(
                            new Hebergement(
                                    currentHebergement.getId(),
                                    
                                    
                                    selectedPays,
                                    titreTF.getText(),
                                    typeTF.getText(),
                                    (int) Float.parseFloat(prixTF.getText()),
                                    selectedImage,
                                    adresseTF.getText(),
                                    periodeTF.getText(),
                                    choixTF.getText(),
                                    dateHTF.getPicker().getDate()

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                         Dialog.show("Succés", "Hebergement modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de hebergement. Code d'erreur : " + responseCode, new Command("Ok"));
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

        
        
        
        
        
        if (titreTF.getText().equals("")) {
            Dialog.show("Avertissement", "Titre vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (typeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Type vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Prix vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixTF.getText() + " n'est pas un nombre valide (prix)", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        
        if (adresseTF.getText().equals("")) {
            Dialog.show("Avertissement", "Adresse vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (periodeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Periode vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (choixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Choix vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        if (dateHTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateH", new Command("Ok"));
            return false;
        }
        

        
        if (selectedPays == null) {
            Dialog.show("Avertissement", "Veuillez choisir un pays", new Command("Ok"));
            return false;
        }
        

        
        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }
        
             
        return true;
    }
}