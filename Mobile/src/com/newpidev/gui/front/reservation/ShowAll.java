package com.newpidev.gui.front.reservation;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Reservation;
import com.newpidev.services.ReservationService;
import com.newpidev.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;

public class ShowAll extends Form {

    public static Reservation currentReservation = null;
    Form previous;
    Button addBtn;


    PickerComponent sortPicker;
    ArrayList<Component> componentModels;
    Label evenementLabel, userLabel, typePaiementLabel, nbrPlaceLabel, prixLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Reservations", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Reservation> listReservations = ReservationService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("Evenement", "User", "TypePaiement", "NbrPlace", "Prix", "PrixI").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listReservations);
            for (Reservation reservation : listReservations) {
                Component model = makeReservationModel(reservation);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listReservations.size() > 0) {
            for (Reservation reservation : listReservations) {
                Component model = makeReservationModel(reservation);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentReservation = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Reservation reservation) {
        Container reservationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reservationModel.setUIID("containerRounded");


        evenementLabel = new Label("Evenement : " + reservation.getEvenement());
        evenementLabel.setUIID("labelDefault");

        userLabel = new Label("User : " + reservation.getUser());
        userLabel.setUIID("labelDefault");

        typePaiementLabel = new Label("TypePaiement : " + reservation.getTypePaiement());
        typePaiementLabel.setUIID("labelDefault");

        nbrPlaceLabel = new Label("NbrPlace : " + reservation.getNbrPlace());
        nbrPlaceLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + reservation.getPrix());
        prixLabel.setUIID("labelDefault");

        evenementLabel = new Label("Evenement : " + reservation.getEvenement().getNom());
        evenementLabel.setUIID("labelDefault");

        userLabel = new Label("User : " + reservation.getUser().getNom());
        userLabel.setUIID("labelDefault");


        reservationModel.addAll(

                evenementLabel, userLabel, typePaiementLabel, nbrPlaceLabel, prixLabel
        );

        return reservationModel;
    }

    private Component makeReservationModel(Reservation reservation) {

        Container reservationModel = makeModelWithoutButtons(reservation);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentReservation = reservation;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce reservation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ReservationService.getInstance().delete(reservation.getId());

                if (responseCode == 200) {
                    currentReservation = null;
                    dlg.dispose();
                    reservationModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        reservationModel.add(btnsContainer);

        return reservationModel;
    }

}