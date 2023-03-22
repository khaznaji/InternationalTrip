/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Like;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public interface IFavServices {
     public int ajouterFav(Like l);
     public int supprimerFav(Like l);
     public ArrayList<Like> afficherFav();
     public ObservableList<Like> getDataTeam();
    
}
