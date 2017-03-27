/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class MainInit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//      création des joueurs
        ArrayList listeJoueurs;
        listeJoueurs = new ArrayList<Joueur>();
        for(int i=0;i<=3;){
            String nomJ = JOptionPane.showInputDialog("Nom du joueur :");
//      modifier l'attribut couleur
            String couleurJ=JOptionPane.showInputDialog("Choisissez votre couleur :");
            Joueur J=new Joueur(nomJ,couleurJ,10,22);
            CanalJ c=new CanalJ(couleurJ);
            listeJoueurs.add(J);
        }
        
//      création des Tuiles
        ArrayList pile1 =new ArrayList<Tuiles>();
        ArrayList pile2 =new ArrayList<Tuiles>();
        ArrayList pile3 =new ArrayList<Tuiles>();
        ArrayList pile4 =new ArrayList<Tuiles>();
        ArrayList pile5 =new ArrayList<Tuiles>();
        ArrayList listeCanal =new ArrayList<Canal>();
        CanalFactory factory=new CanalFactory();
        for(int i=0;i<=15;){
            Canal c=factory.genererCanal();
            listeCanal.add(c);
        }
        
        
    }
    
}
