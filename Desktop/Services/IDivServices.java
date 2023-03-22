package Services;

import java.util.List;
import entite.Div;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public interface IDivServices {
     public void AjouterDiv(Div c);
public void SupprimerDiv(String nom);
//public void ModifierTrip(Trip p);
         public List<Div> afficher1();
        public int modifierH (Div c);
    
}
