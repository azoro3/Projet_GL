/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSantiago;

import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.*;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
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
    @DisplayName ("testAlea")
    @Test
    public void testAlea(){
        Source s=Source.getInstance();
        int sx=s.getX();
        int sy=s.getY();
        assertTrue(sx<6 && sy<5);
    }
    //test sur la création des canaux par la factory
    @DisplayName ("testFactory")
    @Test
    public void testFactory(){
        CanalFactory factory=new CanalFactory();
        Canal c=factory.genererCanal();
        assertNotNull("Ne doit pas être nul",c);
        
    }
}
