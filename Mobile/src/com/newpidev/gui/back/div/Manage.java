package com.newpidev.gui.back.div;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Div;
import com.newpidev.entities.Types;
import com.newpidev.services.DivService;
import com.newpidev.services.TypesService;
import com.newpidev.utils.AlertUtils;

import java.util.ArrayList;

public class Manage extends Form {


    Div currentDiv;

    TextField nomTF;
    TextField numtelTF;
    TextField adresseTF;
    Label nomLabel;
    Label numtelLabel;
    Label adresseLabel;


    ArrayList<Types> listTypess;
    PickerComponent typesPC;
    Types selectedTypes = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentDiv == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentDiv = ShowAll.currentDiv;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] typesStrings;
        int typesIndex;
        typesPC = PickerComponent.createStrings("").label("Types");
        listTypess = TypesService.getInstance().getAll();
        typesStrings = new String[listTypess.size()];
        typesIndex = 0;
        for (Types types : listTypess) {
            typesStrings[typesIndex] = types.getTypes();
            typesIndex++;
        }
        if (listTypess.size() > 0) {
            typesPC.getPicker().setStrings(typesStrings);
            typesPC.getPicker().addActionListener(l -> selectedTypes = listTypess.get(typesPC.getPicker().getSelectedStringIndex()));
        } else {
            typesPC.getPicker().setStrings("");
        }


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");


        numtelLabel = new Label("Numtel : ");
        numtelLabel.setUIID("labelDefault");
        numtelTF = new TextField();
        numtelTF.setHint("Tapez le numtel");


        adresseLabel = new Label("Adresse : ");
        adresseLabel.setUIID("labelDefault");
        adresseTF = new TextField();
        adresseTF.setHint("Tapez le adresse");


        if (currentDiv == null) {


            manageButton = new Button("Ajouter");
        } else {

            nomTF.setText(currentDiv.getNom());
            numtelTF.setText(String.valueOf(currentDiv.getNumtel()));
            adresseTF.setText(currentDiv.getAdresse());

            typesPC.getPicker().setSelectedString(currentDiv.getTypes().getTypes());
            selectedTypes = currentDiv.getTypes();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                nomLabel, nomTF,
                numtelLabel, numtelTF,
                adresseLabel, adresseTF,
                typesPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentDiv == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = DivService.getInstance().add(
                            new Div(


                                    selectedTypes,
                                    nomTF.getText(),
                                    (int) Float.parseFloat(numtelTF.getText()),
                                    adresseTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Div ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de div. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = DivService.getInstance().edit(
                            new Div(
                                    currentDiv.getId(),


                                    selectedTypes,
                                    nomTF.getText(),
                                    (int) Float.parseFloat(numtelTF.getText()),
                                    adresseTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Div modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de div. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (numtelTF.getText().equals("")) {
            Dialog.show("Avertissement", "Numtel vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(numtelTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", numtelTF.getText() + " n'est pas un nombre valide (numtel)", new Command("Ok"));
            return false;
        }


        if (adresseTF.getText().equals("")) {
            Dialog.show("Avertissement", "Adresse vide", new Command("Ok"));
            return false;
        }


        if (selectedTypes == null) {
            Dialog.show("Avertissement", "Veuillez choisir un types", new Command("Ok"));
            return false;
        }


        return true;
    }
}