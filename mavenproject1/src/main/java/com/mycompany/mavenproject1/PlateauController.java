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
    private ImageView tuile1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chargement de l'image "source.png"
        final ImageView img = new ImageView();
        final Image sourceImg = new Image(getClass().getResourceAsStream("/images/source.png"));
        img.setImage(sourceImg);

        // Placement aléatoire de la source
        plateau.add(img, Source.getInstance().getX(), Source.getInstance().getY());
    }

    public void initPartie() {
        // Initialisation d'une nouvelle partie
        Partie partie = new Partie();
        partie.initPartie();

        Image image;

        // Première tuile de chaque pile
        Tuiles[] tuiles = partie.getFirstCarte();

        // Test avec la première tuile
        String type = tuiles[0].getType();
        int nbTravailleurs = tuiles[0].getNbTravailleurs();

        switch (type) {
            case ("piment"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/piment1.jpg"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/piment2.jpg"));
                }
                tuile1.setImage(image);
                break;
            case ("haricot"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/haricot1.jpg"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/haricot2.jpg"));
                }
                tuile1.setImage(image);
                break;
            case ("banane"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/banane1.jpg"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/banane2.jpg"));
                }
                tuile1.setImage(image);
                break;
            case ("patate"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/patate1.jpg"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/patate2.jpg"));
                }
                tuile1.setImage(image);
                break;
            case ("sucre"):
                if (nbTravailleurs == 1) {
                    image = new Image(getClass().getResourceAsStream("/images/sucre1.jpg"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/images/sucre2.jpg"));
                }
                tuile1.setImage(image);
                break;
        }

    }
}
