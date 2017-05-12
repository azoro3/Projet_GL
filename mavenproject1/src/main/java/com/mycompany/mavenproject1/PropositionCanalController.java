/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Jeu.Joueur;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Magalie Mallinger
 */
public class PropositionCanalController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private RadioButton canalVerti, canalHoriz;
    @FXML
    private ChoiceBox<Canal> canaux;
    @FXML
    private Button okButton;

    private Stage dialogStage;
    private Partie partie;
    private final ToggleGroup group = new ToggleGroup();
    private Canal resultat = new Canal();
    private Joueur joueurActuel;
    private ArrayList<Canal> canauxAutorises = new ArrayList<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Création d'un groupe pour les boutons radios
        canalVerti.setToggleGroup(group);
        canalHoriz.setToggleGroup(group);

        // Par défaut le canal vertical est sélectionnée
        canalVerti.setSelected(false);
        canalHoriz.setSelected(false);

        // Listener sur les boutons radio
        ObservableList<Canal> items = FXCollections.observableArrayList();
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (group.getSelectedToggle() == canalVerti) {
                items.clear();
                canaux.getItems().clear();
                canauxAutorises = partie.getListeCanauxAutorises(true);
                items.addAll(canauxAutorises);
            } else if (group.getSelectedToggle() == canalHoriz) {
                items.clear();
                canaux.getItems().clear();
                canauxAutorises = partie.getListeCanauxAutorises(false);
                items.addAll(canauxAutorises);
            }
        });

        canaux.setItems(items);
        canaux.getSelectionModel().selectFirst();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setJoueurActuel(Joueur joueurActuel) {
        this.joueurActuel = joueurActuel;
        label.setText(joueurActuel.getNom() + ", choisissez un canal et ses coordonnées.");
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }
  
    public void okClicked() {
        this.resultat = canaux.getSelectionModel().getSelectedItem();
        dialogStage.close();
    }

    public Canal getResultat() {
        return resultat;
    }

}
