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

public class HebergementService {

    public static HebergementService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Hebergement> listHebergements;

    

    private HebergementService() {
        cr = new ConnectionRequest();
    }

    public static HebergementService getInstance() {
        if (instance == null) {
            instance = new HebergementService();
        }
        return instance;
    }
    
    public ArrayList<Hebergement> getAll() {
        listHebergements = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/hebergement");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listHebergements = getList();
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

        return listHebergements;
    }

    private ArrayList<Hebergement> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Hebergement hebergement = new Hebergement(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        makePays((Map<String, Object>) obj.get("pays")),
                        (String) obj.get("titre"),
                        (String) obj.get("type"),
                        (int) Float.parseFloat(obj.get("prix").toString()),
                        (String) obj.get("image"),
                        (String) obj.get("adresse"),
                        (String) obj.get("periode"),
                        (String) obj.get("choix"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateH"))
                        
                );

                listHebergements.add(hebergement);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listHebergements;
    }
    
    public Pays makePays(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Pays pays = new Pays();
        pays.setId((int) Float.parseFloat(obj.get("id").toString()));
        pays.setPays((String) obj.get("pays"));
        return pays;
    }
    
    public int add(Hebergement hebergement) {
        return manage(hebergement, false, true);
    }

    public int edit(Hebergement hebergement, boolean imageEdited) {
        return manage(hebergement, true , imageEdited);
    }

    public int manage(Hebergement hebergement, boolean isEdit, boolean imageEdited) {
        
        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Hebergement.jpg");

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/hebergement/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(hebergement.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/hebergement/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", hebergement.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", hebergement.getImage());
        }

        cr.addArgumentNoEncoding("pays", String.valueOf(hebergement.getPays().getId()));
        cr.addArgumentNoEncoding("titre", hebergement.getTitre());
        cr.addArgumentNoEncoding("type", hebergement.getType());
        cr.addArgumentNoEncoding("prix", String.valueOf(hebergement.getPrix()));
        cr.addArgumentNoEncoding("image", hebergement.getImage());
        cr.addArgumentNoEncoding("adresse", hebergement.getAdresse());
        cr.addArgumentNoEncoding("periode", hebergement.getPeriode());
        cr.addArgumentNoEncoding("choix", hebergement.getChoix());
        cr.addArgumentNoEncoding("dateH", new SimpleDateFormat("dd-MM-yyyy").format(hebergement.getDateH()));
        

        
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

    public int delete(int hebergementId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/hebergement/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(hebergementId));

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
