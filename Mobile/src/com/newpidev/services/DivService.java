package com.newpidev.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.newpidev.entities.Div;
import com.newpidev.entities.Types;
import com.newpidev.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DivService {

    public static DivService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Div> listDivs;


    private DivService() {
        cr = new ConnectionRequest();
    }

    public static DivService getInstance() {
        if (instance == null) {
            instance = new DivService();
        }
        return instance;
    }

    public ArrayList<Div> getAll() {
        listDivs = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/div");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listDivs = getList();
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

        return listDivs;
    }

    private ArrayList<Div> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Div div = new Div(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        makeTypes((Map<String, Object>) obj.get("types")),
                        (String) obj.get("nom"),
                        (int) Float.parseFloat(obj.get("numtel").toString()),
                        (String) obj.get("adresse")

                );

                listDivs.add(div);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listDivs;
    }

    public Types makeTypes(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Types types = new Types();
        types.setId((int) Float.parseFloat(obj.get("id").toString()));
        types.setTypes((String) obj.get("types"));
        return types;
    }

    public int add(Div div) {
        return manage(div, false);
    }

    public int edit(Div div) {
        return manage(div, true);
    }

    public int manage(Div div, boolean isEdit) {    

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/div/edit");
            cr.addArgument("id", String.valueOf(div.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/div/add");
        }

        cr.addArgument("types", String.valueOf(div.getTypes().getId()));
        cr.addArgument("nom", div.getNom());
        cr.addArgument("numtel", String.valueOf(div.getNumtel()));
        cr.addArgument("adresse", div.getAdresse());


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

    public int delete(int divId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/div/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(divId));

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
