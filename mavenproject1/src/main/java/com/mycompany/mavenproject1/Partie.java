/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.*;
import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.Factory.TuilesFactory;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class Partie {
    private ArrayList<Joueur> listeJoueurs;
    private List<Tuiles> pile1 =new LinkedList();
    private List<Tuiles> pile2 =new LinkedList();
    private List<Tuiles> pile3 =new LinkedList();
    private List<Tuiles> pile4 =new LinkedList();
    private List<Tuiles> pile5 =new LinkedList();
    private List<Canal> listeCanal =new LinkedList();
    private List<Canal> listeCanalPose = new LinkedList<>();
    private Source s;
    
   /**
    * fonction de création d'une partie
    */
    public  void initPartie() {

//      création des joueurs
        s=Source.getInstance();
        System.out.println(s.getX()+""+s.getY());
        this.listeJoueurs = new ArrayList();
        
        for (int i = 0; i <= 3; i++) {
            String nomJ = JOptionPane.showInputDialog("Nom du joueur :");
//      modifier l'attribut couleur
            String couleurJ = JOptionPane.showInputDialog("Choisissez votre couleur :");
            Joueur J = new Joueur(nomJ, couleurJ, 10, 22);
            CanalJ c = new CanalJ(couleurJ);
            this.listeJoueurs.add(J);
        }

        this.listeJoueurs.get(0).setEstConstructeur(true);
        
//      création des Tuiles
        List<Tuiles> touteLesTuiles = new LinkedList();
        CanalFactory factory=new CanalFactory();
        for(int i=0;i<=15;i++){
            Canal c=factory.genererCanal();
            this.listeCanal.add(c);
        }
//      Mettre les tuiles dans les liste
        TuilesFactory tFactory;
        tFactory = new TuilesFactory();
        for (int i=1;i<=9;i++){
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
                    if(this.pile1.size()<9){
                    this.pile1.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 1:
                    if(this.pile2.size()<9){
                        this.pile2.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 2:
                    if(this.pile3.size()<9){
                        this.pile3.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 3:
                    if(this.pile4.size()<9){
                        this.pile4.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 4:
                    if(this.pile5.size()<9){
                        this.pile5.add(touteLesTuiles.remove(val));
                    }
                    break;
            }
        }
     
    }
    
    /**
     * 
     * @return tableau avec la première tuile de chaque pile
     */
    public Tuiles[] getFirstCarte() {
        Tuiles[] t = new Tuiles[5];
        t[0] = this.pile1.get(0);
        t[1] = this.pile2.get(0);
        t[2] = this.pile3.get(0);
        t[3] = this.pile4.get(0);
        t[4] = this.pile5.get(0);
        return t;
    }
    
    /**
     * 
     * @return un hasmap avec les différentes mises des joueurs
     */
    public Map<Joueur, Integer> faireUneEnchere() {
        Map<Joueur, Integer> enchere = new HashMap<>();
//      fonction pour trier les joueurs dans le sens des enchères.
        boolean tri = false; int i=0;
        ArrayList<Joueur> lTemp=new ArrayList<>();
        while(!tri){
          if(this.listeJoueurs.get(i).isEstConstructeur())  {
              for(int j=0;j<=i;j++){
                  lTemp.add(this.listeJoueurs.remove(j));
              }
            tri=true;
          }
          i++;
        }
        this.listeJoueurs.addAll(lTemp);
//      enchères des joueurs
        for (final Joueur joueur : this.listeJoueurs) {
            int valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog(joueur.getNom() + ", faites votre enchère !"));
            while (true) {
                // verifie que la personne ne passe pas son tour
                if (valeurEnchere > 0) {
                    // verifie que la personne ait bien l'argent pour la mise
                    if (joueur.getSolde() >= valeurEnchere) {
                        // verifie que personne n'ai misé cela avant
                        if (!enchere.values().contains(valeurEnchere)) {
                            enchere.put(joueur,valeurEnchere);
                            break;
                        } else {
                            valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Quelqu'un à déjà miser cette somme, faites une autre enchères ! :"));
                        }
                    } else {
                        valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Vous n'avez pas assez d'argent pour une telle enchere ! :"));
                    }
                } else {
                    valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Pas d'enchere négative ! :"));
                }


            }

        }
        return enchere;
    }
    
    /**
     * 
     * @param enchere map avec les enchère de chaque joueurs
     */
    public void changerConstructeur(Map<Joueur, Integer> enchere) {
        Iterator it = enchere.values().iterator();
        int inf = -1;
        // prendre le meilleur joueur par rapport a toutes les encheres
        while (it.hasNext()) {
            int val = (int) it.next();
            if (val < inf) {
                inf = val;
            }
        }
        for (Map.Entry<Joueur, Integer> pair : enchere.entrySet()) {
            if ( pair.getValue() == inf) {
                Joueur CreuseurJoueur = pair.getKey();
                CreuseurJoueur.setEstConstructeur(true);
                break;
            }
        }
    }

    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public List<Tuiles> getPile1() {
        return pile1;
    }

    public List<Tuiles> getPile2() {
        return pile2;
    }

    public List<Tuiles> getPile3() {
        return pile3;
    }

    public List<Tuiles> getPile4() {
        return pile4;
    }

    public List<Tuiles> getPile5() {
        return pile5;
    }

    public List<Canal> getListeCanal() {
        return listeCanal;
    }

    public List<Canal> getListeCanalPose() {
        return listeCanalPose;
    }

}
