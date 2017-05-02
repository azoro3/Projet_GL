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
    private int x;
    private int y;
    private boolean isIrigue;

    public Tuiles(String type) {
        this.type=type;
        Random r=new Random();
        this.nbTravailleurs = 1 + r.nextInt(3 - 1);
    }
    /**
     * 
     * @return un objet tuiles au format JSON
     */
    public String toJSON(){
        String tuileJSON ="{\"type\":\""+this.type+"\",\"x\":"+this.x+",\"y\":"+this.y+",\"isIrigue\" :"+this.isIrigue+"}";
        return tuileJSON;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return y;
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
    public void setNbTravailleurs(int nbTravailleurs){
        this.nbTravailleurs=nbTravailleurs;
    }
    public void setIrigue(boolean isIrigue){
        this.isIrigue=isIrigue;
    }
    public boolean getIrigue(){
        return this.isIrigue;
    }
    
    
}
