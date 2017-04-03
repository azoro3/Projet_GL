/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.*;
import com.mycompany.mavenproject1.Jeu.Factory.TuilesFactory;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
        ArrayList<Joueur> listeJoueurs;
        listeJoueurs = new ArrayList();
        for(int i=0;i<=3;){
            String nomJ = JOptionPane.showInputDialog("Nom du joueur :");
//      modifier l'attribut couleur
            String couleurJ=JOptionPane.showInputDialog("Choisissez votre couleur :");
            Joueur J=new Joueur(nomJ,couleurJ,10,22);
            CanalJ c=new CanalJ(couleurJ);
            listeJoueurs.add(J);
        }
        
//      création des Tuiles
        List<Tuiles> touteLesTuiles = new LinkedList();
        List<Tuiles> pile1 =new LinkedList();
        List<Tuiles> pile2 =new LinkedList();
        List<Tuiles> pile3 =new LinkedList();
        List<Tuiles> pile4 =new LinkedList();
        List<Tuiles> pile5 =new LinkedList();
        List<Canal> listeCanal =new LinkedList();
        CanalFactory factory=new CanalFactory();
        for(int i=0;i<=15;){
            Canal c=factory.genererCanal();
            listeCanal.add(c);
        }
//      Mettre les tuiles dans les liste
        TuilesFactory tFactory;
        tFactory = new TuilesFactory();
        for (int i=1;i<=9;){
            touteLesTuiles.add(tFactory.genererTuiles("piment"));
            touteLesTuiles.add(tFactory.genererTuiles("haricot"));
            touteLesTuiles.add(tFactory.genererTuiles("banane"));
            touteLesTuiles.add(tFactory.genererTuiles("patate"));
            touteLesTuiles.add(tFactory.genererTuiles("sucre"));
        }
        while(touteLesTuiles.size()>0){
            int val;
            val = (int) (Math.random() * ( touteLesTuiles.size()));
            int val2;
            val2 = (int) (Math.random() * (5));
            switch (val2){
                case 0:
                    if(pile1.size()<9){
                    pile1.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 1:
                    if(pile2.size()<9){
                        pile2.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 2:
                    if(pile3.size()<9){
                        pile3.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 3:
                    if(pile4.size()<9){
                        pile4.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 4:
                    if(pile5.size()<9){
                        pile5.add(touteLesTuiles.remove(val));
                    }
                    break;
            }
        }
//      
        
    }   
    
}
