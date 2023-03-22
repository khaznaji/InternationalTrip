/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Hotel;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author DELL
 */
public interface IHotelServices {
     public int ajouterHotel(Hotel h);
     public int supprimerHotel(Hotel p);
     public int modifierHotel(Hotel p);
     public ArrayList<Hotel> afficherHotel();
     public ObservableList<Hotel> getDataTeam();
     public ArrayList<Hotel> rechercherHotel(String V,String C);
}
