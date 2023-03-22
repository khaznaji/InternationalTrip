package com.newpidev.gui.back.evenement;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.entities.Evenement;
import com.newpidev.services.EvenementService;
import com.newpidev.utils.AlertUtils;
import com.newpidev.utils.Statics;

import java.io.IOException;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Evenement currentEvenement;

    TextField nomTF;
    TextField typeTF;
    TextField imageTF;
    TextField nbrPlaceTF;
    TextField prixTF;
    Label nomLabel;
    Label typeLabel;
    Label imageLabel;
    Label nbrPlaceLabel;
    Label prixLabel;
    PickerComponent dateTF;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentEvenement == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentEvenement = ShowAll.currentEvenement;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");


        typeLabel = new Label("Type : ");
        typeLabel.setUIID("labelDefault");
        typeTF = new TextField();
        typeTF.setHint("Tapez le type");


        nbrPlaceLabel = new Label("NbrPlace : ");
        nbrPlaceLabel.setUIID("labelDefault");
        nbrPlaceTF = new TextField();
        nbrPlaceTF.setHint("Tapez le nbrPlace");


        dateTF = PickerComponent.createDate(null).label("Date");


        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentEvenement == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {
            nomTF.setText(currentEvenement.getNom());
            typeTF.setText(currentEvenement.getType());

            nbrPlaceTF.setText(String.valueOf(currentEvenement.getNbrPlace()));
            dateTF.getPicker().setDate(currentEvenement.getDate());
            prixTF.setText(String.valueOf(currentEvenement.getPrix()));


            if (currentEvenement.getImage() != null) {
                selectedImage = currentEvenement.getImage();
                String url = Statics.EVENEMENT_IMAGE_URL + currentEvenement.getImage();
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
                nomLabel, nomTF,
                typeLabel, typeTF,

                nbrPlaceLabel, nbrPlaceTF,
                dateTF,
                prixLabel, prixTF,

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

        if (currentEvenement == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = EvenementService.getInstance().add(
                            new Evenement(


                                    nomTF.getText(),
                                    typeTF.getText(),
                                    selectedImage,
                                    (int) Float.parseFloat(nbrPlaceTF.getText()),
                                    dateTF.getPicker().getDate(),
                                    Float.parseFloat(prixTF.getText())
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Evenement ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de evenement. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = EvenementService.getInstance().edit(
                            new Evenement(
                                    currentEvenement.getId(),


                                    nomTF.getText(),
                                    typeTF.getText(),
                                    selectedImage,
                                    (int) Float.parseFloat(nbrPlaceTF.getText()),
                                    dateTF.getPicker().getDate(),
                                    Float.parseFloat(prixTF.getText())

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Evenement modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de evenement. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }


        if (typeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Type vide", new Command("Ok"));
            return false;
        }


        if (nbrPlaceTF.getText().equals("")) {
            Dialog.show("Avertissement", "NbrPlace vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nbrPlaceTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nbrPlaceTF.getText() + " n'est pas un nombre valide (nbrPlace)", new Command("Ok"));
            return false;
        }


        if (dateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la date", new Command("Ok"));
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


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}