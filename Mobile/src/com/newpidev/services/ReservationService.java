package com.newpidev.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.newpidev.entities.Evenement;
import com.newpidev.entities.Reservation;
import com.newpidev.entities.User;
import com.newpidev.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationService {

    public static ReservationService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Reservation> listReservations;


    private ReservationService() {
        cr = new ConnectionRequest();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public ArrayList<Reservation> getAll() {
        listReservations = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/reservation");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listReservations = getList();
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

        return listReservations;
    }

    private ArrayList<Reservation> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Reservation reservation = new Reservation(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        makeEvenement((Map<String, Object>) obj.get("evenement")),
                        makeUser((Map<String, Object>) obj.get("user")),
                        (String) obj.get("typePaiement"),
                        (int) Float.parseFloat(obj.get("nbrPlace").toString()),
                        Float.parseFloat(obj.get("prix").toString()),
                        Float.parseFloat(obj.get("prixI").toString())

                );

                listReservations.add(reservation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listReservations;
    }

    public Evenement makeEvenement(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Evenement evenement = new Evenement();
        evenement.setId((int) Float.parseFloat(obj.get("id").toString()));
        evenement.setNom((String) obj.get("nom"));
        return evenement;
    }

    public User makeUser(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        User user = new User();
        user.setId((int) Float.parseFloat(obj.get("id").toString()));
        user.setNom((String) obj.get("nom"));
        return user;
    }

    public int add(Reservation reservation) {
        return manage(reservation, false);
    }

    public int edit(Reservation reservation) {
        return manage(reservation, true);
    }

    public int manage(Reservation reservation, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/reservation/edit");
            cr.addArgument("id", String.valueOf(reservation.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/reservation/add");
        }

        cr.addArgument("evenement", String.valueOf(reservation.getEvenement().getId()));
        cr.addArgument("user", String.valueOf(reservation.getUser().getId()));
        cr.addArgument("typePaiement", reservation.getTypePaiement());
        cr.addArgument("nbrPlace", String.valueOf(reservation.getNbrPlace()));
        cr.addArgument("prix", String.valueOf(reservation.getPrix()));
        cr.addArgument("prixI", String.valueOf(reservation.getPrixI()));


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

    public int delete(int reservationId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/reservation/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(reservationId));

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
