/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSantiago;

import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.*;
import com.mycompany.mavenproject1.Jeu.Factory.TuilesFactory;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.rmi.RemoteException;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 *
 * @author Arthur
 */
@RunWith(JUnitPlatform.class)
public class JUnitSantiago {

    //test sur la génération de la source
    @DisplayName("testAlea")
    @Test
    public void testAlea() throws RemoteException {
        Source s = new Source();
        int sx = s.getX();
        int sy = s.getY();
        assertTrue(sx % 3 == 0);
        assertTrue(sy % 3 == 0);
        assertTrue(sx >= 0 && sx < 13);
        assertTrue(sy >= 0 && sy < 10);
    }

    //test sur la création des canaux par la factory
    @DisplayName("testFactory")
    @Test
    public void testFactory() {
        CanalFactory factory = new CanalFactory();
        Canal c = factory.genererCanal();
        assertNotNull("Ne doit pas être nul", c);

    }
    //test sur la création des canaux par la factory

    @DisplayName("testNbTravailleurs")
    @Test
    public void testNbTravailleurs() throws RemoteException {
        Tuiles t = new Tuiles("piment");
        assertTrue(t.getNbTravailleurs() < 3 && t.getNbTravailleurs() > 0);

    }
    //test pour la création des piles
    
    @DisplayName("createPileCartes")
    @Test
    public void testPileCartes() throws RemoteException {
        List<Tuiles> touteLesTuiles = new LinkedList();
        List<Tuiles> pile1 =new LinkedList();
        List<Tuiles> pile2 =new LinkedList();
        List<Tuiles> pile3 =new LinkedList();
        List<Tuiles> pile4 =new LinkedList();
        List<Tuiles> pile5 =new LinkedList();
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
       assertEquals(9,pile2.size());
       assertEquals(9,pile1.size());
       assertEquals(9,pile3.size());
       assertEquals(9,pile4.size());
       assertEquals(9,pile5.size());
    }
    
    @DisplayName("test save score")
    @Test
    public void testSaveScore() throws IOException{
        String res="{joueurs :[\n";
        ArrayList<Joueur> listeJoueurs = new ArrayList<>();
        Joueur J = new Joueur("Arthur", "Bleu", 10, 22);
        listeJoueurs.add(J);
        Joueur J2 = new Joueur("Manon", "Vert", 10, 22);
        listeJoueurs.add(J2);
        for(final Joueur j :listeJoueurs){
            res+=j.toJSON()+",\r\n";
        }
        res=res.substring(0,(res.length()-3));
        res+="]}";
        FileWriter fw= new FileWriter("D:\\Arthur\\Documents\\Cours\\Prog\\M1 MIAGE\\Programme S8\\Projet_GL\\mavenproject1\\saveScore.json");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(res);
        bw.flush();
        bw.close();
    }
    
    @DisplayName("testPoserCanal")
    @Test
    public void testPoserCanal() throws RemoteException {
        Boolean reponse;
        Canal c = new Canal();
        Canal c2 = new Canal();
        Source s = new Source();
        List<Canal> listCanalPose=new LinkedList();
        c.setxDeb(0);
        c.setxFin(1);
        c.setyDeb(0);
        c.setyFin(0);
        listCanalPose.add(c);

        c2.setxDeb(0);
        c2.setxFin(1);
        c2.setyDeb(0);
        c2.setyFin(0);

        reponse=c2.poserCanal(s,listCanalPose);
        //canal c2 = canal c
        assertFalse(reponse);

        c2.setxDeb(s.getX());
        c2.setxFin(s.getX());
        c2.setyDeb(s.getY());
        c2.setyFin(s.getY()+1);
        reponse=c2.poserCanal(s,listCanalPose);
        // xdeb et ydeb de c2 = x et y de la source
        assertTrue(reponse);

        c2.setxDeb(1);
        c2.setyDeb(0);
        c2.setxFin(2);
        c2.setyFin(0);
        reponse=c2.poserCanal(s,listCanalPose);
        //xdeb et ydeb c2 = xfin et yfin de c
        assertTrue(reponse);

        c2.setxDeb(0);
        c2.setyDeb(0);
        c2.setxFin(0);
        c2.setyFin(1);
        reponse=c2.poserCanal(s,listCanalPose);
        // xdeb et ydeb c2 = xdeb et ydeb c
        assertTrue(reponse);


        c2.setxDeb(1);
        c2.setyDeb(0);
        c2.setxFin(3);
        c2.setyFin(5);
        reponse=c2.poserCanal(s,listCanalPose);
        // canal c2 non conforme
        assertFalse(reponse);
    }
}
