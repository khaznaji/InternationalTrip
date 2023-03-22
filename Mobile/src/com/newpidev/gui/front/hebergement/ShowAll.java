package com.newpidev.gui.front.hebergement;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.codename1.io.*;
import com.newpidev.entities.Hebergement;
import com.newpidev.services.HebergementService;
import com.newpidev.utils.Statics;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class ShowAll extends Form {

    Form previous; 
    
    Resources theme = UIManager.initFirstTheme("/theme");
    
    public static Hebergement currentHebergement = null;
    Button addBtn;

    
    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public ShowAll(Form previous) {
        super("Hebergements", new BoxLayout(BoxLayout.Y_AXIS));
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
        

        ArrayList<Hebergement> listHebergements = HebergementService.getInstance().getAll();
        
        componentModels = new ArrayList<>();
        
        sortPicker = PickerComponent.createStrings("Pays", "Titre", "Type", "Prix", "Image", "Adresse", "Periode", "Choix", "DateH").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listHebergements);
            for (Hebergement hebergement : listHebergements) {
                Component model = makeHebergementModel(hebergement);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);
        
        if (listHebergements.size() > 0) {
            for (Hebergement hebergement : listHebergements) {
                Component model = makeHebergementModel(hebergement);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentHebergement = null;
            new Manage(this).show();
        });
        
    }
    Label paysLabel   , titreLabel   , typeLabel   , prixLabel   , imageLabel   , adresseLabel   , periodeLabel   , choixLabel   , dateHLabel  ;
    
    ImageViewer imageIV;
    

    private Container makeModelWithoutButtons(Hebergement hebergement) {
        Container hebergementModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        hebergementModel.setUIID("containerRounded");
        
        
        paysLabel = new Label("Pays : " + hebergement.getPays());
        paysLabel.setUIID("labelDefault");
        
        titreLabel = new Label("Titre : " + hebergement.getTitre());
        titreLabel.setUIID("labelDefault");
        
        typeLabel = new Label("Type : " + hebergement.getType());
        typeLabel.setUIID("labelDefault");
        
        prixLabel = new Label("Prix : " + hebergement.getPrix());
        prixLabel.setUIID("labelDefault");
        
        imageLabel = new Label("Image : " + hebergement.getImage());
        imageLabel.setUIID("labelDefault");
        
        adresseLabel = new Label("Adresse : " + hebergement.getAdresse());
        adresseLabel.setUIID("labelDefault");
        
        periodeLabel = new Label("Periode : " + hebergement.getPeriode());
        periodeLabel.setUIID("labelDefault");
        
        choixLabel = new Label("Choix : " + hebergement.getChoix());
        choixLabel.setUIID("labelDefault");
        
        dateHLabel = new Label("DateH : " + new SimpleDateFormat("dd-MM-yyyy").format(hebergement.getDateH()));
        dateHLabel.setUIID("labelDefault");
        
        paysLabel = new Label("Pays : " + hebergement.getPays().getPays());
        paysLabel.setUIID("labelDefault");
        
        if (hebergement.getImage() != null) {
            String url = Statics.HEBERGEMENT_IMAGE_URL + hebergement.getImage();
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

        hebergementModel.addAll(
                imageIV,
                paysLabel, titreLabel, typeLabel, prixLabel, adresseLabel, periodeLabel, choixLabel, dateHLabel
        );

        return hebergementModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeHebergementModel(Hebergement hebergement) {

        Container hebergementModel = makeModelWithoutButtons(hebergement);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentHebergement = hebergement;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce hebergement ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = HebergementService.getInstance().delete(hebergement.getId());

                if (responseCode == 200) {
                    currentHebergement = null;
                    dlg.dispose();
                    hebergementModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du hebergement. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> share(hebergement));

        btnsContainer.add(BorderLayout.CENTER, btnAfficherScreenshot);
        
        hebergementModel.add(btnsContainer);

        return hebergementModel;
    }
    
    private void share(Hebergement hebergement) {
        Form form = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        form.add(makeModelWithoutButtons(hebergement));
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(
                com.codename1.ui.Display.getInstance().getDisplayWidth(),
                com.codename1.ui.Display.getInstance().getDisplayHeight()
        );
        form.revalidate();
        form.setVisible(true);
        form.paintComponent(screenshot.getGraphics(), true);
        form.removeAll();
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        Form screenShotForm = new Form("Partager hebergement", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(hebergement.toString());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        screenShotForm.show();
    }
    
}