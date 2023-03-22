package Services;

import java.util.List;
import entite.Hebergement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public interface IHebergementServices {
     public void AjouterHebergement(Hebergement c);
public void SupprimerHebergement(String titre);
//public void ModifierTrip(Trip p);
         public List<Hebergement> afficher1();
        public int modifierH (Hebergement c);
    
}
