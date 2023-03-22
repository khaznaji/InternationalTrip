package com.newpidev.entities;

import java.util.Date;
import com.newpidev.utils.*;

public class Pays implements Comparable<Pays> {
    
    private int id;
     private String pays;
    
    public Pays() {}

    public Pays(int id, String pays) {
        this.id = id;
        this.pays = pays;
    }

    public Pays(String pays) {
        this.pays = pays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
    
    
    @Override
    public int compareTo(Pays pays) {
        switch (Statics.compareVar) {
            case "Pays":
                 return this.getPays().compareTo(pays.getPays());
            
            default:
                return 0;
        }
    }
    
}