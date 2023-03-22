package com.newpidev.gui.front;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.gui.Login;

public class AccueilFront extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;

    public AccueilFront() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        ImageViewer userImage = new ImageViewer(theme.getImage("default.jpg").fill(200, 200));
        userImage.setUIID("candidatImage");
        label = new Label("Front");
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setUIID("buttonLogout");
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.WEST, userImage);
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeHebergementsButton(), 
                makePayssButton(), 
                makeChauffeursButton(), 
                makeLocationcsButton(),
                makeEvenementsButton(),
                makeReservationsButton(),
                  makeDivsButton(),
                makeLikesButton(),
                makeTypessButton()

        );

        this.add(menuContainer);
    }
    
    private Button makeHebergementsButton() {
        Button button = new Button("Hebergements");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.hebergement.ShowAll(this).show());
        return button;
    }
    private Button makePayssButton() {
        Button button = new Button("Payss");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.pays.ShowAll(this).show());
        return button;
    }
    private Button makeChauffeursButton() {
        Button button = new Button("Chauffeurs");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.chauffeur.ShowAll(this).show());
        return button;
    }
    private Button makeLocationcsButton() {
        Button button = new Button("Locationcs");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.locationc.ShowAll(this).show());
        return button;
    }
    
    

    private Button makeEvenementsButton() {
        Button button = new Button("Evenements");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.evenement.ShowAll(this).show());
        return button;
    }

    private Button makeReservationsButton() {
        Button button = new Button("Reservations");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.reservation.ShowAll(this).show());
        return button;
    }
 private Button makeDivsButton() {
        Button button = new Button("Divs");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.div.ShowAll(this).show());
        return button;
    }

    private Button makeLikesButton() {
        Button button = new Button("Likes");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.like.ShowAll(this).show());
        return button;
    }

    private Button makeTypessButton() {
        Button button = new Button("Typess");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.newpidev.gui.front.types.ShowAll(this).show());
        return button;
    }
    
}