package com.newpidev.gui.front.div;

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

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label typesLabel, nomLabel, numtelLabel, adresseLabel;
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

      

        divModel.add(btnsContainer);

        return divModel;
    }

}