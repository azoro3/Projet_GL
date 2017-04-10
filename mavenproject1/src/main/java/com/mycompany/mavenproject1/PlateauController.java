package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Jeu.Tuiles;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PlateauController implements Initializable {

    @FXML
    private GridPane plateau;

    @FXML
    private ImageView tuile1, tuile2, tuile3, tuile4, tuile5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chargement de l'image "source.png"
        final ImageView img = new ImageView();
        final Image sourceImg = new Image(getClass().getResourceAsStream("/images/source.png"));
        img.setImage(sourceImg);

        // Placement aléatoire de la source
        plateau.add(img, Source.getInstance().getX(), Source.getInstance().getY());
    }

    public void phase1() {
        // Initialisation d'une nouvelle partie
        Partie partie = new Partie();
        partie.initPartie();

        // On récupère la première tuile de chaque pile
        Tuiles[] tuiles = partie.getFirstCarte();

        // On affiche la première tuile
        mettreImage(tuile1, tuiles[0].getType(), tuiles[0].getNbTravailleurs());
        mettreImage(tuile2, tuiles[1].getType(), tuiles[1].getNbTravailleurs());
        mettreImage(tuile3, tuiles[2].getType(), tuiles[2].getNbTravailleurs());
        mettreImage(tuile4, tuiles[3].getType(), tuiles[3].getNbTravailleurs());
        mettreImage(tuile5, tuiles[4].getType(), tuiles[4].getNbTravailleurs());
    }

    private void mettreImage(ImageView tuile, String type, int nbTravailleurs) {
        Image image;

        switch (type) {
            case ("piment"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/piment1.jpg"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/piment2.jpg"));
                }
                tuile.setImage(image);
                break;
            case ("haricot"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/haricot1.png"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/haricot2.png"));
                }
                tuile.setImage(image);
                break;
            case ("banane"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/banane1.png"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/banane2.png"));
                }
                tuile.setImage(image);
                break;
            case ("patate"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/patate1.png"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/patate2.png"));
                }
                tuile.setImage(image);
                break;
            case ("sucre"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/sucre1.png"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/sucre2.png"));
                }
                tuile.setImage(image);
                break;
        }
    }
}
