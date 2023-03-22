package com.newpidev.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.newpidev.entities.*;
import com.newpidev.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChauffeurService {

    public static ChauffeurService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Chauffeur> listChauffeurs;

    

    private ChauffeurService() {
        cr = new ConnectionRequest();
    }

    public static ChauffeurService getInstance() {
        if (instance == null) {
            instance = new ChauffeurService();
        }
        return instance;
    }
    
    public ArrayList<Chauffeur> getAll() {
        listChauffeurs = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/chauffeur");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listChauffeurs = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listChauffeurs;
    }

    private ArrayList<Chauffeur> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Chauffeur chauffeur = new Chauffeur(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        (String) obj.get("nom"),
                        (String) obj.get("prenom"),
                        (String) obj.get("sexe"),
                        (int) Float.parseFloat(obj.get("num").toString()),
                        (String) obj.get("disponibilite")
                        
                );

                listChauffeurs.add(chauffeur);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return listChauffeurs;
    }
    
    public int add(Chauffeur chauffeur) {
        return manage(chauffeur, false);
    }

    public int edit(Chauffeur chauffeur) {
        return manage(chauffeur, true );
    }

    public int manage(Chauffeur chauffeur, boolean isEdit) {
        
        cr = new ConnectionRequest();

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/chauffeur/edit");
            cr.addArgument("id", String.valueOf(chauffeur.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/chauffeur/add");
        }
        
        cr.addArgument("nom", chauffeur.getNom());
        cr.addArgument("prenom", chauffeur.getPrenom());
        cr.addArgument("sexe", chauffeur.getSexe());
        cr.addArgument("num", String.valueOf(chauffeur.getNum()));
        cr.addArgument("disponibilite", chauffeur.getDisponibilite());
        
        
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int chauffeurId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/chauffeur/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(chauffeurId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
