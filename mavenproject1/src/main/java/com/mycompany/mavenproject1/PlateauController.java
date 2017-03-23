package com.mycompany.mavenproject1;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PlateauController implements Initializable {

    @FXML
    private GridPane plateau;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chargement de l'image "source.png"
        final ImageView img = new ImageView();
        final Image sourceImg = new Image(getClass().getResourceAsStream("/images/source.png"));
        img.setImage(sourceImg);
        
        // TODO Récupérer la position de la source
        
        // Placement de la source
        plateau.add(img, 0, 0);
    }
}
