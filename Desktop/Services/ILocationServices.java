/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import java.util.List;
import entite.Location;


/**
 *
 * @author DELL
 */
public interface ILocationServices {
      public void AjouterLocation(Location c);
public void SupprimerLocation(String model);
//public void ModifierTrip(Trip p);
         public List<Location> afficher1();
        public void ModifierLocation(Location c, String model);
        public int modifierL (Location c);
    
    
}
