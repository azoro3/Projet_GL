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
public class SimpleTuileFactory {

    public Tuiles creerTuile(String type) throws RemoteException {
        Tuiles t=null;
        switch(type){
            case("piment"):t=new Tuiles("piment");break;
            case("haricot"):t=new Tuiles("haricot");break;
            case("banane"):t=new Tuiles("banane");break;
            case("patate"):t=new Tuiles("patate");break;
            case("sucre"):t=new Tuiles("sucre");break;
        }
        return t;
    }
    
}

