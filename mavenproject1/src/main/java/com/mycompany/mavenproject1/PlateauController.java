package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Jeu.Tuiles;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private Label posSource;

    @FXML
    private Label j1Nom, j1Couleur;
    @FXML
    private Label j2Nom, j2Couleur;
    @FXML
    private Label j3Nom, j3Couleur;
    @FXML
    private Label j4Nom, j4Couleur;
    private ArrayList<Joueur> listeJoueurs;
    
    @FXML
    private ImageView canalPropHoriz, canalPropVerti;

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
        /* PHASE 1 */
        // Initialisation d'une nouvelle partie
        partie = new Partie();
        partie.initPartie();
        
        // Affichage de la position de la source
        posSource.setText("x : " + Source.getInstance().getX() + "\ny : " + Source.getInstance().getY());
        
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

        /* PHASE 2 */
        partie.changerConstructeur(enchere);

        dragAndDrop(tuile1);
        dragAndDrop(tuile2);
        dragAndDrop(tuile3);
        dragAndDrop(tuile4);
        dragAndDrop(tuile5);
        
        /* PHASE 3 */
        Map<Joueur, Integer> joueurEncher = new HashMap<>();
        Map<Integer, Joueur> encherJoueur = new HashMap<>();
        Map<Joueur, Canal> joueurCanal = new HashMap<>();
        Joueur meilleurJoueur = null;
        Joueur creuseur = null;
        ArrayList<Joueur> ltemp = partie.getListeJoueurs();
        
        // enlever le constructeur
        for (int i = 0; i <= ltemp.size() - 1; i++) {
            if (ltemp.get(i).isEstConstructeur()) {
                creuseur = ltemp.get(i);
                ltemp.remove(i);
            }
        }
        
        // enchères des joueurs
        List<Canal> listeCanalPose = partie.getListeCanalPose();
        
        dragAndDrop(canalPropHoriz);
        dragAndDrop(canalPropVerti);
        
        /*
        for (final Joueur joueur : ltemp) {
            int valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog(joueur.getNom() + ", faites votre enchère !"));
            while (true) {
                // verifie que la personne ne passe pas son tour
                if (valeurEnchere > 0) {
                    // verifie que la personne ait bien l'argent pour la mise
                    if (joueur.getSolde() >= valeurEnchere) {
                        // verifie que personne n'ai misé cela avant
                        if (!joueurEncher.values().contains(valeurEnchere)) {
                            int xdeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de depart du canal ! :"));
                            int ydeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de depart du canal ! :"));
                            int xfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de fin du canal ! :"));
                            int yfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de fin du canal ! :"));
                            Canal c = new Canal(xdeb, ydeb, xfin, yfin);
                            while (!c.poserCanal(Source.getInstance(), listeCanalPose)) {
                                xdeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de depart du canal ! :"));
                                ydeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de depart du canal ! :"));
                                xfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de fin du canal ! :"));
                                yfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de fin du canal ! :"));
                                c = new Canal(xdeb, ydeb, xfin, yfin);
                            }

                            encherJoueur.put(valeurEnchere, joueur);
                            joueurEncher.put(joueur, valeurEnchere);

                            joueurCanal.put(joueur, c);
                            // Affichage de chaque canal proposé par les joueurs sur le plateau de jeu
                            for (Map.Entry<Joueur, Canal> prop : joueurCanal.entrySet()) {
                                afficherPropositionCanal(prop.getKey().getCouleur(), prop.getValue().getxDeb(), prop.getValue().getyDeb(), prop.getValue().getxFin(), prop.getValue().getyFin());
                            }

                            Iterator it = joueurEncher.values().iterator();
                            int sup = -1;
                            // prendre le meilleur joueur par rapport a toutes les encheres
                            while (it.hasNext()) {
                                int val = (int) it.next();
                                if (val > sup) {
                                    sup = val;
                                }
                            }

                            if (sup == -1 || joueur == encherJoueur.get(sup)) {
                                meilleurJoueur = joueur;
                            }
                            break;
                        } else {
                            valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Quelqu'un à déjà miser cette somme, faites une autre enchères ! :"));
                        }
                    } else {
                        valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Vous n'avez pas assez d'argent pour une telle enchere ! :"));
                    }
                } else {
                    break;
                }
            }
        }
        // afficher la valeur du meilleur joueur pour que le creuseur prenne sa decision (suivre la meilleur proposition on creuser lui meme)
        String reponse;
        // verifie si les joueurs n'ont pas tous passés
        if (meilleurJoueur != null) {
            Alert offreDialog = new Alert(AlertType.INFORMATION);
            offreDialog.setTitle("Meilleur offre");
            offreDialog.setHeaderText(null);
            offreDialog.setContentText("meilleur joueur : " + meilleurJoueur.getNom() + " enchere de : " + joueurEncher.get(meilleurJoueur)
                    + " position canal xdeb : " + joueurCanal.get(meilleurJoueur).getxDeb() + " ydeb : " + joueurCanal.get(meilleurJoueur).getyDeb()
                    + " xfin : " + joueurCanal.get(meilleurJoueur).getxFin() + " yfin : " + joueurCanal.get(meilleurJoueur).getyFin());
            offreDialog.showAndWait();

            reponse = JOptionPane.showInputDialog("Voulez-vous prendre la meilleur enchere ? oui/non");
            while (!reponse.equals("OUI") && !reponse.equals("NON") && !reponse.equals("non") && !reponse.equals("oui") && !reponse.equals("o") && !reponse.equals("n")) {
                reponse = JOptionPane.showInputDialog("Voulez-vous prendre la meilleur enchere ? oui/non");
            }

            if (reponse.equals("oui") || reponse.equals("OUI") || reponse.equals("o")) {
                // si le creuseur choisit de suivre le meilleur joueur il gagne la mise et creuse dans le sens de ce joueur
                listeCanalPose.add(joueurCanal.get(meilleurJoueur));
                creuseur.setSolde(creuseur.getSolde() + joueurEncher.get(meilleurJoueur));
                meilleurJoueur.setSolde(meilleurJoueur.getSolde() - joueurEncher.get(meilleurJoueur));
            } else {
                //sinon il creuse ou il veut mais paye un de plus que le meilleur joueur
                if (creuseur.getSolde() >= joueurEncher.get(meilleurJoueur) + 1) {
                    int xdeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de depart du canal ! :"));
                    int ydeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de depart du canal ! :"));
                    int xfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de fin du canal ! :"));
                    int yfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de fin du canal ! :"));
                    Canal c = new Canal(xdeb, ydeb, xfin, yfin);
                    while (!c.poserCanal(Source.getInstance(), listeCanalPose)) {
                        xdeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de depart du canal ! :"));
                        ydeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de depart du canal ! :"));
                        xfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de fin du canal ! :"));
                        yfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de fin du canal ! :"));
                        c = new Canal(xdeb, ydeb, xfin, yfin);
                    }
                    listeCanalPose.add(c);
                    creuseur.setSolde(creuseur.getSolde() - (joueurEncher.get(meilleurJoueur) + 1));
                } else {
                    listeCanalPose.add(joueurCanal.get(meilleurJoueur));
                    creuseur.setSolde(creuseur.getSolde() + joueurEncher.get(meilleurJoueur));
                    meilleurJoueur.setSolde(meilleurJoueur.getSolde() - joueurEncher.get(meilleurJoueur));
                }
            }
            // si les joueurs ont tous passés alors le creuseur decide lui meme
        } else {
            int xdeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de depart du canal ! :"));
            int ydeb = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de depart du canal ! :"));
            int xfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées x de fin du canal ! :"));
            int yfin = Integer.parseInt(JOptionPane.showInputDialog("définir la coordonées y de fin du canal ! :"));
            Canal c = new Canal(xdeb, ydeb, xfin, yfin);
            while (!c.poserCanal(Source.getInstance(), listeCanalPose)) {
                xdeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de depart du canal ! :"));
                ydeb = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de depart du canal ! :"));
                xfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées x de fin du canal ! :"));
                yfin = Integer.parseInt(JOptionPane.showInputDialog("mauvaise coordonées y de fin du canal ! :"));
                c = new Canal(xdeb, ydeb, xfin, yfin);
            }
            listeCanalPose.add(c);
        }
        */
    }

    /**
     * Fonction d'affichage des propositions de canal.
     *
     * @param couleur couleur du joueur
     * @param xDeb
     * @param xFin
     * @param yDeb
     * @param yFin
     */
    private void afficherPropositionCanal(String couleur, int xDeb, int xFin, int yDeb, int yFin) {
        Image canalHoriz = null;
        Image canalVerti = null;

        switch (couleur) {
            case "Noir":
                canalHoriz = new Image(getClass().getResourceAsStream("/images/canal_horiz_noir.png"));
                canalVerti = new Image(getClass().getResourceAsStream("/images/canal_verti_noir.png"));
                break;
            case "Violet":
                canalHoriz = new Image(getClass().getResourceAsStream("/images/canal_horiz_violet.png"));
                canalVerti = new Image(getClass().getResourceAsStream("/images/canal_verti_violet.png"));
                break;
            case "Beige":
                canalHoriz = new Image(getClass().getResourceAsStream("/images/canal_horiz_beige.png"));
                canalVerti = new Image(getClass().getResourceAsStream("/images/canal_verti_beige.png"));
                break;
            case "Gris":
                canalHoriz = new Image(getClass().getResourceAsStream("/images/canal_horiz_gris.png"));
                canalVerti = new Image(getClass().getResourceAsStream("/images/canal_verti_gris.png"));
                break;
        }

        // Le canal est horizontal
        if (yDeb == yFin) {
            plateau.add(new ImageView(canalHoriz), xDeb + 1, yDeb);
        }

        // Le canal est vertical
        if (xDeb == xFin) {
            plateau.add(new ImageView(canalVerti), xDeb, yDeb + 1);
        }
    }

    /**
     * Fonction d'affichage des tuiles.
     *
     * @param tuile
     * @param type
     * @param nbTravailleurs
     */
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
     * Fonction qui gère le Drag & Drop d'une image (soit une tuile, soit un canal) sur le plateau.
     * @param source 
     */
    private void dragAndDrop(final ImageView source) {
        // Drag une image
        source.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putImage(source.getImage());
            db.setContent(cbContent);
            event.consume();
        });

        // Accepter le drop sur la grille du plateau
        plateau.setOnDragOver((DragEvent event) -> {
            if (event.getGestureSource() != plateau && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Drop une image sur le plateau
        plateau.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            Node node = event.getPickResult().getIntersectedNode();
            if (node != plateau && db.hasImage()) {
                Integer col = GridPane.getColumnIndex(node);
                Integer lig = GridPane.getRowIndex(node);
                
                // Bug lorsque col et lig sont à 0 (on obtient des valeurs à null au lieu de 0)
                if(col == null){
                    col = 0;
                }
                if(lig == null){
                    lig = 0;
                }
                
                //System.out.println("hauteur " + db.getImage().getHeight() + " largeur " + db.getImage().getWidth());
                //System.out.println(col + " " + lig);
                
                // Cas d'une tuile
                // TODO : vérifier que la tuile est près d'un canal irrigué
                if (db.getImage().getHeight() == 100.0 && db.getImage().getWidth() == 100.0) {
                    if ((col == 1 || col == 2 || col == 4 || col == 5 || col == 7 || col == 8 || col == 10 || col == 11)
                            && (lig == 1 || lig == 2 || lig == 4 || lig == 5 || lig == 7 || lig == 8)) {
                        plateau.add(new ImageView(db.getImage()), col, lig);
                        success = true;
                    }
                }
                
                // Cas d'un canal horizontal
                // TODO : Vérifier que le canal est près de la source ou d'un autre canal
                if (db.getImage().getHeight() == 10.0 && db.getImage().getWidth() == 100.0) {
                    if ((col == 1 || col == 4 || col == 7 || col == 10)
                            && (lig == 0 || lig == 3 || lig == 6 || lig == 9)) {
                        plateau.add(new ImageView(db.getImage()), col, lig);
                        plateau.add(new ImageView(db.getImage()), col + 1, lig);
                        success = true;
                    }
                }

                // Cas d'un canal vertical
                // TODO : Vérifier que le canal est près de la source ou d'un autre canal
                if (db.getImage().getHeight() == 100.0 && db.getImage().getWidth() == 10.0) {
                    if ((col == 0 || col == 3 || col == 6 || col == 9 || col == 12)
                            && (lig == 1 || lig == 4 || lig == 7)) {
                        plateau.add(new ImageView(db.getImage()), col, lig);
                        plateau.add(new ImageView(db.getImage()), col, lig + 1);
                        success = true;
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Lorsque l'image est déposée sur le plateau, elle n'est plus visible
        source.setOnDragDone((DragEvent event) -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                source.setVisible(false);
            }
            event.consume();
        });
    }

}
