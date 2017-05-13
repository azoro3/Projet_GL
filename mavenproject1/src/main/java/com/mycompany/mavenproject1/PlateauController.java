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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlateauController implements Initializable {

    static Partie partie = new Partie();
    static Tuiles[] tuiles;

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
    @FXML
    private Label p1, p2, p3, p4, p5, e1, e2, e3, e4, e5;
    private final Label[] p = new Label[5];
    private final Label[] e = new Label[5];
    private ArrayList<Joueur> listeJoueurs;
    static int nbTour =1;

    Image canalHorizVide = new Image(getClass().getResourceAsStream("/images/canal_horiz.png"));
    Image canalVertiVide = new Image(getClass().getResourceAsStream("/images/canal_verti.png"));

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

    /**
     * Phase 1
     * @throws InterruptedException 
     */
    public void phase1() throws InterruptedException {
        // Initialisation d'une nouvelle partie
        if(nbTour==1){
        partie = new Partie();
        partie.initPartie();
        
        // Affichage de la position de la source
        posSource.setText("x : " + Source.getInstance().getX() + "\ny : " + Source.getInstance().getY());
        
        listeJoueurs = partie.getListeJoueurs();
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
        }
        Task<Boolean> afficherTuile = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                // On récupère la première tuile de chaque pile
                tuiles=null;
                tuiles = partie.getFirstCarte();

                // On affiche la première tuile
                mettreImage(tuile1, tuiles[0].getType(), tuiles[0].getNbTravailleurs(), -1, -1);
                mettreImage(tuile2, tuiles[1].getType(), tuiles[1].getNbTravailleurs(), -1, -1);
                mettreImage(tuile3, tuiles[2].getType(), tuiles[2].getNbTravailleurs(), -1, -1);
                mettreImage(tuile4, tuiles[3].getType(), tuiles[3].getNbTravailleurs(), -1, -1);
                mettreImage(tuile5, tuiles[4].getType(), tuiles[4].getNbTravailleurs(), -1, -1);
                return true;
            }
        };

        afficherTuile.setOnSucceeded(e -> {
            this.phase2();
        });

        new Thread(afficherTuile).start();
    }

    /**
     * Phase 2
     */
    public void phase2() {
        Map<Joueur, String> enchere = partie.faireUneEnchere();
        partie.changerConstructeur(enchere);
        
        int i = 0;
        p[0] = this.p1; p[1] = this.p2; p[2] = this.p3; p[3] = this.p4; p[4] = this.p5;
        e[0] = this.e1; e[1] = this.e2; e[2] = this.e3; e[3] = this.e4; e[4] = this.e5;
        
        for (HashMap.Entry<Joueur, String> entry : enchere.entrySet()) {
            this.p[i].setText(entry.getKey().getNom());
            this.e[i].setText(entry.getValue());
            i++;
        }
        
        // Lorsque toutes les tuiles ont été posés sur le plateau, on peu passer à la phase 3
        Task<Boolean> dragAndDropTuile = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                while (tuile1.isVisible() || tuile2.isVisible() || tuile3.isVisible()
                        || tuile4.isVisible() || tuile5.isVisible()) {
                    dragAndDrop(tuile1);
                    dragAndDrop(tuile2);
                    dragAndDrop(tuile3);
                    dragAndDrop(tuile4);
                    dragAndDrop(tuile5);
                }
                return true;
            }
        };

        dragAndDropTuile.setOnSucceeded(e -> {
            try {
                //System.out.println(dragAndDropTuile.getValue());
                this.phase3();
            } catch (InterruptedException ex) {
                Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        new Thread(dragAndDropTuile).start();
    }

    /**
     * Phase 3
     */
    public void phase3() throws InterruptedException {
        Map<Joueur, Integer> enchere = new HashMap<>();
        Map<Joueur, String> passe = new HashMap<>();
        Map<Joueur, Integer> suivi = new HashMap<>();
        Map<Joueur, Canal> propositionJoueur = new HashMap<>();
        String res;
        int miseMax=0;
        for (final Joueur j : partie.getListeJoueurs()) {
            // Seul les joueurs qui ne sont pas constructeur peuvent miser
            // Le constructeur n'intervient qu'à la fin de la phase
            
            if (!j.isEstConstructeur()) {

                try {
                    // Chaque joueur doit décider s'il fait une proposition, s'il soutient une autre proposition ou s'il passe
                    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Phase3.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Mises");
                    dialogStage.initModality(Modality.WINDOW_MODAL);

                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    Phase3Controller controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setEnchere(enchere);
                    controller.setJoueurActuel(j);

                    dialogStage.showAndWait();
                    res = controller.getResultat();

                    // Cas où le joueur passe son tour
                    // Il n'intervient pas dans la construction des canaux
                    if (res.equals("Passe")) {
                        passe.put(j, res);
                    } // Cas où le joueur suit une mise existante :
                    // Il soutient une nouvelle mise de 1 Escudos
                    else if (res.contains("Escudos")) {
                        int mise = Integer.parseInt(res.substring(0, res.indexOf(" ")));
                        j.setSolde(mise);
                        suivi.put(j, mise + 1);
                    } // Cas où le joueur fait une nouvelle proposition :
                    // Il fait une proposition pour creuser un nouveau canal
                    else {
                        enchere.put(j, Integer.parseInt(res));
                        j.setSolde(Integer.parseInt(res));
                        if(Integer.parseInt(res)>miseMax);
                        miseMax=Integer.parseInt(res);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Chaque joueur qui a fait une proposition doit creuser un canal
        enchere.entrySet().forEach((Map.Entry<Joueur, Integer> entry) -> {
            try {
                String couleur = entry.getKey().getCouleur();
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PropositionCanal.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Creuser un canal");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                PropositionCanalController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setPartie(this.partie);
                controller.setJoueurActuel(entry.getKey());

                dialogStage.showAndWait();
                Canal res3 = controller.getResultat();
                propositionJoueur.put(entry.getKey(), res3);
                this.afficherPropositionCanal(couleur, res3.getxDeb(), res3.getxFin(), res3.getyDeb(), res3.getyFin());

            } catch (IOException ex) {
                Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Demander au constructeur de canal s'il veut accepter une offre où construire où il veut
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Contruire un autre canal");
        enchere.entrySet().forEach((entry) -> {
            choices.add(entry.getKey().getNom() + " a misé " + entry.getValue() + " Escudos");
        });

        for (final Joueur j : partie.getListeJoueurs()) {
            if (j.isEstConstructeur()) {
                ChoiceDialog<String> dialog = new ChoiceDialog<>();
                dialog.getItems().addAll(choices);
                dialog.setTitle("Mises enregistrées");
                dialog.setHeaderText(null);
                dialog.setContentText("Choissisez une proposition : ");
                dialog.setSelectedItem("Construire un autre canal");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {

                    System.out.println("Réponse : " + result.get());

                    if (result.get().contains("canal") ) {
                        // Nouvelle fenêtre avec plusieurs proposition, puis ajout à la liste des canaux posés
                        j.setSolde(-(miseMax+1));
                        try {
                            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PropositionCanal.fxml"));
                            AnchorPane page = (AnchorPane) loader.load();
                            Stage dialogStage = new Stage();
                            dialogStage.setTitle("Creuser un canal");
                            dialogStage.initModality(Modality.WINDOW_MODAL);
                            Scene scene = new Scene(page);
                            dialogStage.setScene(scene);
                            PropositionCanalController controller = loader.getController();
                            controller.setDialogStage(dialogStage);
                            controller.setPartie(this.partie);

                            dialogStage.showAndWait();
                            Canal res4 = controller.getResultat();

                            // Effacer les propositions des joueurs
                            propositionJoueur.entrySet().forEach((entry) -> {
                                Canal c = entry.getValue();
                                // Le canal est vertical
                                if (c.getxDeb() == c.getxFin()) {
                                    plateau.add(new ImageView(this.canalVertiVide), c.getxDeb(), c.getyDeb());
                                    plateau.add(new ImageView(this.canalVertiVide), c.getxDeb(), c.getyDeb() + 1);
                                } // Le canal est horizontal
                                else if (c.getyDeb() == c.getyFin()) {
                                    plateau.add(new ImageView(this.canalHorizVide), c.getxDeb(), c.getyDeb());
                                    plateau.add(new ImageView(this.canalHorizVide), c.getxDeb() + 1, c.getyDeb());
                                }
                            });

                            this.afficherPropositionCanal("Bleu", res4.getxDeb(), res4.getxFin(), res4.getyDeb(), res4.getyFin());

                            partie.addListeCanauxPoses(res4);
                        } catch (IOException ex) {
                            Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (result.get().contains("Escudos")) {
                        // Récupérer le canal, puis ajout à la liste des canaux posés, puis coloration en bleu
                        String joueur = result.get().substring(0, result.get().indexOf(" "));
                        Canal res5 = new Canal();
                        propositionJoueur.entrySet().forEach((Map.Entry<Joueur, Canal> entry) -> {
                            if (entry.getKey().getNom().equals(joueur)) {
                                res5.setxDeb(entry.getValue().getxDeb());
                                res5.setxFin(entry.getValue().getxFin());
                                res5.setyDeb(entry.getValue().getyDeb());
                                res5.setyFin(entry.getValue().getyFin());
                                int mise = enchere.get(entry.getKey().getNom());
                                entry.getKey().setSolde(-mise);
                            }
                        });

                        // Effacer les propositions des joueurs
                        propositionJoueur.entrySet().forEach((entry) -> {
                            Canal c = entry.getValue();
                            // Le canal est vertical
                            if (c.getxDeb() == c.getxFin()) {
                                plateau.add(new ImageView(this.canalVertiVide), c.getxDeb(), c.getyDeb());
                                plateau.add(new ImageView(this.canalVertiVide), c.getxDeb(), c.getyDeb() + 1);
                            } // Le canal est horizontal
                            else if (c.getyDeb() == c.getyFin()) {
                                plateau.add(new ImageView(this.canalHorizVide), c.getxDeb(), c.getyDeb());
                                plateau.add(new ImageView(this.canalHorizVide), c.getxDeb() + 1, c.getyDeb());
                            }
                        });
                        this.afficherPropositionCanal("Bleu", res5.getxDeb(), res5.getxFin(), res5.getyDeb(), res5.getyFin());
                        partie.addListeCanauxPoses(res5);
                    }
                }
            }
        }
     this.phase4();
    }
    
    /**
     * Phase 4
     */
    public void phase4() throws InterruptedException{
        System.out.println(partie.getListeJoueurs().size());
        for(final Joueur j:partie.getListeJoueurs()){
            if(j.getCanal().getIsPosed()==false){
                String res=JOptionPane.showInputDialog(j.getNom()+", voulez vous posez un canal ? (o/n)");
                if("o".equals(res)){
                    Canal res2 = null ;
                    try {
                        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PropositionCanal.fxml"));
                        AnchorPane page = (AnchorPane) loader.load();
                        Stage dialogStage = new Stage();
                        dialogStage.setTitle("Creuser un canal");
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        Scene scene = new Scene(page);
                        dialogStage.setScene(scene);
                        PropositionCanalController controller = loader.getController();
                        controller.setDialogStage(dialogStage);
                        controller.setPartie(PlateauController.partie);
                        
                        dialogStage.showAndWait();
                        res2 = controller.getResultat();
                        
                        // Effacer les propositions des joueurs
                        // Le canal est vertical
                        if (res2.getxDeb() == res2.getxFin()) {
                            plateau.add(new ImageView(this.canalVertiVide), res2.getxDeb(), res2.getyDeb());
                            plateau.add(new ImageView(this.canalVertiVide), res2.getxDeb(), res2.getyDeb() + 1);
                        } // Le canal est horizontal
                        else if (res2.getyDeb() == res2.getyFin()) {
                            plateau.add(new ImageView(this.canalHorizVide), res2.getxDeb(), res2.getyDeb());
                            plateau.add(new ImageView(this.canalHorizVide), res2.getxDeb() + 1, res2.getyDeb());
                        }
                        ;
                    } catch (IOException ex) {
                        Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.afficherPropositionCanal("Bleu", res2.getxDeb(), res2.getxFin(), res2.getyDeb(), res2.getyFin());
                    partie.addListeCanauxPoses(res2);
                    break;
                }
            } else {
            }
        }
        this.phase6();
        //this.phase5();
    }
    
    /**
     * Phase 5
     */
    private void phase5() throws InterruptedException{
        System.out.println("sécheresse");
        Tuiles t = partie.getPile1().get(0);
        t.setX((int) tuile1.getX());
        t.setY((int) tuile1.getY());
        partie.getTuilesJoue().add(partie.getPile1().remove(1));
        t = partie.getPile2().get(0);
        t.setX((int) tuile2.getX());
        t.setY((int) tuile2.getY());
        partie.getTuilesJoue().add(partie.getPile2().remove(1));
        partie.getPile3().get(0);
        t.setX((int) tuile3.getX());
        t.setY((int) tuile3.getY());
        partie.getTuilesJoue().add(partie.getPile3().remove(1));
        partie.getPile4().get(0);
        t.setX((int) tuile4.getX());
        t.setY((int) tuile4.getY());
        partie.getTuilesJoue().add(partie.getPile4().remove(1));
        partie.getPile5().get(0);
        t.setX((int) tuile5.getX());
        t.setY((int) tuile5.getY());
        partie.getTuilesJoue().add(partie.getPile5().remove(1));
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
        this.phase6();
    }
    
    /**
     * Phase 6
     */
    public void phase6() throws InterruptedException{
        System.out.println("Revenu");
        this.listeJoueurs.forEach((j) -> {
            j.setSolde(3);
        });
        if(nbTour==9){
            this.saveScore();
        }
        else{
            nbTour++;
            try {
                this.phase1();
            } catch (InterruptedException ex) {
                Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            case "Blanc":
                canalHoriz = new Image(getClass().getResourceAsStream("/images/canal_horiz_blanc.png"));
                canalVerti = new Image(getClass().getResourceAsStream("/images/canal_verti_blanc.png"));
                break;
            case "Bleu":
                canalHoriz = new Image(getClass().getResourceAsStream("/images/canal_horiz_eau.png"));
                canalVerti = new Image(getClass().getResourceAsStream("/images/canal_verti_eau.png"));
                break;
        }

        // Le canal est horizontal
        if (yDeb == yFin) {
            plateau.add(new ImageView(canalHoriz), xDeb, yDeb);
            plateau.add(new ImageView(canalHoriz), xDeb + 1, yDeb);
        }

        // Le canal est vertical
        if (xDeb == xFin) {
            plateau.add(new ImageView(canalVerti), xDeb, yDeb);
            plateau.add(new ImageView(canalVerti), xDeb, yDeb + 1);
        }
    }

    /**
     * Fonction d'affichage des tuiles
     *
     * @param tuile
     * @param type type de la tuiles (banane, patates,...)
     * @param nbTravailleurs
     * @param x position de la tuiles a placer. Si = 0, placer sur les piles de tuiles
     * @param y
     */
    private void mettreImage(ImageView tuile, String type, int nbTravailleurs, int x, int y) {
        Image image=null;

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
                break;
        }
        if(x >=0){
                    plateau.add(new ImageView(image),x,y);
                } else {
                    tuile.setImage(image);
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
}
