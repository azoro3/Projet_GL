/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu;

import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author Arthur
 */
public class Canal {

    private int xDeb;
    private int yDeb;
    private int xFin;
    private int yFin;

    public Canal() {
    }

    public Canal(int xdeb, int ydeb, int xfin, int yfin) {
        this.xDeb = xdeb;
        this.yDeb = ydeb;
        this.xFin = xfin;
        this.yFin = yfin;
    }

    public int getxDeb() {
        return xDeb;
    }

    public void setxDeb(int xDeb) {
        this.xDeb = xDeb;
    }

    public int getyDeb() {
        return yDeb;
    }

    public void setyDeb(int yDeb) {
        this.yDeb = yDeb;
    }

    public int getxFin() {
        return xFin;
    }

    public void setxFin(int xFin) {
        this.xFin = xFin;
    }

    public int getyFin() {
        return yFin;
    }

    public void setyFin(int yFin) {
        this.yFin = yFin;
    }

    @Override
    public String toString() {
        return "x : " + this.xDeb + " y : " + this.yDeb;
    }
    
    public Boolean poserCanal(Source s, List<Canal> listeCanalPose) {
        Boolean poser = true;
        if (this.getxFin() - this.getxDeb() < -3 || this.getxFin() - this.getxDeb() > 3 || this.getyFin() - this.getyDeb() > 3 || this.getyFin() - this.getyDeb() < -3
                || (this.getxFin() - this.getxDeb() == 0 && this.getyFin() - this.getyDeb() == 0)) {
            // mauvais canal d'une distance de plus de 1
            poser = false;
        } else {
            if (this.xDeb == s.getX() && this.yDeb == s.getY() || this.xFin == s.getX() && this.yFin == s.getY()) {
                // afficher le canal
                poser = true;
            }
            for (int i = 0; i == listeCanalPose.size() - 1; i++) {

                if (this.xDeb == listeCanalPose.get(i).getxDeb() && this.yDeb == listeCanalPose.get(i).getyDeb()
                        || this.xFin == listeCanalPose.get(i).getxFin() && this.yFin == listeCanalPose.get(i).getyFin()
                        || this.xDeb == listeCanalPose.get(i).getxFin() && this.yDeb == listeCanalPose.get(i).getyFin()
                        || this.xFin == listeCanalPose.get(i).getxDeb() && this.yFin == listeCanalPose.get(i).getyDeb()) {
                    // afficher le canal
                    // condition si jamais poser passe a false durant la boucle
                    if (poser != false) {
                        poser = true;
                    }
                } else {
                    if (this.xDeb == listeCanalPose.get(i).getxDeb() && this.yDeb == listeCanalPose.get(i).getyDeb()
                            && this.xFin == listeCanalPose.get(i).getxFin() && this.yFin == listeCanalPose.get(i).getyFin()) {
                        poser = false;
                    }

                }
            }

        }
        return poser;
    }

    /**
     * Fonction qui permet de savoir si un canal peut être posé à une position
     * en fonction de la source et des autres canaux.
     *
     * @param s source
     * @param listeCanalPose liste des canaux déjà posés sur le plateau
     * @return 
     */
    public Boolean poserCanalPlateau(Source s, List<Canal> listeCanalPose) {
        int xCanal, yCanal;
        Boolean b = false;

        // Lors de la première pose des canaux, il suffit de vérifier près de la source
        if (listeCanalPose.isEmpty()) {
            return (this.getxDeb() == s.getX() + 1 && this.getyDeb() == s.getY())
                    || (this.getxDeb() == s.getX() - 2 && this.getyDeb() == s.getY())
                    || (this.getxDeb() == s.getX() && this.getyDeb() == s.getY() + 1)
                    || (this.getxDeb() == s.getX() && this.getyDeb() == s.getY() - 2);
        } else {
            for (Canal c : listeCanalPose) {
                xCanal = c.getxDeb();
                yCanal = c.getyDeb();
                // La place est déjà occupée par un autre canal
                if (this.getxDeb() == xCanal && this.getyDeb() == yCanal) {
                    return false;
                } else {
                    // Sinon, il faut vérifier qu'il y a la source à côté
                    if ((this.getxDeb() == s.getX() + 1 && this.getyDeb() == s.getY())
                            || (this.getxDeb() == s.getX() - 2 && this.getyDeb() == s.getY())
                            || (this.getxDeb() == s.getX() && this.getyDeb() == s.getY() + 1)
                            || (this.getxDeb() == s.getX() && this.getyDeb() == s.getY() - 2)) {
                        return true;
                    } // Ou qu'un canal adjacent est posé
                    else if ((this.getxDeb() == xCanal + 3 && this.getyDeb() == yCanal)
                            || (this.getxDeb() == xCanal - 3 && this.getyDeb() == yCanal)
                            || (this.getxDeb() == xCanal + 2 && this.getyDeb() == yCanal - 2)
                            || (this.getxDeb() == xCanal + 2 && this.getyDeb() == yCanal + 1)
                            || (this.getxDeb() == xCanal + 1 && this.getyDeb() == yCanal - 2)
                            || (this.getxDeb() == xCanal + 1 && this.getyDeb() == yCanal - 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String toJSON() {
        String canalJSON ="{\"xDeb\":\""+this.xDeb+"\",\"xFin\":"+this.xFin+",\"yDeb\":"+this.yDeb+",\"yFin\" :"+this.yFin+"}";
        return canalJSON;
    }
    
}
