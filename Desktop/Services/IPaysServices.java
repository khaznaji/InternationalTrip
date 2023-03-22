package Services;

import entite.Pays;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public interface IPaysServices {
        public int ajouterPays(Pays p);
     public int supprimerPays(Pays p);
     public int modifierPays(Pays p);
     public ArrayList<Pays> afficherPays();
     public ObservableList<Pays> getDataTeam();
     public ArrayList<Pays> rechercherPays(String V,String C);
}
