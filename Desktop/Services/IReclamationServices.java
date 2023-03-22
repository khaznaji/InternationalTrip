/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entite.Reclamation;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface IReclamationServices {
   public int ajouterReclamation(Reclamation r);
   //  public int supprimerReclamation(Reclamation r);
     public int modifierReclamation(Reclamation r);
     public ArrayList<Reclamation> afficherReclamation();   
}
