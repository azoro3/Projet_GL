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
public class Joueur {
    private String nom;
    private String couleur;
    private Canal canal;
    private int solde;
    private int travailleurs;
    private boolean estConstructeur = false;

    //Getter and setter
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public int getTravailleurs() {
        return travailleurs;
    }

    public void setTravailleurs(int travailleurs) {
        this.travailleurs = travailleurs;
    }

    public boolean isEstConstructeur() {
        return estConstructeur;
    }

    public void setEstConstructeur(boolean estConstructeur) {
        this.estConstructeur = estConstructeur;
    }

    public Joueur(String nom, String couleur, int solde, int travailleurs) {
        this.nom = nom;
        this.couleur = couleur;
        this.solde = solde;
        this.travailleurs = travailleurs;
    }
    
    
}
