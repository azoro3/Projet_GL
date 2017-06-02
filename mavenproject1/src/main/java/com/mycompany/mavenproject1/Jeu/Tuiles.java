package com.mycompany.mavenproject1.Jeu;

import java.util.Random;

/**
 *
 * @author Arthur
 */
public class Tuiles implements Cloneable {
    
    private String type;
    private int nbTravailleurs;
    private int x;
    private int y;
    private boolean isIrigue;

    public Tuiles(String type) {
        this.type=type;
        Random r=new Random();
        this.nbTravailleurs = 1 + r.nextInt(3 - 1);
        this.isIrigue = false;
    }
    
    @Override
    public Object clone() {
        Tuiles t = null;
        try {
            t = (Tuiles) super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return t;
    }
    
    /**
     * 
     * @return un objet tuiles au format JSON
     */
    public String toJSON(){
        String tuileJSON ="{\"type\":\""+this.type+"\",\"x\":"+this.x+",\"y\":"+this.y+",\"isIrigue\" :"+this.isIrigue+"}";
        return tuileJSON;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbTravailleurs() {
        return nbTravailleurs;
    }

    public void setNbTravailleurs(int nbTravailleurs) {
        this.nbTravailleurs = nbTravailleurs;
    }

    public void setIrigue(boolean isIrigue) {
        this.isIrigue = isIrigue;
    }

    public boolean getIrigue() {
        return this.isIrigue;
    }

    @Override
    public String toString() {
        return "Tuiles{" + "type=" + type + ", nbTravailleurs=" + nbTravailleurs + ", x=" + x + ", y=" + y + ", isIrigue=" + isIrigue + '}';
    }

}
