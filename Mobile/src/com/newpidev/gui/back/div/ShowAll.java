package com.newpidev.gui.back.div;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Div;
import com.newpidev.services.DivService;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Div currentDiv = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label typesLabel, nomLabel, numtelLabel, adresseLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Divs", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Div> listDivs = DivService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher div par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Div div : listDivs) {
                if (div.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeDivModel(div);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listDivs.size() > 0) {
            for (Div div : listDivs) {
                Component model = makeDivModel(div);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentDiv = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Div div) {
        Container divModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        divModel.setUIID("containerRounded");


        typesLabel = new Label("Types : " + div.getTypes());
        typesLabel.setUIID("labelDefault");

        nomLabel = new Label("Nom : " + div.getNom());
        nomLabel.setUIID("labelDefault");

        numtelLabel = new Label("Numtel : " + div.getNumtel());
        numtelLabel.setUIID("labelDefault");

        adresseLabel = new Label("Adresse : " + div.getAdresse());
        adresseLabel.setUIID("labelDefault");

        typesLabel = new Label("Types : " + div.getTypes().getTypes());
        typesLabel.setUIID("labelDefault");


        divModel.addAll(

                typesLabel, nomLabel, numtelLabel, adresseLabel
        );

        return divModel;
    }

    private Component makeDivModel(Div div) {

        Container divModel = makeModelWithoutButtons(div);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentDiv = div;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce div ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = DivService.getInstance().delete(div.getId());

                if (responseCode == 200) {
                    currentDiv = null;
                    dlg.dispose();
                    divModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du div. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        divModel.add(btnsContainer);

        return divModel;
    }

}