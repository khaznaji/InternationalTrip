/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import java.util.List;
import entite.Chauffeur;

/**
 *
 * @author DELL
 */
public interface IChauffeurServices {
     public void AjouterChauffeur(Chauffeur c);
public void SupprimerChauffeur(String nom);
//public void ModifierTrip(Trip p);
         public List<Chauffeur> afficher();
        public void ModifierChauffeur(Chauffeur c, String nom);
        public int modifier (Chauffeur c);
    
}
