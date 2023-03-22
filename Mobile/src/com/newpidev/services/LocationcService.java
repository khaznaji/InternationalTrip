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

public class LocationcService {

    public static LocationcService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Locationc> listLocationcs;

    

    private LocationcService() {
        cr = new ConnectionRequest();
    }

    public static LocationcService getInstance() {
        if (instance == null) {
            instance = new LocationcService();
        }
        return instance;
    }
    
    public ArrayList<Locationc> getAll() {
        listLocationcs = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/locationc");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listLocationcs = getList();
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

        return listLocationcs;
    }

    private ArrayList<Locationc> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Locationc locationc = new Locationc(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        makeChauffeur((Map<String, Object>) obj.get("chauffeur")),
                        (String) obj.get("model"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateloc")),
                        (int) Float.parseFloat(obj.get("duree").toString())
                        
                );

                listLocationcs.add(locationc);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listLocationcs;
    }
    
    public Chauffeur makeChauffeur(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setId((int) Float.parseFloat(obj.get("id").toString()));
        chauffeur.setNom((String) obj.get("nom"));
        return chauffeur;
    }
    
    public int add(Locationc locationc) {
        return manage(locationc, false);
    }

    public int edit(Locationc locationc) {
        return manage(locationc, true );
    }

    public int manage(Locationc locationc, boolean isEdit) {
        
        cr = new ConnectionRequest();

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/locationc/edit");
            cr.addArgument("id", String.valueOf(locationc.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/locationc/add");
        }
        
        cr.addArgument("chauffeur", String.valueOf(locationc.getChauffeur().getId()));
        cr.addArgument("model", locationc.getModel());
        cr.addArgument("dateloc", new SimpleDateFormat("dd-MM-yyyy").format(locationc.getDateloc()));
        cr.addArgument("duree", String.valueOf(locationc.getDuree()));
        
        
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

    public int delete(int locationcId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/locationc/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(locationcId));

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
