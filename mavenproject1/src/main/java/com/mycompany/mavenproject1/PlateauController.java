package com.mycompany.mavenproject1;

import Reseau.InterfaceClient;
import Reseau.InterfaceServeur;
import Reseau.Serveur;
import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Jeu.Tuiles;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
    @FXML
    private Label j5Nom, j5Couleur;
    private ArrayList<InterfaceClient> listeJoueurs;
    
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
    /**
     * fonction pour sauvegarder les scores de la partie au format JSON
     */

    public void saveScore(){
        String filePath="./saveScore.json";
        String res="{\"joueurs\" :[\n";
        for(final Joueur j :partie.getListeJoueurs()){
            System.out.println(j.getNom());
            res+=j.toJSON()+",\r\n";
        }
        res=res.substring(0,(res.length()-3));
        res+="]}";
        try{
            FileWriter fw= new FileWriter("saveScore.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(res);
            bw.flush();
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }
    /**
     * fonctions pour sauvegarder la partie au format JSON
     */
    public void savePartie(){
        String res="{\n\"tuiles\" :[\n";
        for(final Tuiles t :partie.getPile1()){
            res+=t.toJSON()+",\r\n";
        }
        for(final Tuiles t :partie.getPile2()){
            res+=t.toJSON()+",\r\n";
        }
        for(final Tuiles t :partie.getPile3()){
            res+=t.toJSON()+",\r\n";
        }
        for(final Tuiles t :partie.getPile4()){
            res+=t.toJSON()+",\r\n";
        }
        for(final Tuiles t :partie.getPile5()){
            res+=t.toJSON()+",\r\n";
        }
        res=res.substring(0,(res.length()-3));
        res+="}]";
        try{
            FileWriter fw= new FileWriter("savePartie.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(res);
            bw.flush();
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        } 
        res=",\n\"joueurs\" :[\n";
        for(final Joueur j :partie.getListeJoueurs()){
            res+=j.toJSON()+",\r\n";
        }
        res=res.substring(0,(res.length()-3));
        res+="]}";
        try{
            FileWriter fw= new FileWriter("savePartie.json",true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(res);
            bw.flush();
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void phase1(InterfaceServeur s) throws InterruptedException, RemoteException {
        /* PHASE 1 */
        // Initialisation d'une nouvelle partie
        partie = new Partie();
        partie.setServeur(s);
        partie.initPartie();
        
        // Affichage de la position de la source
        posSource.setText("x : " + Source.getInstance().getX() + "\ny : " + Source.getInstance().getY());
        
        // On récupère la première tuile de chaque pile
        Tuiles[] tuiles = partie.getFirstCarte();

        // On affiche la première tuile
        mettreImage(tuile1, tuiles[0].getType(), tuiles[0].getNbTravailleurs(),-1,-1);
        mettreImage(tuile2, tuiles[1].getType(), tuiles[1].getNbTravailleurs(),-1,-1);
        mettreImage(tuile3, tuiles[2].getType(), tuiles[2].getNbTravailleurs(),-1,-1);
        mettreImage(tuile4, tuiles[3].getType(), tuiles[3].getNbTravailleurs(),-1,-1);
        mettreImage(tuile5, tuiles[4].getType(), tuiles[4].getNbTravailleurs(),-1,-1);

        // Affichage des informations sur les joueurs
        listeJoueurs = s.getListeClient();
        j1Nom.setText(listeJoueurs.get(0).getNom());
        j1Couleur.setText(listeJoueurs.get(0).getCouleur());
        j2Nom.setText(listeJoueurs.get(1).getNom());
        j2Couleur.setText(listeJoueurs.get(1).getCouleur());
        j3Nom.setText(listeJoueurs.get(2).getNom());
        j3Couleur.setText(listeJoueurs.get(2).getCouleur());
        j4Nom.setText(listeJoueurs.get(3).getNom());
        j4Couleur.setText(listeJoueurs.get(3).getCouleur());
        j5Nom.setText(listeJoueurs.get(4).getNom());
        j5Couleur.setText(listeJoueurs.get(4).getCouleur());

        this.phase2();

    }

    public void phase2() throws RemoteException {
        
        /* PHASE 2 */
        Map<Joueur, String> enchere = partie.faireUneEnchere();
        partie.changerConstructeur(enchere);

        dragAndDrop(tuile1);
        dragAndDrop(tuile2);
        dragAndDrop(tuile3);
        dragAndDrop(tuile4);
        dragAndDrop(tuile5);
        this.phase3();
    }
    
    public void phase3(){
        List<Canal> listeCanalPose = partie.getListeCanalPose();
        dragAndDrop(canalPropHoriz);
        dragAndDrop(canalPropVerti);
    }
        
        // enchères des joueurs
        /* PHASE 3 */

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
 * fonctions d'affichage des tuiles
 * @param tuile
 * @param type type de la tuiles (banane, patates,...)
 * @param nbTravailleurs
 * @param x position de la tuiles a placer si =0, placer sur les piles de tuiles
 * @param y 
 */
    private void mettreImage(ImageView tuile, String type, int nbTravailleurs, int x, int y) {
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
                
                if(x >=0){
                    plateau.add(new ImageView(image),x,y);
                } else {
                    tuile.setImage(image);
                }
                
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

                // Cas d'une tuile
                if (db.getImage().getHeight() == 100.0 && db.getImage().getWidth() == 100.0) {
                    // Vérifie que la position est acceptée pour une tuile
                    if ((col == 1 || col == 2 || col == 4 || col == 5 || col == 7 || col == 8 || col == 10 || col == 11)
                            && (lig == 1 || lig == 2 || lig == 4 || lig == 5 || lig == 7 || lig == 8)) {
                        // Vérifie que la place n'est pas déjà occupée par une autre tuile
                        if (!getNodeByRowColumnIndex(plateau, lig, col).isDisable()) {
                            plateau.add(new ImageView(db.getImage()), col, lig);
                            getNodeByRowColumnIndex(plateau, lig, col).setDisable(true);
                            success = true;
                        }
                    }
                }
                
                List<Canal> listeCanalPose = partie.getListeCanalPose();
                
                // Cas d'un canal horizontal
                if (db.getImage().getHeight() == 10.0 && db.getImage().getWidth() == 100.0) {
                    // Vérifie que la position est acceptée pour un canal horizontal
                    if ((col == 1 || col == 4 || col == 7 || col == 10)
                            && (lig == 0 || lig == 3 || lig == 6 || lig == 9)) {
                        // Vérifier que le canal est près de la source ou d'un autre canal
                        Canal c = new Canal(col, lig, col + 1, lig);
                        if(c.poserCanalPlateau(Source.getInstance(), listeCanalPose)){
                            plateau.add(new ImageView(db.getImage()), col, lig);
                            plateau.add(new ImageView(db.getImage()), col + 1, lig);
                            success = true;
                        }
                    }
                }

                // Cas d'un canal vertical
                if (db.getImage().getHeight() == 100.0 && db.getImage().getWidth() == 10.0) {
                    // Vérifie que la position est acceptée pour un canal vertical
                    if ((col == 0 || col == 3 || col == 6 || col == 9 || col == 12)
                            && (lig == 1 || lig == 4 || lig == 7)) {
                        // Vérifier que le canal est près de la source ou d'un autre canal
                        Canal c = new Canal(col, lig, col, lig + 1);
                        if(c.poserCanalPlateau(Source.getInstance(), listeCanalPose)){
                            plateau.add(new ImageView(db.getImage()), col, lig);
                            plateau.add(new ImageView(db.getImage()), col, lig + 1);
                            success = true;
                        }
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
    
    /**
     * Retourne le noeud d'une GridPane à une colonne et une ligne définie
     * @param gridPane
     * @param lig
     * @param col
     * @return 
     */
    private Node getNodeByRowColumnIndex(GridPane gridPane, final int lig, final int col) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == lig && gridPane.getColumnIndex(node) == col) {
                result = node;
                break;
            }
        }
        return result;
    }

    /*PHASE 5*/
    /**
     * 
     * @param tuiles liste des tuiles posée SUR le plateau
     */
    private void secheresse(Tuiles[] tuiles){
        Tuiles t = tuiles[0];
        t.setX((int) tuile1.getLayoutX());
        t.setY((int) tuile1.getLayoutY());
        partie.getTuilesJoue().add(t);
        t = tuiles[1];
        t.setX((int) tuile2.getLayoutX());
        t.setY((int) tuile2.getLayoutY());
        partie.getTuilesJoue().add(t);
        t = tuiles[2];
        t.setX((int) tuile3.getLayoutX());
        t.setY((int) tuile3.getLayoutY());
        partie.getTuilesJoue().add(t);
        t = tuiles[3];
        t.setX((int) tuile4.getLayoutX());
        t.setY((int) tuile4.getLayoutY());
        partie.getTuilesJoue().add(t);
        t = tuiles[4];
        t.setX((int) tuile5.getLayoutX());
        t.setY((int) tuile5.getLayoutY());
        partie.getTuilesJoue().add(t);
        for(final Tuiles tu: partie.getTuilesJoue()){
            for(final Canal c: partie.getListeCanalPose()){
                if(tu.getX()-1==c.getxDeb()||tu.getX()+1==c.getxDeb()
                  ||tu.getX()-1==c.getxFin()||tu.getX()+1==c.getxFin()
                  ||tu.getY()-1==c.getyDeb()||tu.getY()+1==c.getyDeb()
                  ||tu.getY()-1==c.getyFin()||tu.getY()+1==c.getyFin()){
                  tu.setIrigue(true);break;
                }
            }
            if(!tu.getIrigue()){
                tu.setNbTravailleurs(tu.getNbTravailleurs()-1);
                if(tu.getNbTravailleurs()==0){
                    Image desert = new Image(getClass().getResourceAsStream("/images/vide.jpg"));
                    plateau.add(new ImageView(desert),tu.getX(),tu.getY());
                }else
                {
                    mettreImage(null, tu.getType(), tu.getNbTravailleurs(),tu.getX(),tu.getY());
                }
            }
        }
    }
}
