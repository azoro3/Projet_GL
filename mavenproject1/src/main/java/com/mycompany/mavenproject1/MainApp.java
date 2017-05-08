package com.mycompany.mavenproject1;

import Reseau.InterfaceServeur;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;


public class MainApp extends Application{
    private InterfaceServeur serveur;
    public InterfaceServeur getServeur(){
        return this.serveur;
    }
    public void setServeur(InterfaceServeur s){
        this.serveur=s;
    }
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/Plateau.fxml"));

        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Santiago");
        stage.setResizable(false); // empêcher le redimmensionnement de la fenêtre
        stage.setScene(scene);
        PlateauController p = new PlateauController();
        System.out.println(this.getServeur().getListeNom());
        stage.show();
        //p.phase1(this.serveur);

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public void main(String[] args) throws RemoteException, InterruptedException {

       launch();

    }


}
