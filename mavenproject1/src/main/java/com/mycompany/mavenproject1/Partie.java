/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.*;
import com.mycompany.mavenproject1.Jeu.Factory.TuilesFactory;
import com.mycompany.mavenproject1.Jeu.Plateau.Parcelle;
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
        t[0]=this.pile1.get(0);
        t[1]=this.pile2.get(0);
        t[2]=this.pile3.get(0);
        t[3]=this.pile4.get(0);
        t[4]=this.pile5.get(0);
        return null;
            
        }
    /**
     * 
     * @return un hasmap avec les différentes mises des joueurs
     */
    public Map<Joueur,String> faireUneEnchere(){
        Map<Joueur,String> enchere = new HashMap<>();
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
        for(final Joueur joueur : this.listeJoueurs){
            String valeurEnchere = JOptionPane.showInputDialog("Faites votre enchères ! :");
            while(enchere.values().contains(valeurEnchere)){
                if("Passe".equals(valeurEnchere)){
                    enchere.put(joueur, valeurEnchere);break;
                }
                else{
                     valeurEnchere = JOptionPane.showInputDialog("Quelqu'un à déjà miser cette somme, faites une autre enchères ! :");                
                }
            }
            enchere.put(joueur, valeurEnchere);
        }
        return enchere;
    }
    /**
     * 
     * @param enchere map avec les enchère de chaque joueurs
     */
    public void changerConstructeur(Map<Joueur,String> enchere){
        for (Map.Entry<Joueur, String> pair : enchere.entrySet()) {
            if(pair.getValue().equals("Passe")){
                pair.getKey().setEstConstructeur(true);break;
            }
        }
    }

    public void soudoyerConstructeur(){

        Map<Joueur,Integer> joueurEncher = new HashMap<>();
        Map<Integer,Joueur> encherJoueur = new HashMap<>();
        Map<Joueur,Canal> joueurCanal = new HashMap<>();
        Joueur meilleurJoueur=null;
        Joueur creuseur= null;
        ArrayList<Joueur> ltemp = this.listeJoueurs;
//      enlever le constructeur
        for(int i=0; i<=ltemp.size()-1;i++) {
            if (ltemp.get(i).isEstConstructeur()) {
                creuseur=ltemp.get(i);
                    ltemp.remove(i);
            }
        }
//      enchères des joueurs
        for(final Joueur joueur : ltemp) {
            int valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Faites votre enchères ! :"));

            while (true) {
                //verifie que la personne ne passe pas son tour
                if(valeurEnchere>0) {
                    //verifie que la personne ait bien l'argent pour la mise
                    if(joueur.getSolde()>=valeurEnchere) {
                        //verifie que personne n'ai misé cela avant
                        if (!joueurEncher.values().contains(valeurEnchere)) {

                            int xdeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de depart du canal ! :"));
                            int ydeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de depart du canal ! :"));
                            int xfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de fin du canal ! :"));
                            int yfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de fin du canal ! :"));
                            Canal c = new Canal(xdeb, ydeb, xfin, yfin);
                            while (!c.poserCanal(s, listeCanalPose)) {
                                xdeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de depart du canal ! :"));
                                ydeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de depart du canal ! :"));
                                xfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de fin du canal ! :"));
                                yfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de fin du canal ! :"));
                                c = new Canal(xdeb, ydeb, xfin, yfin);
                            }

                            encherJoueur.put(valeurEnchere, joueur);
                            joueurEncher.put(joueur, valeurEnchere);

                            //AFAIRE afficher chaque canal (que la personne souhaite creuser) de chaque joueur de la couleur de celui ci EN FX
                            joueurCanal.put(joueur, c);

                            Iterator it = joueurEncher.values().iterator();
                            int sup = -1;
                            // prendre le meilleur joueur par rapport a toutes les encheres
                            while (it.hasNext()) {
                                int val = (int) it.next();
                                if (val > sup) {
                                    sup = val;
                                }
                            }

                            if (sup == -1 || joueur == encherJoueur.get(sup)) {
                                meilleurJoueur = joueur;
                            }
                            break;
                        } else {
                            valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Quelqu'un à déjà miser cette somme, faites une autre enchères ! :"));
                        }
                    }else{
                        valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Vous n'avez pas assez d'argent pour une telle enchere ! :"));
                    }
                }else{
                    break;
                }
            }

        }

        //afficher la valeur du meilleur joueur pour que le creuseur prenne sa decision (suivre la meilleur proposition on creuser lui meme) en FX AFAIRE /!\
        String reponse;
        //verifie si les joueurs n'ont pas tous passés
        if (meilleurJoueur!=null) {
            //afficher la valeur du meilleur joueur pour que le creuseur prenne sa decision
            // (suivre la meilleur proposition on creuser lui meme) en FX AFAIRE a la place du sysout/!\
            System.out.println("meilleur joueur :" + meilleurJoueur.getNom() + "enchere de :" + joueurEncher.get(meilleurJoueur) +
                    "position canal xdeb: " + joueurCanal.get(meilleurJoueur).getxDeb() + " ydeb : " + joueurCanal.get(meilleurJoueur).getyDeb() + " xfin : " + joueurCanal.get(meilleurJoueur).getxFin() +
                    " yfin : " + joueurCanal.get(meilleurJoueur).getyFin());

            reponse = JOptionPane.showInputDialog("Voulez-vous prendre la meilleur enchere ? oui/non");
            while (!reponse.equals("OUI") && !reponse.equals("NON") && !reponse.equals("non") && !reponse.equals("oui") && !reponse.equals("o") && !reponse.equals("n")) {
                reponse = JOptionPane.showInputDialog("Voulez-vous prendre la meilleur enchere ? oui/non");
            }

            if (reponse.equals("oui") || reponse.equals("OUI") || reponse.equals("o")) {
                // si le creuseur choisit de suivre le meilleur joueur il gagne la mise et creuse dans le sens de ce joueur
                listeCanalPose.add(joueurCanal.get(meilleurJoueur));
                creuseur.setSolde(creuseur.getSolde() + joueurEncher.get(meilleurJoueur));
                meilleurJoueur.setSolde(meilleurJoueur.getSolde() - joueurEncher.get(meilleurJoueur));
            } else {
                //sinon il creuse ou il veut mais paye un de plus que le meilleur joueur
                if (creuseur.getSolde() >= joueurEncher.get(meilleurJoueur) + 1) {
                    int xdeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de depart du canal ! :"));
                    int ydeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de depart du canal ! :"));
                    int xfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de fin du canal ! :"));
                    int yfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de fin du canal ! :"));
                    Canal c = new Canal(xdeb, ydeb, xfin, yfin);
                    while (!c.poserCanal(s, listeCanalPose)) {
                        xdeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de depart du canal ! :"));
                        ydeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de depart du canal ! :"));
                        xfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de fin du canal ! :"));
                        yfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de fin du canal ! :"));
                        c = new Canal(xdeb, ydeb, xfin, yfin);
                    }
                    listeCanalPose.add(c);
                    creuseur.setSolde(creuseur.getSolde() - (joueurEncher.get(meilleurJoueur) + 1));
                } else {
                    listeCanalPose.add(joueurCanal.get(meilleurJoueur));
                    creuseur.setSolde(creuseur.getSolde() + joueurEncher.get(meilleurJoueur));
                    meilleurJoueur.setSolde(meilleurJoueur.getSolde() - joueurEncher.get(meilleurJoueur));
                }
            }
            //si les joueurs ont tous passés alors le creuseur decide lui meme
        }else{
            int xdeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de depart du canal ! :"));
            int ydeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de depart du canal ! :"));
            int xfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de fin du canal ! :"));
            int yfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de fin du canal ! :"));
            Canal c = new Canal(xdeb, ydeb, xfin, yfin);
            while (!c.poserCanal(s, listeCanalPose)) {
                xdeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de depart du canal ! :"));
                ydeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de depart du canal ! :"));
                xfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de fin du canal ! :"));
                yfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de fin du canal ! :"));
                c = new Canal(xdeb, ydeb, xfin, yfin);
            }
            listeCanalPose.add(c);
        }
    }
    /**
     *
     * @return getters
     */

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




}
