/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu;

import Reseau.InterfaceClient;
import Reseau.InterfaceServeur;

import java.rmi.RemoteException;

/**
 *
 * @author Arthur
 */
public class Joueur implements InterfaceClient {
    private String nom;
    private String couleur;
    private Canal canal;
    private int solde;
    private int travailleurs;
    private boolean estConstructeur = false;
    private InterfaceServeur serv;

    //Getter and setter
    public String getNom(){
        return nom;
    }
    public InterfaceServeur getServeur(){
        return this.serv;
    }
    public void setServeur(InterfaceServeur se){
        this.serv=se;
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

    public Joueur(String nom, String couleur, int solde, int travailleurs) throws RemoteException {
        super();
        this.nom = nom;
        this.couleur = couleur;
        this.solde = solde;
        this.travailleurs = travailleurs;
    }
    /**
     * 
     * @return un objet joueur au format JSON
     */
    public String toJSON(){
        int score=22-this.travailleurs+this.solde;
        String joueurJSON ="{\"nom\" : \""+this.nom+"\",\"couleur\":\""+this.couleur+"\",\"solde\":"+this.solde+",\"score\":"+score+"}";
        return joueurJSON;
    }
    
    
}
