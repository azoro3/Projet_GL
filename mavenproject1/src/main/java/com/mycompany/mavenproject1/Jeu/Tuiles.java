/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu;

import Reseau.InterfaceTuiles;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 *
 * @author Arthur
 */
public class Tuiles extends UnicastRemoteObject implements InterfaceTuiles {
    private String type;
    private int nbTravailleurs;
    private double x;
    private double y;
    private boolean isIrigue;
    private int num;
    public Tuiles(String type) throws RemoteException {
        super();
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getX() {
        return this.x;
    }

    public double getY() {
        return y;
    }

    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int i) {
        this.num = i;
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
