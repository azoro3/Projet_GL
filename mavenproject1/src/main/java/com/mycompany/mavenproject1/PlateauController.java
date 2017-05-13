package com.mycompany.mavenproject1;

import Reseau.*;
import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Jeu.CanalJ;
import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Tuiles;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.JOptionPane;

public class PlateauController implements Initializable {

    private InterfacePartie partie;

    @FXML
    private GridPane plateau;

    @FXML
    private ImageView tuile1, tuile2, tuile3, tuile4, tuile5;
    
    @FXML
    private Label posSource;


    Image canalHorizVide = new Image(getClass().getResourceAsStream("/images/canal_horiz.png"));
    Image canalVertiVide = new Image(getClass().getResourceAsStream("/images/canal_verti.png"));
    private ArrayList<InterfaceClient> listeJoueurs;
    private InterfaceServeur serv;
    private InterfaceSource source;
    
    @FXML
    private ImageView canalPropHoriz, canalPropVerti;
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
    private InterfaceClient joueurEnCours;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chargement de l'image "source.png"
        final ImageView img = new ImageView();
        final Image sourceImg = new Image(getClass().getResourceAsStream("/images/source.png"));
        img.setImage(sourceImg);
        try {
            this.lancerClient();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Placement aléatoire de la source
        try {
            plateau.add(img,this.source.getX(), this.source.getY());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    /**
     * fonction pour sauvegarder les scores de la partie au format JSON
     */
    public InterfaceServeur getServeur(){
        return this.serv;
    }
    public void saveScore() throws RemoteException {
        String filePath="./saveScore.json";
        String res="{\"joueurs\" :[\n";
        for(final InterfaceClient j :partie.getListeJoueurs()){
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
    public void savePartie() throws RemoteException {
        String res="{\n\"tuiles\" :[\n";
        for(final InterfaceTuiles t :partie.getPile1()){
            res+=t.toJSON()+",\r\n";
        }
        for(final InterfaceTuiles t :partie.getPile2()){
            res+=t.toJSON()+",\r\n";
        }
        for(final InterfaceTuiles t :partie.getPile3()){
            res+=t.toJSON()+",\r\n";
        }
        for(final InterfaceTuiles t :partie.getPile4()){
            res+=t.toJSON()+",\r\n";
        }
        for(final InterfaceTuiles t :partie.getPile5()){
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
        for(final InterfaceClient j :partie.getListeJoueurs()){
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

    public void phase1() throws InterruptedException, RemoteException, NotBoundException, MalformedURLException {
        /* PHASE 1 */
        // Initialisation d'une nouvelle partie


        this.inscrireJoueur();

             Thread mon = new Thread();
             int z=0;
             synchronized (mon) {
                 while(this.serv.getListeClient().size()!=5){
                     if(z==0){
                         System.out.println("Attendre que 5 clients se connectent");
                     }

                        mon.wait(1000);
                     z=2;
                 }
             }
        partie.setServeur(this.serv);
        this.afficherJoueur();

        // Affichage de la position de la source
        posSource.setText("x : " + this.source.getX() + "\ny : " + this.source.getY());

        Task<Boolean> afficherTuile = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                // On récupère la première tuile de chaque pile
                InterfaceTuiles[] tuiles = partie.getFirstCarte();

                // On affiche la première tuile
                mettreImage(tuile1, tuiles[0].getType(), tuiles[0].getNbTravailleurs(), -1, -1);
                mettreImage(tuile2, tuiles[1].getType(), tuiles[1].getNbTravailleurs(), -1, -1);
                mettreImage(tuile3, tuiles[2].getType(), tuiles[2].getNbTravailleurs(), -1, -1);
                mettreImage(tuile4, tuiles[3].getType(), tuiles[3].getNbTravailleurs(), -1, -1);
                mettreImage(tuile5, tuiles[4].getType(), tuiles[4].getNbTravailleurs(), -1, -1);
                Thread mon = new Thread();
                synchronized (mon) {
                    mon.wait(10);
                }
                return true;
            }
        };

        afficherTuile.setOnSucceeded(e -> {
            try {
                this.phase2();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });

        new Thread(afficherTuile).start();



    }

    private void lancerClient() throws InterruptedException, RemoteException, NotBoundException, MalformedURLException {
        LancerClient client=new LancerClient();
        client.run();
        this.serv=client.getServeur();
        partie=client.getPartie();
        this.source=client.getSource();
    }
    public void afficherJoueur() throws RemoteException {
        ArrayList<InterfaceClient> listeClient = this.serv.getListeClient();
        this.listeJoueurs=this.serv.getListeClient();
        j1Nom.setText(listeClient.get(0).getNom());
        j1Couleur.setText(listeClient.get(0).getCouleur());
        if (this.listeJoueurs.size() == 2 || this.listeJoueurs.size() == 3 || this.listeJoueurs.size() == 4 || this.listeJoueurs.size() == 5) {
            j2Nom.setText(listeJoueurs.get(1).getNom());
            j2Couleur.setText(listeJoueurs.get(1).getCouleur());
        }
        if (this.listeJoueurs.size() == 3 || this.listeJoueurs.size() == 4 || this.listeJoueurs.size() == 5) {
            j3Nom.setText(listeJoueurs.get(2).getNom());
            j3Couleur.setText(listeJoueurs.get(2).getCouleur());
        }
        if (this.listeJoueurs.size() == 4 || this.listeJoueurs.size() == 5) {
            j4Nom.setText(listeJoueurs.get(3).getNom());
            j4Couleur.setText(listeJoueurs.get(3).getCouleur());
        }
        if (this.listeJoueurs.size() == 5) {
            j5Nom.setText(listeJoueurs.get(4).getNom());
            j5Couleur.setText(listeJoueurs.get(4).getCouleur());
        }
    }


    public void phase2() throws RemoteException, InterruptedException {




            int valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog(this.joueurEnCours.getNom() + ", faites votre enchère !"));

            while (true) {
                // verifie que la personne ne passe pas son tour
                if (valeurEnchere >= 0) {
                    // verifie que la personne ait bien l'argent pour la mise
                    if (this.joueurEnCours.getSolde() >= valeurEnchere) {
                        // verifie que personne n'ai misé cela avant
                        if (!this.serv.getEnchere().values().contains(valeurEnchere) || valeurEnchere==0) {
                            this.serv.putEnchere(this.joueurEnCours,valeurEnchere);
                            break;
                        } else {
                            valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Quelqu'un à déjà miser cette somme, faites une autre enchères ! :"));
                        }
                    } else {
                        valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Vous n'avez pas assez d'argent pour une telle enchere ! :"));
                    }
                } else {
                    valeurEnchere = Integer.parseInt(JOptionPane.showInputDialog("Pas d'enchere négative ! :"));
                }


            }

        serv.setListeEncherClient(partie.changerConstructeur(this.serv.getEnchere()));
        Thread mon = new Thread();
            int z=0;
        synchronized (mon) {
            while(this.serv.getListeEncherClient().size()!=5){
                if(z==0){
                    System.out.println("Attendre que tout le monde ait fait son enchere");
                }

                mon.wait(1000);
                z=2;
            }
        }

        this.serv.setMeilleurJoueur(this.serv.getListeEncherClient().get(0));
        int a=0;
        synchronized (mon) {
            while(!this.joueurEnCours.getNom().equals(serv.getMeilleurJoueur().getNom())){
                if(a==0){
                    System.out.println("attendre que les joueurs avec une plus grosse enchere joue");
                }
                mon.wait(1000);
                a=1;
            }
        }
        Task<Boolean> dragAndDropTuile = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                InterfaceTuiles[] tuiles = partie.getFirstCarte();
                    if(!partie.getTuilesJoue().contains(tuiles[0])) {
                        dragAndDrop(tuile1);
                    }else{
                        for(int i=0;i<partie.getTuilesJoue().size();i++){

                            if(partie.getTuilesJoue().get(i).getNum()==tuiles[0].getNum()) {

                                tuile1.setLayoutX(partie.getTuilesJoue().get(i).getX());
                                tuile1.setLayoutY(partie.getTuilesJoue().get(i).getY());
                                System.out.println("tuile1 x :"+tuile1.getLayoutX()+" tuile1 y :"+tuile1.getLayoutY());
                            }
                        }

                    }
                    if(!partie.getTuilesJoue().contains(tuiles[1])) {
                        dragAndDrop(tuile2);
                    }else{
                        for(int i=0;i<partie.getTuilesJoue().size();i++){
                            if(partie.getTuilesJoue().get(i).getNum()==tuiles[1].getNum()) {
                                tuile2.setLayoutX(partie.getTuilesJoue().get(i).getX());
                                tuile2.setLayoutY(partie.getTuilesJoue().get(i).getY());
                                System.out.println("tuile2 x :"+tuile2.getLayoutX()+" tuile2 y :"+tuile2.getLayoutY());}
                        }
                    }
                    if(!partie.getTuilesJoue().contains(tuiles[2])) {
                        dragAndDrop(tuile3);
                    }else{
                        for(int i=0;i<partie.getTuilesJoue().size();i++){
                            if(partie.getTuilesJoue().get(i).getNum()==tuiles[2].getNum()) {
                                tuile3.setLayoutX(partie.getTuilesJoue().get(i).getX());
                                tuile3.setLayoutY(partie.getTuilesJoue().get(i).getY());
                                System.out.println("tuile3 x :"+tuile3.getLayoutX()+" tuile3 y :"+tuile3.getLayoutY());}
                        }
                    }
                    if(!partie.getTuilesJoue().contains(tuiles[3])) {
                        dragAndDrop(tuile4);
                    }else{
                        for(int i=0;i<partie.getTuilesJoue().size();i++){
                            if(partie.getTuilesJoue().get(i).getNum()==tuiles[3].getNum()) {
                                tuile4.setLayoutX(partie.getTuilesJoue().get(i).getX());
                                tuile4.setLayoutY(partie.getTuilesJoue().get(i).getY());
                                System.out.println("tuile4 x :"+tuile4.getLayoutX()+" tuile4 y :"+tuile4.getLayoutY());}
                        }
                    }
                    if(!partie.getTuilesJoue().contains(tuiles[4])) {
                        dragAndDrop(tuile5);
                    }else{
                        for(int i=0;i<partie.getTuilesJoue().size();i++){
                            if(partie.getTuilesJoue().get(i).getNum()==tuiles[4].getNum()) {
                                tuile5.setLayoutX(partie.getTuilesJoue().get(i).getX());
                                tuile5.setLayoutY(partie.getTuilesJoue().get(i).getY());
                                System.out.println("tuile5 x :"+tuile5.getLayoutX()+" tuile5 y :"+tuile5.getLayoutY());
                            }
                        }
                    }
                Thread mon = new Thread();

                    int z=0;
                synchronized (mon) {
                    while(tuile1.isVisible()&&tuile2.isVisible()&&tuile3.isVisible()&&tuile4.isVisible()&&tuile5.isVisible()) {
                        if(z==0){
                            System.out.println("Veuillez jouer");
                        }
                        mon.wait(1000);
                        z=2;
                    }
                }
                return true;}


        };

        dragAndDropTuile.setOnSucceeded(e -> {

            try {
                InterfaceTuiles[] tuiles = partie.getFirstCarte();

                if( !tuile1.isVisible()) {
                    tuiles[0].setX(tuile1.getLayoutX());
                    tuiles[0].setY(tuile1.getLayoutY());
                    partie.addTuileJoue(tuiles[0]);
                }
                if(!tuile2.isVisible()) {
                    tuiles[1].setX(tuile2.getLayoutX());
                    tuiles[1].setY(tuile2.getLayoutY());
                    partie.addTuileJoue(tuiles[1]);
                }
                if(!tuile3.isVisible()) {
                    tuiles[2].setX(tuile3.getLayoutX());
                    tuiles[2].setY(tuile3.getLayoutY());
                    partie.addTuileJoue(tuiles[2]);
                }
                if(!tuile4.isVisible()) {
                    tuiles[3].setX(tuile4.getLayoutX());
                    tuiles[3].setY(tuile4.getLayoutY());
                    partie.addTuileJoue(tuiles[3]);
                }
                if(!tuile5.isVisible()) {
                    tuiles[4].setX(tuile5.getLayoutX());
                    tuiles[4].setY(tuile5.getLayoutY());
                    partie.addTuileJoue(tuiles[4]);
                }
                tuile1.setDisable(true);
                tuile2.setDisable(true);
                tuile3.setDisable(true);
                tuile4.setDisable(true);
                tuile5.setDisable(true);

                if (serv.getMeilleurJoueur().getNom().equals(this.serv.getListeEncherClient().get(0).getNom())){
                    serv.setMeilleurJoueur(this.serv.getListeEncherClient().get(1));
                }else{if (serv.getMeilleurJoueur().getNom().equals(this.serv.getListeEncherClient().get(1).getNom())){
                    serv.setMeilleurJoueur(this.serv.getListeEncherClient().get(2));
                }else{if (serv.getMeilleurJoueur().getNom().equals(this.serv.getListeEncherClient().get(2).getNom())){
                    serv.setMeilleurJoueur(this.serv.getListeEncherClient().get(3));
                }else{if (serv.getMeilleurJoueur().getNom().equals(this.serv.getListeEncherClient().get(3).getNom())){
                    serv.setMeilleurJoueur(this.serv.getListeEncherClient().get(4));
                }}}}
                this.phase3();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });

        new Thread(dragAndDropTuile).start();
    }
    
    public void phase3() throws RemoteException, InterruptedException {

        Map<InterfaceClient, Integer> enchere=new HashMap<>();
        this.serv.setEnchere(enchere);
        Map<InterfaceClient, String> passe = new HashMap<>();
        this.serv.setPasse(passe);
        Map<InterfaceClient, Integer> suivi = new HashMap<>();
        this.serv.setSuivi(suivi);
        Map<InterfaceClient, Canal> propositionJoueur = new HashMap<>();
        this.serv.setPropositionJoueur(propositionJoueur);


                // Seul les joueurs qui ne sont pas constructeur peuvent miser
                // Le constructeur n'intervient qu'à la fin de la phase
        for(int i=0;i<serv.getListeClient().size();i++) {
            if(serv.getListeClient().get(i)==this.joueurEnCours && serv.getListeClient().get(i).isEstConstructeur()){
                joueurEnCours.setEstConstructeur(true);
            };
        }

                if (!joueurEnCours.isEstConstructeur()) {

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
                        controller.setEnchere(this.serv.getEnchere());
                        controller.setJoueurActuel(joueurEnCours);

                        dialogStage.showAndWait();
                        String res = controller.getResultat();

                        // Cas où le joueur passe son tour
                        // Il n'intervient pas dans la construction des canaux
                        if (res.equals("Passe")) {
                            this.serv.passePut(joueurEnCours, res);
                        } // Cas où le joueur suit une mise existante :
                        // Il soutient une nouvelle mise de 1 Escudos
                        else if (res.contains("Escudos")) {
                            int mise = Integer.parseInt(res.substring(0, res.indexOf(" ")));
                            this.serv.putSuivi(joueurEnCours, mise + 1);
                        } // Cas où le joueur fait une nouvelle proposition :
                        // Il fait une proposition pour creuser un nouveau canal
                        else {
                            this.serv.putEnchere(joueurEnCours, Integer.parseInt(res));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.serv.augmentTour();
        Thread mon = new Thread();

        System.out.println(this.serv.getTour());



            // Chaque joueur qui a fait une proposition doit creuser un canal
            this.serv.getEnchere().entrySet().forEach((entry) -> {
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
                    Canal res = controller.getResultat();
                    this.serv.putPropositionJoueur(entry.getKey(), res);
                    this.afficherPropositionCanal(couleur, res.getxDeb(), res.getxFin(), res.getyDeb(), res.getyFin());

                } catch (IOException ex) {
                    Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            // Demander au constructeur de canal s'il veut accepter une offre où construire où il veut
            ArrayList<String> choices = new ArrayList<>();
            choices.add("Contruire un autre canal");
            this.serv.getEnchere().entrySet().forEach((entry) -> {
                try {
                    choices.add(entry.getKey().getNom() + " a misé " + entry.getValue() + " Escudos");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });


                if (joueurEnCours.isEstConstructeur()) {
                    ChoiceDialog<String> dialog = new ChoiceDialog<>();
                    dialog.getItems().addAll(choices);
                    dialog.setTitle("Mises enregistrées");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Choissisez une proposition : ");
                    dialog.setSelectedItem("Construire un autre canal");

                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {

                        System.out.println("Réponse : " + result.get());

                        if (result.get().contains("canal")) {
                            // Nouvelle fenêtre avec plusieurs proposition, puis ajout à la liste des canaux posés
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
                                Canal res = controller.getResultat();

                                // Effacer les propositions des joueurs
                                this.serv.getPropositionJoueur().entrySet().forEach((entry) -> {
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

                                this.afficherPropositionCanal("Bleu", res.getxDeb(), res.getxFin(), res.getyDeb(), res.getyFin());

                                partie.addListeCanauxPoses(res);
                            } catch (IOException ex) {
                                Logger.getLogger(PlateauController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (result.get().contains("Escudos")) {
                            // Récupérer le canal, puis ajout à la liste des canaux posés, puis coloration en bleu
                            String joueur = result.get().substring(0, result.get().indexOf(" "));
                            System.out.println(joueur);
                            Canal res = new Canal();
                            this.serv.getPropositionJoueur().entrySet().forEach((Map.Entry<InterfaceClient, Canal> entry) -> {
                                try {
                                    if (entry.getKey().getNom().equals(joueur)) {
                                        res.setxDeb(entry.getValue().getxDeb());
                                        res.setxFin(entry.getValue().getxFin());
                                        res.setyDeb(entry.getValue().getyDeb());
                                        res.setyFin(entry.getValue().getyFin());
                                    }
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            });

                            // Effacer les propositions des joueurs
                            this.serv.getPropositionJoueur().entrySet().forEach((entry) -> {
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
                            this.afficherPropositionCanal("Bleu", res.getxDeb(), res.getxFin(), res.getyDeb(), res.getyFin());
                            partie.addListeCanauxPoses(res);
                        }
                    }
                }

            //this.phase4();
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
    private void mettreImage(ImageView tuile, String type, int nbTravailleurs, double x, double y) {
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

                    plateau.add(new ImageView(image),(int)x,(int)y);

                } else {
                    tuile.setImage(image);
                }

        
    }
    public void inscrireJoueur() throws RemoteException {
        this.listeJoueurs = new ArrayList();


        // Le joueur doit choisir son nom et sa couleur
        String nomJ = "";
        String couleurJ = "";
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Informations joueurs");
        dialog.setHeaderText("Choisissez vos informations.");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Champ pour le nom
        TextField nom = new TextField();
        nom.setPromptText("Nom");
        // Liste de choix de couleur
        ChoiceBox couleur = new ChoiceBox(FXCollections.observableArrayList("Noir", "Violet", "Beige", "Gris","Caca d'oie"));
        couleur.getSelectionModel().selectFirst();

        grid.add(new Label("Nom :"), 0, 0);
        grid.add(nom, 1, 0);
        grid.add(new Label("Couleur :"), 0, 1);
        grid.add(couleur, 1, 1);

        // Active/désactive le bouton OK si le champ Nom est complété
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        nom.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Conversion du résultat en une paire de string lorsque le bouton OK est cliqué
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(nom.getText(), (String) couleur.getSelectionModel().getSelectedItem());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.isPresent()){
            nomJ = result.get().getKey();
            couleurJ = result.get().getValue();
        }

        Joueur J = new Joueur(nomJ, couleurJ, 10, 22);
        CanalJ c = new CanalJ(couleurJ);
        this.joueurEnCours = J;
        this.serv.enregistrer(J);
        partie.setServeur(this.serv);
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

                List<Canal> listeCanalPose = null;
                try {
                    listeCanalPose = partie.getListeCanalPose();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                // Cas d'un canal horizontal
                if (db.getImage().getHeight() == 10.0 && db.getImage().getWidth() == 100.0) {
                    // Vérifie que la position est acceptée pour un canal horizontal
                    if ((col == 1 || col == 4 || col == 7 || col == 10)
                            && (lig == 0 || lig == 3 || lig == 6 || lig == 9)) {
                        // Vérifier que le canal est près de la source ou d'un autre canal
                        Canal c = new Canal(col, lig, col + 1, lig);
                        try {
                            if(c.poserCanalPlateau(this.source, listeCanalPose)){
                                plateau.add(new ImageView(db.getImage()), col, lig);
                                plateau.add(new ImageView(db.getImage()), col + 1, lig);
                                success = true;
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
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
                        try {
                            if(c.poserCanalPlateau(this.source, listeCanalPose)){
                                plateau.add(new ImageView(db.getImage()), col, lig);
                                plateau.add(new ImageView(db.getImage()), col, lig + 1);
                                success = true;
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
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
    private void secheresse(Tuiles[] tuiles) throws RemoteException {
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
        for(final InterfaceTuiles tu: partie.getTuilesJoue()){
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
                    plateau.add(new ImageView(desert),(int)tu.getX(),(int)tu.getY());
                }else
                {
                    mettreImage(null, tu.getType(), tu.getNbTravailleurs(),tu.getX(),tu.getY());
                }
            }
        }
    }
}
