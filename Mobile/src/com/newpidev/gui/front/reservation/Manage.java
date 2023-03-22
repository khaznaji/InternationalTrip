package com.newpidev.gui.front.reservation;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Evenement;
import com.newpidev.entities.Reservation;
import com.newpidev.entities.User;
import com.newpidev.services.EvenementService;
import com.newpidev.services.ReservationService;
import com.newpidev.services.UserService;
import com.newpidev.utils.AlertUtils;

import java.util.ArrayList;

public class Manage extends Form {


    Reservation currentReservation;

    TextField typePaiementTF;
    TextField nbrPlaceTF;
    TextField prixTF;
    Label typePaiementLabel;
    Label nbrPlaceLabel;
    Label prixLabel;


    ArrayList<Evenement> listEvenements;
    PickerComponent evenementPC;
    Evenement selectedEvenement = null;
    ArrayList<User> listUsers;
    PickerComponent userPC;
    User selectedUser = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentReservation == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentReservation = ShowAll.currentReservation;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] evenementStrings;
        int evenementIndex;
        evenementPC = PickerComponent.createStrings("").label("Evenement");
        listEvenements = EvenementService.getInstance().getAll();
        evenementStrings = new String[listEvenements.size()];
        evenementIndex = 0;
        for (Evenement evenement : listEvenements) {
            evenementStrings[evenementIndex] = evenement.getNom();
            evenementIndex++;
        }
        if (listEvenements.size() > 0) {
            evenementPC.getPicker().setStrings(evenementStrings);
            evenementPC.getPicker().addActionListener(l -> selectedEvenement = listEvenements.get(evenementPC.getPicker().getSelectedStringIndex()));
        } else {
            evenementPC.getPicker().setStrings("");
        }

        String[] userStrings;
        int userIndex;
        userPC = PickerComponent.createStrings("").label("User");
        listUsers = UserService.getInstance().getAll();
        userStrings = new String[listUsers.size()];
        userIndex = 0;
        for (User user : listUsers) {
            userStrings[userIndex] = user.getNom();
            userIndex++;
        }
        if (listUsers.size() > 0) {
            userPC.getPicker().setStrings(userStrings);
            userPC.getPicker().addActionListener(l -> selectedUser = listUsers.get(userPC.getPicker().getSelectedStringIndex()));
        } else {
            userPC.getPicker().setStrings("");
        }


        typePaiementLabel = new Label("TypePaiement : ");
        typePaiementLabel.setUIID("labelDefault");
        typePaiementTF = new TextField();
        typePaiementTF.setHint("Tapez le typePaiement");


        nbrPlaceLabel = new Label("NbrPlace : ");
        nbrPlaceLabel.setUIID("labelDefault");
        nbrPlaceTF = new TextField();
        nbrPlaceTF.setHint("Tapez le nbrPlace");


        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");



        if (currentReservation == null) {


            manageButton = new Button("Ajouter");
        } else {


            typePaiementTF.setText(currentReservation.getTypePaiement());
            nbrPlaceTF.setText(String.valueOf(currentReservation.getNbrPlace()));
            prixTF.setText(String.valueOf(currentReservation.getPrix()));
            evenementPC.getPicker().setSelectedString(currentReservation.getEvenement().getNom());
            selectedEvenement = currentReservation.getEvenement();
            userPC.getPicker().setSelectedString(currentReservation.getUser().getNom());
            selectedUser = currentReservation.getUser();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                typePaiementLabel, typePaiementTF,
                nbrPlaceLabel, nbrPlaceTF,
                prixLabel, prixTF,
                evenementPC, userPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentReservation == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReservationService.getInstance().add(
                            new Reservation(


                                    selectedEvenement,
                                    selectedUser,
                                    typePaiementTF.getText(),
                                    (int) Float.parseFloat(nbrPlaceTF.getText()),
                                    Float.parseFloat(prixTF.getText()),
                                    0
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reservation ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReservationService.getInstance().edit(
                            new Reservation(
                                    currentReservation.getId(),


                                    selectedEvenement,
                                    selectedUser,
                                    typePaiementTF.getText(),
                                    (int) Float.parseFloat(nbrPlaceTF.getText()),
                                    Float.parseFloat(prixTF.getText()),
                                    0

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reservation modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (typePaiementTF.getText().equals("")) {
            Dialog.show("Avertissement", "TypePaiement vide", new Command("Ok"));
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


        if (selectedEvenement == null) {
            Dialog.show("Avertissement", "Veuillez choisir un evenement", new Command("Ok"));
            return false;
        }

        if (selectedUser == null) {
            Dialog.show("Avertissement", "Veuillez choisir un user", new Command("Ok"));
            return false;
        }


        return true;
    }
}