/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu.Factory;

import com.mycompany.mavenproject1.Jeu.Tuiles;

import java.rmi.RemoteException;

/**
 *
 * @author Arthur
 */
public class TuilesFactory {
    private SimpleTuileFactory sTF;
    public TuilesFactory(){
        this.sTF=new SimpleTuileFactory();
    }
    
    public Tuiles genererTuiles(String type) throws RemoteException {
        Tuiles tuile=this.sTF.creerTuile(type);
        return tuile;
    }
    
}
