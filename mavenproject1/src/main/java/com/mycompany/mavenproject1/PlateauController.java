package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Jeu.Tuiles;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chargement de l'image "source.png"
        final ImageView img = new ImageView();
        final Image sourceImg = new Image(getClass().getResourceAsStream("/images/source.png"));
        img.setImage(sourceImg);

        // Placement aléatoire de la source
        plateau.add(img, Source.getInstance().getX(), Source.getInstance().getY());
    }
    
    public void saveScore(){
        String filePath="./saveScore.json";
        String res="{\"joueurs\" :[\n";
        for(final Joueur j :this.listeJoueurs){
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
    
    public void savePartie(){
        String res="{\"tuiles\" :[\n";
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
        res+="]}";
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
        res="\n{\"joueurs\" :[\n";
        for(final Joueur j :this.listeJoueurs){
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

    public void phase1() throws InterruptedException {
        /* PHASE 1 */
        // Initialisation d'une nouvelle partie
        partie = new Partie();
        partie.initPartie();
        
        // Affichage de la position de la source
        posSource.setText("x : " + Source.getInstance().getX() + " y : " + Source.getInstance().getY());
        
        // On récupère la première tuile de chaque pile
        Tuiles[] tuiles = partie.getFirstCarte();

        // On affiche la première tuile
        mettreImage(tuile1, tuiles[0].getType(), tuiles[0].getNbTravailleurs(),-1,-1);
        mettreImage(tuile2, tuiles[1].getType(), tuiles[1].getNbTravailleurs(),-1,-1);
        mettreImage(tuile3, tuiles[2].getType(), tuiles[2].getNbTravailleurs(),-1,-1);
        mettreImage(tuile4, tuiles[3].getType(), tuiles[3].getNbTravailleurs(),-1,-1);
        mettreImage(tuile5, tuiles[4].getType(), tuiles[4].getNbTravailleurs(),-1,-1);

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

        dragAndDropTuile(tuile1);
        dragAndDropTuile(tuile2);
        dragAndDropTuile(tuile3);
        dragAndDropTuile(tuile4);
        dragAndDropTuile(tuile5);

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
                            // TODO : il faut encore gérer les couleurs (pour l'instant ils sont tous verts)
                            for (Map.Entry<Joueur, Canal> prop : joueurCanal.entrySet()) {
                                afficherPropositionCanal(prop.getValue().getxDeb(), prop.getValue().getyDeb(), prop.getValue().getxFin(), prop.getValue().getyFin());
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
        
    }

    /**
     * Fonction d'affichage des propositions de canal.
     *
     * @param xDeb
     * @param xFin
     * @param yDeb
     * @param yFin
     */
    private void afficherPropositionCanal(int xDeb, int xFin, int yDeb, int yFin) {
        Image canalHoriz = new Image(getClass().getResourceAsStream("/images/canal_horiz_prop.png"));
        Image canalVerti = new Image(getClass().getResourceAsStream("/images/canal_verti_prop.png"));

        // Le canal est horizontal
        if (yDeb == yFin) {
            plateau.add(new ImageView(canalHoriz), xDeb + 1, yDeb + 1);
        }

        // Le canal est vertical
        if (xDeb == xFin) {
            plateau.add(new ImageView(canalVerti), xDeb + 1, yDeb + 1);
        }
    }

    /**
     * Fonction d'affichage des tuiles.
     *
     * @param tuile
     * @param type
     * @param nbTravailleurs
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
     * Fonction qui gère le Drag and Drop des tuiles sur le plateau de jeu.
     *
     * @param source
     */
    private void dragAndDropTuile(final ImageView source) {
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
    /*PHASE 4*/
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
