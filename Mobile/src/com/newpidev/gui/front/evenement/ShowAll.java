package com.newpidev.gui.front.evenement;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.entities.Evenement;
import com.newpidev.services.EvenementService;
import com.newpidev.utils.Statics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Evenement currentEvenement = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label nomLabel, typeLabel, imageLabel, nbrPlaceLabel, dateLabel, prixLabel;
    ImageViewer imageIV;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Evenements", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Evenement> listEvenements = EvenementService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher evenement par nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Evenement evenement : listEvenements) {
                if (evenement.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeEvenementModel(evenement);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listEvenements.size() > 0) {
            for (Evenement evenement : listEvenements) {
                Component model = makeEvenementModel(evenement);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {

    }

    private Container makeModelWithoutButtons(Evenement evenement) {
        Container evenementModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        evenementModel.setUIID("containerRounded");


        nomLabel = new Label("Nom : " + evenement.getNom());
        nomLabel.setUIID("labelDefault");

        typeLabel = new Label("Type : " + evenement.getType());
        typeLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + evenement.getImage());
        imageLabel.setUIID("labelDefault");

        nbrPlaceLabel = new Label("NbrPlace : " + evenement.getNbrPlace());
        nbrPlaceLabel.setUIID("labelDefault");

        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDate()));
        dateLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + evenement.getPrix());
        prixLabel.setUIID("labelDefault");

        if (evenement.getImage() != null) {
            String url = Statics.EVENEMENT_IMAGE_URL + evenement.getImage();
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

        evenementModel.addAll(
                imageIV,
                nomLabel, typeLabel, nbrPlaceLabel, dateLabel, prixLabel
        );

        return evenementModel;
    }

    private Component makeEvenementModel(Evenement evenement) {

        Container evenementModel = makeModelWithoutButtons(evenement);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        evenementModel.add(btnsContainer);

        return evenementModel;
    }

}