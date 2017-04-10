package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.Jeu.Plateau.Source;
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
    
    @FXML
    private ImageView tuile1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chargement de l'image "source.png"
        final ImageView img = new ImageView();
        final Image sourceImg = new Image(getClass().getResourceAsStream("/images/source.png"));
        img.setImage(sourceImg);
        
        // Placement aléatoire de la source
        plateau.add(img, Source.getInstance().getX(), Source.getInstance().getY());
        
        
    }
}
