package com.newpidev.gui.back.like;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Div;
import com.newpidev.entities.Like;
import com.newpidev.services.DivService;
import com.newpidev.services.LikeService;
import com.newpidev.utils.AlertUtils;

import java.util.ArrayList;

public class Manage extends Form {


    Like currentLike;


    ArrayList<Div> listDivs;
    PickerComponent divPC;
    Div selectedDiv = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentLike == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentLike = ShowAll.currentLike;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] divStrings;
        int divIndex;
        divPC = PickerComponent.createStrings("").label("Div");
        listDivs = DivService.getInstance().getAll();
        divStrings = new String[listDivs.size()];
        divIndex = 0;
        for (Div div : listDivs) {
            divStrings[divIndex] = div.getNom();
            divIndex++;
        }
        if (listDivs.size() > 0) {
            divPC.getPicker().setStrings(divStrings);
            divPC.getPicker().addActionListener(l -> selectedDiv = listDivs.get(divPC.getPicker().getSelectedStringIndex()));
        } else {
            divPC.getPicker().setStrings("");
        }


        if (currentLike == null) {


            manageButton = new Button("Ajouter");
        } else {


            divPC.getPicker().setSelectedString(currentLike.getDiv().getNom());
            selectedDiv = currentLike.getDiv();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                divPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentLike == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = LikeService.getInstance().add(
                            new Like(


                                    selectedDiv
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Like ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de like. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = LikeService.getInstance().edit(
                            new Like(
                                    currentLike.getId(),


                                    selectedDiv

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Like modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de like. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (selectedDiv == null) {
            Dialog.show("Avertissement", "Veuillez choisir un div", new Command("Ok"));
            return false;
        }


        return true;
    }
}