/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu;

import com.mycompany.mavenproject1.Jeu.Plateau.Source;

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

public Canal(){
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

    public Boolean poserCanal(Source s, List<Canal> listeCanalPose){
        Boolean poser=false;
        if(this.xDeb  == s.getX() && this.yDeb ==s.getY() || this.xFin  == s.getX() && this.yFin ==s.getY())
        {
            // afficher le canal
            poser=true;
        }
        for(int i=0; i==listeCanalPose.size()-1 ; i++){
            if (this.xDeb  == listeCanalPose.get(i).getxDeb() && this.yDeb ==listeCanalPose.get(i).getyDeb()
                    || this.xFin  == listeCanalPose.get(i).getxFin() && this.yFin ==listeCanalPose.get(i).getyFin()
                    ||this.xDeb  == listeCanalPose.get(i).getxFin() && this.yDeb ==listeCanalPose.get(i).getyFin()
                    || this.xFin  == listeCanalPose.get(i).getxDeb() && this.yFin ==listeCanalPose.get(i).getyDeb() )
            {
                        // afficher le canal

                        poser= true;
            }
            if (this.xDeb == listeCanalPose.get(i).getxDeb() && this.yDeb == listeCanalPose.get(i).getyDeb()
                    && this.xFin == listeCanalPose.get(i).getxFin() && this.yFin == listeCanalPose.get(i).getyFin())
            {
                    poser = false;
            }
            if(this.getxFin()-this.getxDeb() < 0 || this.getxFin()-this.getxDeb() > 1 || this.getyFin()-this.getyDeb() > 1 || this.getyFin()-this.getyDeb() < 0){
                // mauvais canal d'une distance de plus de 1
                poser=false;
            }

        }

        return poser;
    }
}
