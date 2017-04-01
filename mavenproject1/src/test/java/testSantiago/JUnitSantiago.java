/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSantiago;

import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 *
 * @author Arthur
 */
@RunWith(JUnitPlatform.class)

public class JUnitSantiago {

    //test sur la génération de la source
    @Test
    public void testAlea() {
        Source s = Source.getInstance();
        int sx = s.getX();
        int sy = s.getY();
        assertTrue(sx % 3 == 0);
        assertTrue(sy % 3 == 0);
        assertTrue(sx >= 0 && sx < 13);
        assertTrue(sy >= 0 && sy < 10);
    }
}
