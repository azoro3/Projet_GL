/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Joueur;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Magalie Mallinger
 */
public class Phase3Controller implements Initializable {

    @FXML
    private RadioButton radio1;
    @FXML
    private RadioButton radio2;
    @FXML
    private TextField mise;
    @FXML
    private RadioButton radio3;
    @FXML
    private ChoiceBox<String> soutien;
    @FXML
    private Button okButton;
    @FXML
    private Label text;

    private Stage dialogStage;
    final ToggleGroup group = new ToggleGroup();
    private Map<Joueur, String> enchere;
    private String resultat;
    private Joueur joueurActuel;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Création d'un groupe pour les boutons radios
        radio1.setToggleGroup(group);
        radio2.setToggleGroup(group);
        radio3.setToggleGroup(group);
        
        // Par défaut la proposition d'une mise est sélectionnée
        radio2.setSelected(true);
        mise.setText("1");
        soutien.setDisable(true);
        
        // Active/Désactive les éléments du dialogue
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (group.getSelectedToggle() == radio1) {
                mise.setDisable(true);
                soutien.setDisable(true);
            } else if (group.getSelectedToggle() == radio2) {
                mise.setDisable(false);
                soutien.setDisable(true);
            } else if (group.getSelectedToggle() == radio3) {
                mise.setDisable(true);
                soutien.setDisable(false);
            }
        });
        
        // Seul des nombres peuvent être entrés dans le champ de proposition d'une mise
        mise.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                mise.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setEnchere(Map<Joueur, String> enchere){
        this.enchere = enchere;
        
        // Remplissage de la liste de choix si suivi de mise
        enchere.entrySet().forEach((entry) -> {
            soutien.getItems().add(entry.getKey().getNom() + " mise " + entry.getValue());
        });
    }

    public void setJoueurActuel(Joueur joueurActuel) {
        this.joueurActuel = joueurActuel;
        text.setText(joueurActuel.getNom() + ", passez, proposez une mise ou soutenez une mise existante.");
    }
    
    public void okClicked() {
        RadioButton choix = (RadioButton) group.getSelectedToggle();
        
        switch(choix.getId()){
            case "radio1":
                this.resultat = "Passe";
                break;
            case "radio2":
                this.resultat = mise.getText();
                break;
            case "radio3":
                this.resultat = soutien.getSelectionModel().getSelectedItem();
                break;
        }

        dialogStage.close();
    }

    public String getResultat() {
        return resultat;
    }
    
}
