/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.*;
import com.mycompany.mavenproject1.Jeu.Factory.CanalFactory;
import com.mycompany.mavenproject1.Jeu.Factory.TuilesFactory;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class Partie {
    
    private ArrayList<Joueur> listeJoueurs;
    private List<Tuiles> pile1 = new LinkedList();
    private List<Tuiles> pile2 = new LinkedList();
    private List<Tuiles> pile3 = new LinkedList();
    private List<Tuiles> pile4 = new LinkedList();
    private List<Tuiles> pile5 = new LinkedList();
    private List<Canal> listeCanal = new LinkedList();
    private List<Canal> listeCanalPose = new LinkedList<>();
    private List<Tuiles> tuilesJoue = new LinkedList<>();
    private Source s;
    
   /**
    * fonction de création d'une partie
    */
    public  void initPartie() {

//      création des joueurs
        s=Source.getInstance();
        System.out.println(s.getX()+""+s.getY());
        this.listeJoueurs = new ArrayList();
        
        for (int i = 0; i <= 3; i++) {
            // Le joueur doit choisir son nom et sa couleur
            String nomJ = "";
            String couleurJ = "";
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Informations joueurs");
            dialog.setHeaderText("Choisissez vos informations.");
            
            ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            // Champ pour le nom
            TextField nom = new TextField();
            nom.setPromptText("Nom");
            // Liste de choix de couleur
            ChoiceBox couleur = new ChoiceBox(FXCollections.observableArrayList("Noir", "Violet", "Beige", "Gris"));
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
            this.listeJoueurs.add(J);
        }

        this.listeJoueurs.get(0).setEstConstructeur(true);
        
//      création des Tuiles
        List<Tuiles> touteLesTuiles = new LinkedList();
        CanalFactory factory=new CanalFactory();
        for(int i=0;i<=15;i++){
            Canal c=factory.genererCanal();
            this.listeCanal.add(c);
        }
//      Mettre les tuiles dans les liste
        TuilesFactory tFactory;
        tFactory = new TuilesFactory();
        for (int i=1;i<=9;i++){
            touteLesTuiles.add(tFactory.genererTuiles("piment"));
            touteLesTuiles.add(tFactory.genererTuiles("haricot"));
            touteLesTuiles.add(tFactory.genererTuiles("banane"));
            touteLesTuiles.add(tFactory.genererTuiles("patate"));
            touteLesTuiles.add(tFactory.genererTuiles("sucre"));
        }
        while(touteLesTuiles.size()>0){
            int val;
            val = (int) (Math.random() * ( touteLesTuiles.size()));
            int val2;
            val2 = (int) (Math.random() * (5));
            switch (val2){
                case 0:
                    if(this.pile1.size()<9){
                    this.pile1.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 1:
                    if(this.pile2.size()<9){
                        this.pile2.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 2:
                    if(this.pile3.size()<9){
                        this.pile3.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 3:
                    if(this.pile4.size()<9){
                        this.pile4.add(touteLesTuiles.remove(val));
                    }
                    break;
                case 4:
                    if(this.pile5.size()<9){
                        this.pile5.add(touteLesTuiles.remove(val));
                    }
                    break;
            }
        }
     
    }
    
    /**
     * 
     * @return tableau avec la première tuile de chaque pile
     */
    public Tuiles[] getFirstCarte() {
        Tuiles[] t = new Tuiles[5];
        t[0] = this.pile1.get(0);
        t[1] = this.pile2.get(0);
        t[2] = this.pile3.get(0);
        t[3] = this.pile4.get(0);
        t[4] = this.pile5.get(0);
        return t;
    }
    
    /**
     * 
     * @return un hasmap avec les différentes mises des joueurs
     */
    public Map<Joueur, String> faireUneEnchere() {
        Map<Joueur, String> enchere = new HashMap<>();
//      fonction pour trier les joueurs dans le sens des enchères.
        boolean tri = false; int i=0;
        ArrayList<Joueur> lTemp=new ArrayList<>();
        while(!tri){
          if(this.listeJoueurs.get(i).isEstConstructeur())  {
              for(int j=0;j<=i;j++){
                  lTemp.add(this.listeJoueurs.remove(j));
              }
            tri=true;
          }
          i++;
        }
        this.listeJoueurs.addAll(lTemp);
        // Enchères des joueurs
        for (final Joueur joueur : this.listeJoueurs) {
            String valeurEnchere = JOptionPane.showInputDialog(joueur.getNom() + ", faites votre enchère !");
            
            while (enchere.values().contains(valeurEnchere) /*|| Integer.parseInt(valeurEnchere)>=joueur.getSolde()*/) {
                if ("Passe".equals(valeurEnchere)) {
                    enchere.put(joueur, valeurEnchere);
                    break;
                } else {
                    valeurEnchere = JOptionPane.showInputDialog("Quelqu'un à déjà miser cette somme, faites une autre enchères ! :");
                }
            }
            enchere.put(joueur, valeurEnchere);
            if(!valeurEnchere.equals("Passe")){
                joueur.setSolde(joueur.getSolde()-Integer.parseInt(valeurEnchere));
            }
        }
        return enchere;
    }
    
    /**
     * 
     * @param enchere map avec les enchère de chaque joueurs
     */
    public void changerConstructeur(Map<Joueur, String> enchere) {
        for (Map.Entry<Joueur, String> pair : enchere.entrySet()) {
            if (pair.getValue().equals("Passe")) {
                pair.getKey().setEstConstructeur(true);
                break;
            }
        }
    }

    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public List<Tuiles> getPile1() {
        return pile1;
    }

    public List<Tuiles> getPile2() {
        return pile2;
    }

    public List<Tuiles> getPile3() {
        return pile3;
    }

    public List<Tuiles> getPile4() {
        return pile4;
    }

    public List<Tuiles> getPile5() {
        return pile5;
    }

    public List<Canal> getListeCanal() {
        return listeCanal;
    }

    public List<Canal> getListeCanalPose() {
        return listeCanalPose;
    }
     public List<Tuiles> getTuilesJoue(){
        return tuilesJoue;
}

}