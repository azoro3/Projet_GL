/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu;

import java.util.Random;

/**
 *
 * @author Arthur
 */
public class Tuiles {
    private String type;
    private int nbTravailleurs;

    public Tuiles(String type) {
        this.type=type;
        Random r=new Random();
        this.nbTravailleurs = 1 + r.nextInt(3 - 1);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbTravailleurs() {
        return nbTravailleurs;
    }
    
    
}
