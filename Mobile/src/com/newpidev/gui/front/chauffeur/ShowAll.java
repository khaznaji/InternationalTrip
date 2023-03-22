package com.newpidev.gui.front.chauffeur;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.codename1.io.*;
import com.newpidev.entities.Chauffeur;
import com.newpidev.services.ChauffeurService;
import com.newpidev.utils.Statics;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class ShowAll extends Form {

    Form previous; 
    
    public static Chauffeur currentChauffeur = null;
    

    TextField searchTF;
    ArrayList<Component> componentModels;
    

    public ShowAll(Form previous) {
        super("Chauffeurs", new BoxLayout(BoxLayout.Y_AXIS));
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
        

        ArrayList<Chauffeur> listChauffeurs = ChauffeurService.getInstance().getAll();
        componentModels = new ArrayList<>();
        
        searchTF = new TextField("", "Chercher chauffeur par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Chauffeur chauffeur : listChauffeurs) {
                if (chauffeur.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeChauffeurModel(chauffeur);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);
        
        
        if (listChauffeurs.size() > 0) {
            for (Chauffeur chauffeur : listChauffeurs) {
                Component model = makeChauffeurModel(chauffeur);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        
    }
    Label nomLabel   , prenomLabel   , sexeLabel   , numLabel   , disponibiliteLabel  ;
    

    private Container makeModelWithoutButtons(Chauffeur chauffeur) {
        Container chauffeurModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        chauffeurModel.setUIID("containerRounded");
        
        
        nomLabel = new Label("Nom : " + chauffeur.getNom());
        nomLabel.setUIID("labelDefault");
        
        prenomLabel = new Label("Prenom : " + chauffeur.getPrenom());
        prenomLabel.setUIID("labelDefault");
        
        sexeLabel = new Label("Sexe : " + chauffeur.getSexe());
        sexeLabel.setUIID("labelDefault");
        
        numLabel = new Label("Num : " + chauffeur.getNum());
        numLabel.setUIID("labelDefault");
        
        disponibiliteLabel = new Label("Disponibilite : " + chauffeur.getDisponibilite());
        disponibiliteLabel.setUIID("labelDefault");
        

        chauffeurModel.addAll(
                
                nomLabel, prenomLabel, sexeLabel, numLabel, disponibiliteLabel
        );

        return chauffeurModel;
    }
    
    
    Container btnsContainer;

    private Component makeChauffeurModel(Chauffeur chauffeur) {

        Container chauffeurModel = makeModelWithoutButtons(chauffeur);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        
        chauffeurModel.add(btnsContainer);

        return chauffeurModel;
    }
    
}