package com.newpidev.gui.back.types;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.entities.Types;
import com.newpidev.services.TypesService;
import com.newpidev.utils.Statics;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Types currentTypes = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label imageLabel, typesLabel;
    ImageViewer imageIV;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Typess", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Types> listTypess = TypesService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher types par Types");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Types types : listTypess) {
                if (types.getTypes().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeTypesModel(types);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listTypess.size() > 0) {
            for (Types types : listTypess) {
                Component model = makeTypesModel(types);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentTypes = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Types types) {
        Container typesModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        typesModel.setUIID("containerRounded");


        imageLabel = new Label("Image : " + types.getImage());
        imageLabel.setUIID("labelDefault");

        typesLabel = new Label("Types : " + types.getTypes());
        typesLabel.setUIID("labelDefault");

        if (types.getImage() != null) {
            String url = Statics.TYPES_IMAGE_URL + types.getImage();
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

        typesModel.addAll(
                imageIV,
                typesLabel
        );

        return typesModel;
    }

    private Component makeTypesModel(Types types) {

        Container typesModel = makeModelWithoutButtons(types);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentTypes = types;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce types ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = TypesService.getInstance().delete(types.getId());

                if (responseCode == 200) {
                    currentTypes = null;
                    dlg.dispose();
                    typesModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du types. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        typesModel.add(btnsContainer);

        return typesModel;
    }

}