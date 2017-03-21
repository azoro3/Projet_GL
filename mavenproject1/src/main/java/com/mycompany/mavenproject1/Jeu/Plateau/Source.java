/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Jeu.Plateau;

/**
 *
 * @author Arthur
 */
public class Source {
    private int x;
    private int y;
    
    private Source() {
        this.x=0 + (int)(Math.random() * ((5 - 0) + 1));
        this.y=0 + (int)(Math.random() * ((4 - 0) + 1));
    }
    
    public static Source getInstance() {
        return SourceHolder.INSTANCE;
    }
    
    private static class SourceHolder {

        private static final Source INSTANCE = new Source();
    }

    @Override
    public String toString() {
        return "Source{" + "x=" + x + ", y=" + y + '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
