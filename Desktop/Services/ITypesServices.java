package Services;

import entite.Types;
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
public interface ITypesServices {
        public void ajouterTypes(Types p);
     public int supprimerTypes(Types p);
     public int modifierT(Types p);
     public ArrayList<Types> afficherTypes();
     public ObservableList<Types> getDataTeam();
     public ArrayList<Types> rechercherTypes(String V,String C);
}
