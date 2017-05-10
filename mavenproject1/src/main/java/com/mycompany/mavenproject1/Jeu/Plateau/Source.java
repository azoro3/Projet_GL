/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu.Plateau;

import Reseau.InterfaceSource;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Arthur
 */
public class Source extends UnicastRemoteObject implements InterfaceSource {

    private int x;
    private int y;

    public Source() throws RemoteException {
        super();
        this.x = rand3(0,13);
        this.y = rand3(0,10);
    }

    /**
     * Génère un entier multiple de 3 compris entre min (inclus) et max (exclus).
     * @param min
     * @param max
     * @return 
     */
    public int rand3(int min, int max) {
        return (int) ((Math.random() * (max - min) + min) / 3) * 3;
    }




    public String tostring() {
        return "Source{" + "x=" + x + ", y=" + y + '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
