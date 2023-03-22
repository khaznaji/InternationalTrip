package com.newpidev.gui.back.like;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Like;
import com.newpidev.services.LikeService;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Like currentLike = null;
    Form previous;
    Button addBtn;
    Label divLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Likes", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Like> listLikes = LikeService.getInstance().getAll();


        if (listLikes.size() > 0) {
            for (Like like : listLikes) {
                Component model = makeLikeModel(like);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentLike = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Like like) {
        Container likeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        likeModel.setUIID("containerRounded");


        divLabel = new Label("Div : " + like.getDiv());
        divLabel.setUIID("labelDefault");

        divLabel = new Label("Div : " + like.getDiv().getNom());
        divLabel.setUIID("labelDefault");


        likeModel.addAll(

                divLabel
        );

        return likeModel;
    }

    private Component makeLikeModel(Like like) {

        Container likeModel = makeModelWithoutButtons(like);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentLike = like;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce like ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = LikeService.getInstance().delete(like.getId());

                if (responseCode == 200) {
                    currentLike = null;
                    dlg.dispose();
                    likeModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du like. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        likeModel.add(btnsContainer);

        return likeModel;
    }

}