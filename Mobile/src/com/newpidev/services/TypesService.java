package com.newpidev.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.newpidev.entities.Types;
import com.newpidev.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypesService {

    public static TypesService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Types> listTypess;


    private TypesService() {
        cr = new ConnectionRequest();
    }

    public static TypesService getInstance() {
        if (instance == null) {
            instance = new TypesService();
        }
        return instance;
    }

    public ArrayList<Types> getAll() {
        listTypess = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/types");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listTypess = getList();
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

        return listTypess;
    }

    private ArrayList<Types> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Types types = new Types(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("image"),
                        (String) obj.get("types")

                );

                listTypess.add(types);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listTypess;
    }

    public int add(Types types) {
        return manage(types, false, true);
    }

    public int edit(Types types, boolean imageEdited) {
        return manage(types, true, imageEdited);
    }

    public int manage(Types types, boolean isEdit, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Types.jpg");


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/types/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(types.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/types/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", types.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", types.getImage());
        }

        cr.addArgumentNoEncoding("image", types.getImage());
        cr.addArgumentNoEncoding("types", types.getTypes());


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

    public int delete(int typesId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/types/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(typesId));

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
