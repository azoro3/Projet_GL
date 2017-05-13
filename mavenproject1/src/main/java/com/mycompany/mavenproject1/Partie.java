/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import Reseau.*;
import com.mycompany.mavenproject1.Jeu.*;
import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.Factory.TuilesFactory;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 *
 * @author Arthur
 */
public class Partie extends UnicastRemoteObject implements InterfacePartie{
    
    private ArrayList<InterfaceClient> listeJoueurs;
    private List<InterfaceTuiles> pile1 = new LinkedList();
    private List<InterfaceTuiles> pile2 = new LinkedList();
    private List<InterfaceTuiles> pile3 = new LinkedList();
    private List<InterfaceTuiles> pile4 = new LinkedList();
    private List<InterfaceTuiles> pile5 = new LinkedList();
    private List<Canal> listeCanal = new LinkedList();
    private List<Canal> listeCanalPose = new LinkedList<>();
    private List<InterfaceTuiles> tuilesJoue = new LinkedList<>();
    private Source s;
    private InterfaceServeur serv;
    private Source source;
    private ArrayList<Canal> canauxAutorises;
    private ArrayList<Canal> vertical = new ArrayList<>();
    private ArrayList<Canal> horizontal = new ArrayList<>();

    public Partie() throws RemoteException {
        super();
    }

    public InterfaceServeur getServeur(){
        return this.serv;
    }
    public void setServeur(InterfaceServeur s){
        this.serv=s;
    }

    /**
    * fonction de création d'une partie
    */
    public  void initPartie(Source s) throws RemoteException {
        canauxAutorises=new ArrayList<>();
        for (int col = 0; col < 13; col++) {
            for (int lig = 0; lig < 8; lig++) {
                if ((col == 0 || col == 3 || col == 6 || col == 9 || col == 12)
                        && (lig == 1 || lig == 4 || lig == 7)) {
                    vertical.add(new Canal(col, lig, col, lig + 1));
                }
            }
        }

        // Liste de tous les canaux horizontaux
        for (int col = 0; col < 11; col++) {
            for (int lig = 0; lig < 10; lig++) {
                if ((col == 1 || col == 4 || col == 7 || col == 10)
                        && (lig == 0 || lig == 3 || lig == 6 || lig == 9)) {
                    horizontal.add(new Canal(col, lig, col + 1, lig));
                }
            }
        }
        this.tuilesJoue=new LinkedList<InterfaceTuiles>();
//      création des joueurs
        this.source=s;
        System.out.println(s.getX()+""+s.getY());
//      création des Tuiles
        List<Tuiles> touteLesTuiles = new LinkedList();
        CanalFactory factory=new CanalFactory();
        for(int i=0;i<=15;i++){
            Canal ca=factory.genererCanal();
            this.listeCanal.add(ca);
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

    public InterfaceTuiles[] getFirstCarte() throws RemoteException {
        InterfaceTuiles[] t = new InterfaceTuiles[5];
        t[0] = this.pile1.get(0);
        t[1] = this.pile2.get(0);
        t[2] = this.pile3.get(0);
        t[3] = this.pile4.get(0);
        t[4] = this.pile5.get(0);
        t[0].setNum(0);
        t[1].setNum(1);
        t[2].setNum(2);
        t[3].setNum(3);
        t[4].setNum(4);
        return t;
    }
    public ArrayList<Canal> getListeCanauxAutorises(Boolean orientation) {
        canauxAutorises.clear();
        // Canaux verticaux autorisés
        if (orientation) {
            for (Canal c : listeCanal) {
                if (!listeCanalPose.contains(c)) {
                    canauxAutorises.add(c);
                }
            }
        } else {
            // Canaux horizontaux autorisés
            for (Canal c : horizontal) {
                if (!listeCanalPose.contains(c)) {
                    canauxAutorises.add(c);
                }
            }

        }
        return canauxAutorises;
    }
    public void addTuileJoue(InterfaceTuiles tuile){
        this.tuilesJoue.add(tuile);
    }
    public boolean addListeCanauxPoses(Canal c){
        return listeCanalPose.add(c);
    }
    
    /**
     *
     * @param enchere map avec les enchère de chaque joueurs
     */
    public ArrayList<InterfaceClient> changerConstructeur(Map<InterfaceClient, Integer> enchere) throws RemoteException {

        ArrayList<Integer> tab;

        tab = new ArrayList<Integer>();
        int i=0;
        Iterator it = enchere.values().iterator();
        int inf = -1;
        // prendre le meilleur joueur par rapport a toutes les encheres
        while (it.hasNext()) {
            int val = (int) it.next();
            tab.add(val);
            if (val < inf) {
                inf = val;
            }
            i++;
        }
        for (Map.Entry<InterfaceClient, Integer> pair : enchere.entrySet()) {
            if ( pair.getValue() == inf) {
                InterfaceClient CreuseurJoueur = pair.getKey();
                for(int k=0;k<serv.getListeClient().size();i++) {
                    if (serv.getListeClient().get(k) == CreuseurJoueur) {
                        serv.getListeClient().get(k).setEstConstructeur(true);
                    }
                }
                break;
            }
        }
        Collections.sort(tab);
        ArrayList<InterfaceClient> trier = new ArrayList<InterfaceClient>();
        if(serv.getEnchere().size()==5){
        for (Map.Entry<InterfaceClient, Integer> pair2 : serv.getEnchere().entrySet()) {
            if(tab.get(4).equals(pair2.getValue())){
                trier.add(pair2.getKey());
            }
        }
        for (Map.Entry<InterfaceClient, Integer> pair2 : serv.getEnchere().entrySet()) {
            if(tab.get(3).equals(pair2.getValue())){
                trier.add(pair2.getKey());
            }
        }for (Map.Entry<InterfaceClient, Integer> pair2 : serv.getEnchere().entrySet()) {
            if(tab.get(2).equals(pair2.getValue())){
                trier.add(pair2.getKey());
            }
        }for (Map.Entry<InterfaceClient, Integer> pair2 : serv.getEnchere().entrySet()) {
            if(tab.get(1).equals(pair2.getValue())){
                trier.add(pair2.getKey());
            }
        }for (Map.Entry<InterfaceClient, Integer> pair2 : serv.getEnchere().entrySet()) {
            if (tab.get(0).equals( pair2.getValue())) {
                trier.add(pair2.getKey());

            }
        }
          }
        return trier;


    }




    public ArrayList<InterfaceClient> getListeJoueurs() {
        return listeJoueurs;
    }



    public List<InterfaceTuiles> getPile1() {
        return pile1;
    }

    public List<InterfaceTuiles> getPile2() {
        return pile2;
    }

    public List<InterfaceTuiles> getPile3() {
        return pile3;
    }

    public List<InterfaceTuiles> getPile4() {
        return pile4;
    }

    public List<InterfaceTuiles> getPile5() {
        return pile5;
    }

    public List<Canal> getListeCanal() {
        return listeCanal;
    }

    public List<Canal> getListeCanalPose() {
        return listeCanalPose;
    }
     public List<InterfaceTuiles> getTuilesJoue(){
        return tuilesJoue;
}




}