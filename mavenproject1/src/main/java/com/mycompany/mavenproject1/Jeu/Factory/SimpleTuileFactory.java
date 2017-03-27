/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu.Factory;

import com.mycompany.mavenproject1.Jeu.Tuiles;

/**
 *
 * @author Arthur
 */
public class SimpleTuileFactory {
    public Tuiles creerTuile(String type){
        Tuiles t=null;
        switch(type){
            case("piment"):t=new Tuiles("piment");
            case("haricot"):t=new Tuiles("haricot");
            case("banane"):t=new Tuiles("banane");
            case("patate"):t=new Tuiles("patate");
            case("sucre"):t=new Tuiles("sucre");
        }
        return t;
    }
    
}

