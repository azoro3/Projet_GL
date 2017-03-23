/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu.Factory;

import com.mycompany.mavenproject1.Jeu.Canal;

/**
 *
 * @author Arthur
 */
public class CanalFactory {
    private simpleCanalFactory sCF;
    public CanalFactory(){
        this.sCF=new simpleCanalFactory();
    }
    
    public Canal genererCanal(){
        Canal canal=this.sCF.creerCanal();
        return canal;
    }
    
}
