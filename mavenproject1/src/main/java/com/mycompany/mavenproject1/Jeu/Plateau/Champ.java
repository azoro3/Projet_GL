/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu.Plateau;

import com.mycompany.mavenproject1.Jeu.Tuiles;

/**
 *
 * @author Arthur
 */
public class Champ {
    private int x;
    private int t;
    private boolean estDesert;
    private Tuiles tuile;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public boolean isEstDesert() {
        return estDesert;
    }

    public void setEstDesert(boolean estDesert) {
        this.estDesert = estDesert;
    }

    public Tuiles getTuile() {
        return tuile;
    }

    public void setTuile(Tuiles tuile) {
        this.tuile = tuile;
    }
    
    
}
