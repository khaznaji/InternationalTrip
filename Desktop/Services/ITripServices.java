/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import entite.Trip;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author IHEB
 */

public interface ITripServices {
     public int ajouterTrip(Trip p);
     public int supprimerTrip(Trip p);
     public int modifierTrip(Trip p);
     public ArrayList<Trip> afficherTrip();
     public ObservableList<Trip> getDataTeam();
     public ArrayList<Trip> rechercherTrip(String V,String C);
    
}
