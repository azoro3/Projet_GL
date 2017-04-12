package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Jeu.Tuiles;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

public class PlateauController implements Initializable {

    static Partie partie = new Partie();
    
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
    

    public void phase1() {
        // Initialisation d'une nouvelle partie
        partie.initPartie();
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
        
        Map<Joueur, String> enchere = partie.faireUneEnchere();
        
        // Phase 2
        partie.changerConstructeur(enchere);

        dragAndDrop(tuile1);
        dragAndDrop(tuile2);
        dragAndDrop(tuile3);
        dragAndDrop(tuile4);
        dragAndDrop(tuile5);
        
        
        /*
        for (int i = 0; i <= 3; i++) {
            //phase 2
            int choixCarte = Integer.parseInt(JOptionPane.showInputDialog("choisissez votre carte ! :"));
            Tuiles tuile = tuiles[choixCarte];
            int choixX = Integer.parseInt(JOptionPane.showInputDialog("choisissez la colonne ou pacer votre carte ! :"));
            int choixY = Integer.parseInt(JOptionPane.showInputDialog("choisissez la ligne ou placez votre carte ! :"));
        }
        */
        
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
    
    /**
     * Fonction qui gère le Drag and Drop des tuiles sur le plateau de jeu.
     * @param source
     */
    private void dragAndDrop(final ImageView source) {
        // Drag une des tuiles des piles
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(source.getImage());
                db.setContent(cbContent);
                event.consume();
            }
        });

        // Accepter le drop sur la grille du plateau
        plateau.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != plateau && event.getDragboard().hasImage()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });

        // Drop une tuile sur le plateau
        plateau.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                Node node = event.getPickResult().getIntersectedNode();
                if (node != plateau && db.hasImage()) {
                    Integer col = GridPane.getColumnIndex(node);
                    Integer lig = GridPane.getRowIndex(node);

                    // Certaines cases seulement peuvent accueillir des tuiles
                    if ((col == 1 || col == 2 || col == 4 || col == 5 || col == 7 || col == 8 || col == 10 || col == 11)
                            && (lig == 1 || lig == 2 || lig == 4 || lig == 5 || lig == 7 || lig == 8)) {
                        //System.out.println(col + " " + lig);
                        plateau.add(new ImageView(db.getImage()), col, lig);
                        success = true;
                    }

                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        // Lorsque la tuile est déposée sur le plateau, elle n'est plus visible dans les piles
        source.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    //source.setImage(new Image("/images/vide.jpg"));
                    source.setVisible(false);
                }
                event.consume();
            }
        });

    }
}
