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

    public Source() {
        this.x = rand3(0,13);
        this.y = rand3(0,10);
    }

    /**
     * Génère un entier multiple de 3 compris entre min (inclus) et max (exclus).
     * @param min
     * @param max
     * @return 
     */
    private int rand3(int min, int max) {
        return (int) ((Math.random() * (max - min) + min) / 3) * 3;
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
