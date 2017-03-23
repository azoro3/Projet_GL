/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu;

/**
 *
 * @author Arthur
 */
public class CanalJ extends Canal {
    private String Couleur;

    public String getCouleur() {
        return Couleur;
    }

    protected void setCouleur(String Couleur) {
        this.Couleur = Couleur;
    }

    public CanalJ(String Couleur) {
        this.Couleur = Couleur;
    }
    
    
    
}
