package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Jeu.Tuiles;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

public class PlateauController implements Initializable {

    @FXML
    private GridPane plateau;

    @FXML
    private ImageView tuile1, tuile2, tuile3, tuile4, tuile5;

    @FXML
    private Label j1Nom, j1Couleur;
    @FXML
    private Label j2Nom, j2Couleur;
    @FXML
    private Label j3Nom, j3Couleur;
    @FXML
    private Label j4Nom, j4Couleur;
    private ArrayList<Joueur> listeJoueurs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chargement de l'image "source.png"
        final ImageView img = new ImageView();
        final Image sourceImg = new Image(getClass().getResourceAsStream("/images/source.png"));
        img.setImage(sourceImg);

        // Placement aléatoire de la source
        plateau.add(img, Source.getInstance().getX(), Source.getInstance().getY());
    }
    static Partie partie = new Partie();

    public void phase1() {
        // Initialisation d'une nouvelle partie
        
        partie.initPartie();
        for (int i=0;i<=3;i++){
        // On récupère la première tuile de chaque pile
            Tuiles[] tuiles = partie.getFirstCarte();

            // On affiche la première tuile
            mettreImage(tuile1, tuiles[0].getType(), tuiles[0].getNbTravailleurs());
            mettreImage(tuile2, tuiles[1].getType(), tuiles[1].getNbTravailleurs());
            mettreImage(tuile3, tuiles[2].getType(), tuiles[2].getNbTravailleurs());
            mettreImage(tuile4, tuiles[3].getType(), tuiles[3].getNbTravailleurs());
            mettreImage(tuile5, tuiles[4].getType(), tuiles[4].getNbTravailleurs());

            // Affichage des informations sur les joueurs
            listeJoueurs = partie.getListeJoueurs();
            j1Nom.setText(listeJoueurs.get(0).getNom());
            j1Couleur.setText(listeJoueurs.get(0).getCouleur());
            j2Nom.setText(listeJoueurs.get(1).getNom());
            j2Couleur.setText(listeJoueurs.get(1).getCouleur());
            j3Nom.setText(listeJoueurs.get(2).getNom());
            j3Couleur.setText(listeJoueurs.get(2).getCouleur());
            j4Nom.setText(listeJoueurs.get(3).getNom());
            j4Couleur.setText(listeJoueurs.get(3).getCouleur());
        
            //phase 2
            Map<Joueur, String> enchere = partie.faireUneEnchere();
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7e40534d323620accb29a46337b1751db58028e
            partie.changerConstructeur(enchere);
            int choixCarte=Integer.parseInt(JOptionPane.showInputDialog("choisissez votre carte ! :"));
            Tuiles tuile = tuiles[choixCarte];
            int choixX=Integer.parseInt(JOptionPane.showInputDialog("choisissez la colonne ou pacer votre carte ! :"));
            int choixY=Integer.parseInt(JOptionPane.showInputDialog("choisissez la ligne ou placez votre carte ! :"));
<<<<<<< HEAD
=======
=======
>>>>>>> f6aeed3474dc9f24e68e63dca4d7eb25cd6e1585
>>>>>>> b7e40534d323620accb29a46337b1751db58028e
        }
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
